package br.com.alura.literalura.Literalura;

import br.com.alura.literalura.Literalura.principal.Principal;
import br.com.alura.literalura.Literalura.repository.AutorRepository;
import br.com.alura.literalura.Literalura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LivroRepository repositorioLivro;

	@Autowired
	private AutorRepository repositorioAutor;


	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorioLivro, repositorioAutor);
		principal.menu();
	}
}
