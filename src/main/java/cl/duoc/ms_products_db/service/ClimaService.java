package cl.duoc.ms_products_db.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClimaService {

    @Value("${meteored.api.key}")
    private String apiKey;

    @Value("${meteored.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String obtenerClima(String ciudad) {

        String idCiudad = obtenerIdCiudad(ciudad);

        if (idCiudad == null) {
            throw new RuntimeException("Ciudad no soportada: " + ciudad);
        }

        String url = apiUrl
                + "?affiliate_id=" + apiKey
                + "&localidad=" + idCiudad
                + "&format=json";

        return restTemplate.getForObject(url, String.class);
    }

    private String obtenerIdCiudad(String ciudad) {

        if (ciudad == null) return null;

        switch (ciudad.toLowerCase()) {
            case "santiago":
                return "18578";
            case "valparaiso":
                return "18577";
            case "concepcion":
                return "18579";
            default:
                return null;
        }
    }
}
