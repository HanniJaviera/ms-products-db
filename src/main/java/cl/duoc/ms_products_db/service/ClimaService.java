package cl.duoc.ms_products_db.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;


@Service
public class ClimaService {

    @Value("${meteored.api.key}")
    private String apiKey;

    @Value("${meteored.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String obtenerClima(String ciudad) {

        String url = apiUrl +
                "?affiliate_id=" + apiKey +
                "&city=" + ciudad +
                "&lang=es";

        return restTemplate.getForObject(url, String.class);
    }
}