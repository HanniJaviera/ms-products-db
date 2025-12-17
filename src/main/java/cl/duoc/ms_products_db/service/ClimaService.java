package cl.duoc.ms_products_db.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClimaService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final String BASE_URL = "https://api.meteored.com";

    public Map<String, Object> obtenerClima(String ciudad) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> respuestaFinal = new HashMap<>();

        try {
            // Buscar ID de la ciudad
            String searchUrl = String.format("%s/api/location/v1/search/txt/%s?apikey=%s",
                    BASE_URL, ciudad, apiKey);

            // Meteored devuelve una Lista de resultados
            List<Map<String, Object>> searchResult = restTemplate.getForObject(searchUrl, List.class);

            if (searchResult == null || searchResult.isEmpty()) {
                return null; // Ciudad no encontrada
            }

            // Toma el primer resultado
            Map<String, Object> location = searchResult.get(0);
            
            // Intenta obtener el ID
            Object idObj = location.get("id");
            if (idObj == null) idObj = location.get("key"); // Fallback
            
            if (idObj == null) return null;

            String locationId = idObj.toString();
            String locationName = (String) location.get("name");

            //Obtener datos del clima actual
            String weatherUrl = String.format("%s/api/weather/current/%s?apikey=%s",
                    BASE_URL, locationId, apiKey);

            Map<String, Object> weatherData = restTemplate.getForObject(weatherUrl, Map.class);
            
            if (weatherData == null) return null;

            // normalizar datos clave
            
            // 1. Temperatura (Meteored suele enviarla en 'temp' o dentro de 'main')
            Object temp = weatherData.get("temp");
            if (temp == null && weatherData.containsKey("main")) {
                Map<String, Object> main = (Map<String, Object>) weatherData.get("main");
                temp = main.get("temp");
            }

            // 2. Descripción y Icono
            String description = (String) weatherData.getOrDefault("weather", "Parcialmente nublado");
            Object icon = weatherData.getOrDefault("symbol", "01d"); // Default icon

            // Construimos una respuesta tipo "OpenWeather" para mantener compatibilidad
            Map<String, Object> mainBlock = new HashMap<>();
            mainBlock.put("temp", temp != null ? temp : 0);

            Map<String, Object> weatherBlock = new HashMap<>();
            weatherBlock.put("description", description);
            weatherBlock.put("icon", icon);

            respuestaFinal.put("name", locationName);
            respuestaFinal.put("main", mainBlock);
            respuestaFinal.put("weather", List.of(weatherBlock)); // Array de 1 elemento

            return respuestaFinal;

        } catch (HttpClientErrorException e) {
            System.err.println("Error cliente HTTP: " + e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
