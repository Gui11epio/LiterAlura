package br.com.alura.literalura.Literalura.repository;

import br.com.alura.literalura.Literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT  DISTINCT a FROM Livro l JOIN l.autor a WHERE  a.anoNascimento <= :ano AND (a.anoFalecimento IS NULL OR a.anoFalecimento >= :ano)")
    List<Autor> listarAutoresVivos(int anoVivo);

    @Query("SELECT DISTINCT a FROM Livro l JOIN l.autor a ORDER BY a.nome")
    List<Autor> listarAutores();

}
