package cl.duoc.ms_products_db.service;

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

    private final String API_KEY = "8ddadecc7ae4f56fee73b2b405a63659"; 
    
    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    @SuppressWarnings("unchecked")
    public Map<String, Object> obtenerClima(String ciudad) {
        RestTemplate restTemplate = new RestTemplate();
        
        try {
            // 1. Validar entrada
            if (ciudad == null || ciudad.trim().isEmpty()) {
                System.out.println("ClimaService: Ciudad vacía.");
                return null;
            }

            String ciudadEncoded = URLEncoder.encode(ciudad.trim(), StandardCharsets.UTF_8.toString());


            String url = String.format("%s?q=%s&appid=%s&units=metric&lang=es", 
                    BASE_URL, ciudadEncoded, API_KEY);

            Map<String, Object> respuestaApi = restTemplate.getForObject(url, Map.class);

            if (respuestaApi == null) return null;


            String nombre = (String) respuestaApi.get("name");


            Map<String, Object> mainData = (Map<String, Object>) respuestaApi.get("main");
            Object temp = (mainData != null) ? mainData.get("temp") : 0;

            List<Map<String, Object>> weatherList = (List<Map<String, Object>>) respuestaApi.get("weather");
            String descripcion = "Clima actual";
            String icono = "01d";
            
            if (weatherList != null && !weatherList.isEmpty()) {
                Map<String, Object> w = weatherList.get(0);
                if (w.get("description") != null) descripcion = (String) w.get("description");
                if (w.get("icon") != null) icono = (String) w.get("icon");
            }

            Map<String, Object> respuestaFinal = new HashMap<>();
            
            Map<String, Object> mainBlock = new HashMap<>();
            mainBlock.put("temp", temp);

            Map<String, Object> weatherBlock = new HashMap<>();
            weatherBlock.put("description", descripcion);
            weatherBlock.put("icon", icono);

            respuestaFinal.put("name", nombre);
            respuestaFinal.put("main", mainBlock);
            respuestaFinal.put("weather", Collections.singletonList(weatherBlock));

            return respuestaFinal;

        } catch (Exception e) {
            System.err.println("Error en ClimaService (OpenWeather): " + e.getMessage());
            return null;
        }
    }
}

