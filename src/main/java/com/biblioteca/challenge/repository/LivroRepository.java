package com.biblioteca.challenge.repository;

import com.biblioteca.challenge.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByTituloContainingIgnoreCase(String titulo);

    // Consulta customizada para buscar livros por nome de autor
    @Query("SELECT l FROM Livro l JOIN l.autores a WHERE a.nome LIKE %:nomeAutor%")
    List<Livro> findByAutorNomeContainingIgnoreCase(String nomeAutor);

    // Consulta customizada para buscar livros por código de idioma
    @Query("SELECT l FROM Livro l JOIN l.idiomas i WHERE i.codigo = :codigoIdioma")
    List<Livro> findByIdiomaCodigo(String codigoIdioma);
}
