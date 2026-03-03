package br.com.larissa.literalura;

import br.com.larissa.literalura.service.LivroService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Bean
	CommandLineRunner run(LivroService service) {
		return args -> {

			Scanner scanner = new Scanner(System.in);
			int opcao = -1;

			while (opcao != 0) {
				System.out.println("""
                1 - Buscar livro pelo título
                2 - Listar livros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos em determinado ano
                5 - Listar livros por idioma
                0 - Sair
                """);

				opcao = scanner.nextInt();
				scanner.nextLine();

				switch (opcao) {
					case 1 -> service.buscarLivro();
					case 2 -> service.listarLivros();
					case 3 -> service.listarAutores();
					case 4 -> service.listarAutoresPorAno();
					case 5 -> service.listarPorIdioma();
				}
			}
		};
	}
}