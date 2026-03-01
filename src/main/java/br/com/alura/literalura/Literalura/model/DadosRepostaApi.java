package br.com.alura.literalura.Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record DadosRepostaApi(
        @JsonAlias("results") List<DadosLivro> resultado)
        {
}
