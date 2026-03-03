package br.com.larissa.literalura.service;

import br.com.larissa.literalura.dto.*;
import br.com.larissa.literalura.model.*;
import br.com.larissa.literalura.repository.*;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class LivroService {

    private final ConsumoApi consumoApi;
    private final ConverteDados converteDados;
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public LivroService(ConsumoApi consumoApi,
                        ConverteDados converteDados,
                        LivroRepository livroRepository,
                        AutorRepository autorRepository) {
        this.consumoApi = consumoApi;
        this.converteDados = converteDados;
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void buscarLivro() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome do livro:");
        String titulo = scanner.nextLine();

        String json = consumoApi.obterDados(titulo);
        DadosResultado resultado = converteDados.obterDados(json, DadosResultado.class);

        if (resultado.results().isEmpty()) {
            System.out.println("Livro não encontrado!");
            return;
        }

        DadosLivro dadosLivro = resultado.results().get(0);
        DadosAutor dadosAutor = dadosLivro.authors().get(0);

        Autor autor = new Autor();
        autor.setNome(dadosAutor.name());
        autor.setAnoNascimento(dadosAutor.birth_year());
        autor.setAnoFalecimento(dadosAutor.death_year());

        autorRepository.save(autor);

        Livro livro = new Livro();
        livro.setTitulo(dadosLivro.title());
        livro.setIdioma(dadosLivro.languages().get(0));
        livro.setNumeroDownloads(dadosLivro.download_count());
        livro.setAutor(autor);

        livroRepository.save(livro);

        System.out.println("Livro salvo com sucesso!");
        public void listarLivros() {
            livroRepository.findAll().forEach(l ->
                    System.out.println(
                            "Livro: " + l.getTitulo() +
                                    " | Autor: " + l.getAutor().getNome() +
                                    " | Idioma: " + l.getIdioma()
                    )
            );
        }
        public void listarAutores() {
            autorRepository.findAll().forEach(a ->
                    System.out.println(
                            "Autor: " + a.getNome() +
                                    " (" + a.getAnoNascimento() +
                                    " - " + a.getAnoFalecimento() + ")"
                    )
            );
        }
        public void listarPorIdioma() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Digite o idioma (ex: en, pt, fr): ");
            String idioma = scanner.nextLine();

            livroRepository.findByIdioma(idioma)
                    .forEach(l ->
                            System.out.println("Livro: " + l.getTitulo())
                    );
        }
        public void listarAutoresPorAno() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Digite o ano: ");
            int ano = scanner.nextInt();

            autorRepository.autoresVivosNoAno(ano)
                    .forEach(a ->
                            System.out.println("Autor: " + a.getNome())
                    );
        }
    }

}