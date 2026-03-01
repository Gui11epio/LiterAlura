package br.com.alura.literalura.Literalura.service;

public interface ICoverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
