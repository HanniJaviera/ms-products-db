package cl.duoc.ms_products_db.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClimaService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final String BASE_URL = "https://api.meteored.com";

    @SuppressWarnings("unchecked")
    public Map<String, Object> obtenerClima(String ciudad) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> respuestaFinal = new HashMap<>();

        try {

            String ciudadEncoded = URLEncoder.encode(ciudad, StandardCharsets.UTF_8.toString());
            String searchUrl = String.format("%s/api/location/v1/search/txt/%s?apikey=%s",
                    BASE_URL, ciudadEncoded, apiKey);


            System.out.println("Backend consultando a Meteored: " + searchUrl);


            List<Map<String, Object>> searchResult = (List<Map<String, Object>>) restTemplate.getForObject(searchUrl, List.class);

            if (searchResult == null || searchResult.isEmpty()) {
                System.out.println("Meteored no encontró la ciudad: " + ciudad);
                return null;
            }

            Map<String, Object> location = searchResult.get(0);
            

            Object idObj = location.get("id");
            if (idObj == null) idObj = location.get("key");
            
            if (idObj == null) return null;

            String locationId = idObj.toString();
            String locationName = (String) location.get("name");
            String weatherUrl = String.format("%s/api/weather/current/%s?apikey=%s",
                    BASE_URL, locationId, apiKey);


            Map<String, Object> weatherData = (Map<String, Object>) restTemplate.getForObject(weatherUrl, Map.class);
            
            if (weatherData == null) return null;

            Object temp = weatherData.get("temp");

            if (temp == null && weatherData.containsKey("main")) {
                Map<String, Object> main = (Map<String, Object>) weatherData.get("main");
                if (main != null) temp = main.get("temp");
            }

            if (temp == null && weatherData.containsKey("Temperature")) {
                 Map<String, Object> temperature = (Map<String, Object>) weatherData.get("Temperature");
                 Map<String, Object> metric = (Map<String, Object>) temperature.get("Metric");
                 if (metric != null) temp = metric.get("Value");
            }

            String description = (String) weatherData.getOrDefault("weather", "Clima actual");
            if (weatherData.containsKey("WeatherText")) {
                description = (String) weatherData.get("WeatherText");
            }

            Object icon = weatherData.getOrDefault("symbol", "01d");
            if (weatherData.containsKey("WeatherIcon")) {
                icon = weatherData.get("WeatherIcon");
            }


            Map<String, Object> mainBlock = new HashMap<>();
            mainBlock.put("temp", temp != null ? temp : 0);

            Map<String, Object> weatherBlock = new HashMap<>();
            weatherBlock.put("description", description);
            weatherBlock.put("icon", icon);

            respuestaFinal.put("name", locationName);
            respuestaFinal.put("main", mainBlock);
            respuestaFinal.put("weather", Collections.singletonList(weatherBlock));

            return respuestaFinal;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}