package com.biblioteca.challenge.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "AUTOR")
@Data
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(name = "ANO_NASCIMENTO")
    private Integer anoNascimento;

    @Column(name = "ANO_MORTE")
    private Integer anoMorte;

    @ManyToMany(mappedBy = "autores")
    private Set<Livro> livros;

    @Override
    public String toString() {
        return "---------- Autor ----------" +
                "\nNome: " + nome +
                "\nAno de Nascimento: " + anoNascimento +
                "\nAno de Morte: " + anoMorte +
                "\n---------------------------";
    }
}
