package com.biblioteca.challenge.scanner;

import com.biblioteca.challenge.model.Autor;
import com.biblioteca.challenge.model.Idioma;
import com.biblioteca.challenge.model.Livro;
import com.biblioteca.challenge.service.BibliotecaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Console implements CommandLineRunner {

    private final BibliotecaService bibliotecaService;
    private final Scanner scanner;

    public Console(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
        // Popula o banco de dados com livros da API ao iniciar a aplicação
        bibliotecaService.buscarESalvarLivrosAPI();

        // Inicia o menu de interação com o usuário
        exibirMenu();
    }

    private void exibirMenu() {
        var opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Bem-vindo à Biblioteca! ---");
            System.out.println("1. Listar todos os livros já buscados");
            System.out.println("2. Buscar livros por título");
            System.out.println("3. Buscar livros por autor");
            System.out.println("4. Listar todos os autores já buscados");
            System.out.println("5. Listar livros por idioma");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                String entrada = scanner.nextLine();
                if (entrada.isEmpty()) {
                    continue; // Pula para a próxima iteração se a entrada for vazia
                }
                opcao = Integer.parseInt(entrada);

                switch (opcao) {
                    case 1:
                        List<Livro> todosLivros = bibliotecaService.listarTodosLivros();
                        if (todosLivros.isEmpty()) {
                            System.out.println("Nenhum livro encontrado.");
                        } else {
                            todosLivros.forEach(System.out::println);
                        }
                        break;
                    case 2:
                        System.out.print("Digite o título do livro: ");
                        String titulo = scanner.nextLine();
                        List<Livro> livrosPorTitulo = bibliotecaService.buscarLivrosPorTitulo(titulo);
                        if (livrosPorTitulo.isEmpty()) {
                            System.out.println("Nenhum livro com esse título encontrado.");
                        } else {
                            livrosPorTitulo.forEach(System.out::println);
                        }
                        break;
                    case 3:
                        System.out.print("Digite o nome do autor: ");
                        String nomeAutor = scanner.nextLine();
                        List<Livro> livrosPorAutor = bibliotecaService.buscarLivrosPorAutor(nomeAutor);
                        if (livrosPorAutor.isEmpty()) {
                            System.out.println("Nenhum livro com esse autor encontrado.");
                        } else {
                            livrosPorAutor.forEach(System.out::println);
                        }
                        break;
                    case 4:
                        List<Autor> todosAutores = bibliotecaService.listarTodosAutores();
                        if (todosAutores.isEmpty()) {
                            System.out.println("Nenhum autor encontrado.");
                        } else {
                            todosAutores.forEach(System.out::println);
                        }
                        break;
                    case 5:
                        // 1. Chama o serviço para listar os idiomas
                        List<Idioma> todosIdiomas = bibliotecaService.listarTodosIdiomas();

                        // 2. Exibe a lista de idiomas disponíveis
                        if (todosIdiomas.isEmpty()) {
                            System.out.println("Nenhum idioma encontrado no banco de dados.");
                        } else {
                            System.out.println("Idiomas disponíveis:");
                            todosIdiomas.stream()
                                    .map(Idioma::getCodigo)
                                    .forEach(codigo -> System.out.print(codigo + " "));
                            System.out.println("\n");
                        }

                        // 3. Pede a entrada do usuário
                        System.out.print("Digite o código do idioma (ex: en, es, fr): ");
                        String codigoIdioma = scanner.nextLine();
                        List<Livro> livrosPorIdioma = bibliotecaService.listarLivrosPorIdioma(codigoIdioma);
                        if (livrosPorIdioma.isEmpty()) {
                            System.out.println("Nenhum livro neste idioma encontrado.");
                        } else {
                            livrosPorIdioma.forEach(System.out::println);
                        }
                        break;

                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
            }
        }
    }
}