package br.com.larissa.literalura.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConsumoApi {

    private final RestTemplate restTemplate = new RestTemplate();

    public String obterDados(String titulo) {
        String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "%20");
        return restTemplate.getForObject(url, String.class);
    }
}