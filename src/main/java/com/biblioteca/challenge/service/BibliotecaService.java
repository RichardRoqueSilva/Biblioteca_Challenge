package com.biblioteca.challenge.service;

import com.biblioteca.challenge.dto.GutendexAuthor;
import com.biblioteca.challenge.dto.response.GutendexResponse;
import com.biblioteca.challenge.model.Autor;
import com.biblioteca.challenge.model.Idioma;
import com.biblioteca.challenge.model.Livro;
import com.biblioteca.challenge.repository.AutorRepository;
import com.biblioteca.challenge.repository.IdiomaRepository;
import com.biblioteca.challenge.repository.LivroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BibliotecaService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final IdiomaRepository idiomaRepository;
    private final RestClient gutendexClient;

    @Autowired
    public BibliotecaService(LivroRepository livroRepository, AutorRepository autorRepository,
                             IdiomaRepository idiomaRepository, RestClient gutendexClient) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.idiomaRepository = idiomaRepository;
        this.gutendexClient = gutendexClient;
    }

    @Transactional
    public void buscarESalvarLivrosAPI() {
        GutendexResponse response = gutendexClient.get()
                .retrieve()
                .body(GutendexResponse.class);

        if (response != null && response.getResults() != null) {
            for (com.biblioteca.challenge.dto.GutendexBook gutendexBook : response.getResults()) {
                // Verifica se o livro já existe no banco de dados para evitar duplicação
                if (livroRepository.findById(gutendexBook.getId()).isEmpty()) {

                    Livro novoLivro = new Livro();
                    novoLivro.setId(gutendexBook.getId());
                    novoLivro.setTitulo(gutendexBook.getTitle());
                    novoLivro.setDownloadCount(gutendexBook.getDownload_count());

                    Set<Autor> autoresDoLivro = new HashSet<>();
                    for (GutendexAuthor gutendexAuthor : gutendexBook.getAuthors()) {
                        Autor autor = autorRepository.findByNome(gutendexAuthor.getName())
                                .orElseGet(() -> {
                                    Autor novoAutor = new Autor();
                                    novoAutor.setNome(gutendexAuthor.getName());
                                    novoAutor.setAnoNascimento(gutendexAuthor.getBirth_year());
                                    novoAutor.setAnoMorte(gutendexAuthor.getDeath_year());
                                    return autorRepository.save(novoAutor);
                                });
                        autoresDoLivro.add(autor);
                    }
                    novoLivro.setAutores(autoresDoLivro);

                    Set<Idioma> idiomasDoLivro = new HashSet<>();
                    for (String codigoIdioma : gutendexBook.getLanguages()) {
                        Idioma idioma = idiomaRepository.findByCodigo(codigoIdioma)
                                .orElseGet(() -> {
                                    Idioma novoIdioma = new Idioma();
                                    novoIdioma.setCodigo(codigoIdioma);
                                    return idiomaRepository.save(novoIdioma);
                                });
                        idiomasDoLivro.add(idioma);
                    }
                    novoLivro.setIdiomas(idiomasDoLivro);

                    livroRepository.save(novoLivro);
                }
            }
            System.out.println("Livros da API Gutendex salvos com sucesso!");
        } else {
            System.out.println("Não foi possível obter dados da API Gutendex.");
        }
    }

    public List<Livro> listarTodosLivros() {
        return livroRepository.findAll();
    }

    public List<Livro> buscarLivrosPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Livro> buscarLivrosPorAutor(String nomeAutor) {
        return livroRepository.findByAutorNomeContainingIgnoreCase(nomeAutor);
    }

    public List<Autor> listarTodosAutores() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(a -> System.out.println(a));
        return autores;
    }

    public List<Livro> listarLivrosPorIdioma(String codigoIdioma) {
        List<Livro> livros = livroRepository.findByIdiomaCodigo(codigoIdioma);
        livros.forEach(l -> System.out.println(l));
        return livros;
    }
    public List<Idioma> listarTodosIdiomas() {
        return idiomaRepository.findAll();
    }

}
