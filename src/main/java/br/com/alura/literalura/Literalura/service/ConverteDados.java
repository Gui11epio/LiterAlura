package br.com.alura.literalura.Literalura.service;

import tools.jackson.databind.ObjectMapper;

public class ConverteDados implements ICoverteDados{
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar converter os dados: " +e.getMessage());
        }
    }
}
