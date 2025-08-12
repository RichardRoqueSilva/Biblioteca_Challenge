package com.biblioteca.challenge.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "IDIOMA")
@Data
public class Idioma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    @ManyToMany(mappedBy = "idiomas")
    private Set<Livro> livros;

    @Override
    public String toString() {
        return "Código: " + codigo;
    }
}
