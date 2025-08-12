package com.biblioteca.challenge.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "LIVRO")
@Data
public class Livro {
    @Id
    private Long id;

    private String titulo;

    @Column(name = "DOWNLOAD_COUNT")
    private Integer downloadCount;

    @ManyToMany
    @JoinTable(name = "LIVRO_AUTOR",
            joinColumns = @JoinColumn(name = "LIVRO_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTOR_ID"))
    private Set<Autor> autores;

    @ManyToMany
    @JoinTable(name = "LIVRO_IDIOMA",
            joinColumns = @JoinColumn(name = "LIVRO_ID"),
            inverseJoinColumns = @JoinColumn(name = "IDIOMA_ID"))
    private Set<Idioma> idiomas;

    @Override
    public String toString() {
        return "---------- Livro ----------" +
                "\nTítulo: " + titulo +
                "\nID: " + id +
                "\nContagem de Downloads: " + downloadCount +
                "\n---------------------------";
    }
}
