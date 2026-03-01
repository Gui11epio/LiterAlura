package br.com.alura.literalura.Literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "livro")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_autor")
    private Autor autor;
    private String idioma;
    private double downloads;

    public Livro() {
    }

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();

        if (dadosLivro.autores() != null && !dadosLivro.autores().isEmpty()) {
            this.autor = new Autor(dadosLivro.autores().get(0));
        } else {
            this.autor = null;
        }

        if (dadosLivro.idiomas() != null && !dadosLivro.idiomas().isEmpty()) {
            this.idioma = String.join(",", dadosLivro.idiomas());
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public double getDownloads() {
        return downloads;
    }

    public void setDownloads(double downloads) {
        this.downloads = downloads;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "titulo: '" + titulo + '\'' +
                "\nAutor: " + (autor != null ? autor.getNome() : "Não encontrado") +
                "\nIdioma: " + idioma +
                "\nNúmero de Downloads:" + downloads + "\n";
    }
}
