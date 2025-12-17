package cl.duoc.ms_products_db.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import java.util.Map;

@Service
public class ClimaService {

    // Inyectamos la clave desde application.properties o variables de entorno
    @Value("${weather.api.key}")
    private String apiKey;

    private final String API_URL = "https://api.openweathermap.org/data/2.5/weather";

    public Map<String, Object> obtenerClima(String ciudad) {
        RestTemplate restTemplate = new RestTemplate();
        
        // Construimos la URL segura (la API Key se queda en el servidor)
        String url = String.format("%s?q=%s,CL&units=metric&lang=es&appid=%s", 
                                   API_URL, ciudad, apiKey);

        try {
            // Hacemos la petición a OpenWeatherMap y devolvemos el mapa genérico
            return restTemplate.getForObject(url, Map.class);
        } catch (HttpClientErrorException e) {
            // Manejo básico de errores si la ciudad no existe
            return null;
        }
    }
}
