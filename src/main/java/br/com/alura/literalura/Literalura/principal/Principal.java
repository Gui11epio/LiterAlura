package br.com.alura.literalura.Literalura.principal;

import br.com.alura.literalura.Literalura.model.Autor;
import br.com.alura.literalura.Literalura.model.DadosLivro;
import br.com.alura.literalura.Literalura.model.DadosRepostaApi;
import br.com.alura.literalura.Literalura.model.Livro;
import br.com.alura.literalura.Literalura.repository.AutorRepository;
import br.com.alura.literalura.Literalura.repository.LivroRepository;
import br.com.alura.literalura.Literalura.service.ConsumoAPI;
import br.com.alura.literalura.Literalura.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private Scanner leitor = new Scanner(System.in);
    private LivroRepository repositorioLivro;
    private AutorRepository repositorioAutor;
    private List<Livro> livros = new ArrayList<>();
    List<Autor> autores = new ArrayList<>();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private ConverteDados conversor = new ConverteDados();
    private ConsumoAPI consumo = new ConsumoAPI();
    private LivroRepository repositorio;

    public Principal(LivroRepository repositorioLivro, AutorRepository repositorioAutor) {
        this.repositorioLivro = repositorioLivro;
        this.repositorioAutor = repositorioAutor;
    }

    public void menu() {

        int opcao = -1;

        while (opcao != 0) {
            var menu = """
                    -------------------
                    Escolha uma opção:
                    1 - Buscar livro pelo título
                    2 - Listar Livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar Livros em determinado idioma
                    0 - Sair
                    -------------------
                    Digite uma opção:
                    """;

            System.out.println(menu);

            try {
                var entrada = leitura.nextLine();
                opcao = Integer.parseInt(entrada);

                switch (opcao) {
                    case 1:
                        buscarLivro();
                        break;
                    case 2:
                        listarLivrosDoBanco();
                        break;
                    case 3:
                        listarAutor();
                        break;
                    case 4:
                        listarAutoresVivos();
                        break;
                    case 5:
                        listarPorIdioma();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida");
                        break;
                }



            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite a penas números inteiros!");
            }
        }
    }




    private void listarLivrosDoBanco() {
        System.out.println("==== Livros Registrados  no Banco ===");
        livros = repositorioLivro.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no banco de dados");
        } else {
            livros.stream()
                    .forEach(System.out::println);
        }
    }

    private void listarAutor() {
        System.out.println("<==== Autor foi registrado! ====>");
        autores = repositorioAutor.listarAutores();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado!");
        } else {
            autores.stream()
                    .map(autor -> autor.getNome())
                    .forEach(System.out::println);
        }

    }

    private void listarAutoresVivos() {
        System.out.println("Digite o ano que deseja pesquisa: ");
        try {
            var ano = Integer.parseInt(leitura.nextLine());
            autores = repositorioAutor.listarAutoresVivos(ano);

            if (autores.isEmpty()) {
                System.out.println("Nenhum autor encontrado no ano de " + ano);
            } else {
                System.out.println("<==== Autor encontrado, ano: ("+ ano +") ====>");
                autores.stream().forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("Ano inválido!");
        }

    }

    private void listarPorIdioma() {
        System.out.println("""
                Escolha um idioma:
                pt - Português
                en - Inglês
                es - Espanhol
                fr - Francês
                """);
        var idioma = leitura.nextLine().toLowerCase().trim();

        List<Livro> livrosPorIdioma = repositorioLivro.findByIdiomas(idioma);
        if (livrosPorIdioma.isEmpty()) {
            System.out.println("Nenhum livro encontrado nesse idioma");
        } else {
            System.out.println("\n Livro encontrado em (" + idioma + ") ===");
            livrosPorIdioma.stream().forEach(System.out::println);
        }

    }

    public void buscarLivro() {
        System.out.println("Digite nome de um Livro para fazer busca");
        var nomeLivro = leitor.nextLine();

        if (nomeLivro.isBlank()) {
            System.out.println("Busca vazia");
        }

        String json = consumo.obterDados(ENDERECO + nomeLivro
                .trim().replace(" ", "+"));

        DadosRepostaApi dados = conversor.obterDados(json, DadosRepostaApi.class);

        if (dados != null && !dados.resultado().isEmpty()) {
            DadosLivro dadosLivro = dados.resultado().get(0);
            // Verifica se o livro já existe no banco
            Optional<Livro> livroExistente = repositorio.findByTitulo(dadosLivro.titulo());

            if (livroExistente.isPresent()) {
                System.out.println("\n Livro já registrado no banco!");
                exibirResumo(dadosLivro);
            } else {
                Livro novoLivro = new Livro(dadosLivro);
                repositorio.save(novoLivro);

                System.out.println("\n Livro  salvo com sucesso!");
                exibirResumo(dadosLivro);
            }

        } else {
            System.out.println("Livro não encontrado na API.");
        }
    }

    public void exibirResumo(DadosLivro dadosLivro) {
        System.out.println("==================");
        System.out.println("Titulo:" + dadosLivro.titulo());
        System.out.println("Autor:" + dadosLivro.autores());
        System.out.println("Idioma:" + dadosLivro.idiomas());
        System.out.println("==================");
    }
}
