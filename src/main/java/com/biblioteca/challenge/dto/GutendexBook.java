package com.biblioteca.challenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GutendexBook {
    private Long id;
    private String title;
    private List<GutendexAuthor> authors;
    private List<String> languages;
    private int download_count;

    // Você pode adicionar mais campos se for usar (subjects, bookshelves, etc.)
}
