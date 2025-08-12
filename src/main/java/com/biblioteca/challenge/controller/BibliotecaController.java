package com.biblioteca.challenge.controller;

import com.biblioteca.challenge.model.Livro;
import com.biblioteca.challenge.service.BibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/biblioteca")
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;

    @Autowired
    public BibliotecaController(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    @GetMapping("/listar-livros")
    public ResponseEntity<List<Livro>> listarTodosLivros() {
        List<Livro> livros = bibliotecaService.listarTodosLivros();
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/buscar-por-titulo")
    public ResponseEntity<List<Livro>> buscarLivrosPorTitulo(@RequestParam String titulo) {
        List<Livro> livrosEncontrados = bibliotecaService.buscarLivrosPorTitulo(titulo);
        return ResponseEntity.ok(livrosEncontrados);
    }
}
