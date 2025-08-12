package com.biblioteca.challenge.dto.response;

import com.biblioteca.challenge.dto.GutendexBook;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GutendexResponse {
    private int count;
    private String next;
    private String previous;
    private List<GutendexBook> results;
}
