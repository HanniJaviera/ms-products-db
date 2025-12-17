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

    @Value("${weather.api.key:404fe577ee8921c5b902a764daca8b81eed6e5c30f6bf18eee2585a3d7f112d3}")
    private String apiKey;

    private final String BASE_URL = "https://api.meteored.com";

    @SuppressWarnings("unchecked")
    public Map<String, Object> obtenerClima(String ciudad) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> respuestaFinal = new HashMap<>();

        try {

            if (ciudad == null || ciudad.trim().isEmpty()) return null;


            String ciudadEncoded = URLEncoder.encode(ciudad.trim(), StandardCharsets.UTF_8.toString());

            String searchUrl = String.format("%s/api/location/v1/search/txt/%s?apikey=%s",
                    BASE_URL, ciudadEncoded, apiKey);

            Object respuestaSearch = restTemplate.getForObject(searchUrl, Object.class);

            if (!(respuestaSearch instanceof List)) {
                System.err.println("Error API Search: Respuesta no es una lista válida.");
                return null;
            }

            List<Map<String, Object>> listaResultados = (List<Map<String, Object>>) respuestaSearch;

            if (listaResultados.isEmpty()) {
                System.out.println("No se encontró la locación: " + ciudad);
                return null;
            }

            Map<String, Object> primerResultado = listaResultados.get(0);
            

            Object idObj = primerResultado.get("id");
            if (idObj == null) idObj = primerResultado.get("key");
            if (idObj == null) idObj = primerResultado.get("Key");

            if (idObj == null) {
                System.err.println("Locación encontrada pero sin ID válido.");
                return null;
            }

            String idCiudad = idObj.toString();
            String nombreCiudad = (String) primerResultado.getOrDefault("name", ciudad);


            String weatherUrl = String.format("%s/api/weather/current/%s?apikey=%s",
                    BASE_URL, idCiudad, apiKey);


            Object respuestaWeather = restTemplate.getForObject(weatherUrl, Object.class);
            Map<String, Object> datosClima = null;

            if (respuestaWeather instanceof List) {
                List<Map<String, Object>> listaClima = (List<Map<String, Object>>) respuestaWeather;
                if (!listaClima.isEmpty()) {
                    datosClima = listaClima.get(0);
                }
            } else if (respuestaWeather instanceof Map) {
                datosClima = (Map<String, Object>) respuestaWeather;
            }

            if (datosClima == null) return null;

            Object temp = datosClima.get("temp");
            if (temp == null && datosClima.containsKey("main")) {
                temp = ((Map<String, Object>) datosClima.get("main")).get("temp");
            }
            if (temp == null && datosClima.containsKey("Temperature")) {
                Map<String, Object> t = (Map<String, Object>) datosClima.get("Temperature");
                if (t != null) {
                    Map<String, Object> m = (Map<String, Object>) t.get("Metric");
                    if (m != null) temp = m.get("Value");
                }
            }


            String desc = (String) datosClima.getOrDefault("weather", "Clima actual");
            if (datosClima.containsKey("WeatherText")) {
                desc = (String) datosClima.get("WeatherText");
            } else if (datosClima.containsKey("weather") && datosClima.get("weather") instanceof List) {
                 List<?> wList = (List<?>) datosClima.get("weather");
                 if (!wList.isEmpty() && wList.get(0) instanceof Map) {
                     desc = (String) ((Map<?,?>) wList.get(0)).getOrDefault("description", desc);
                 }
            }


            Object icon = datosClima.getOrDefault("icon", "01d");
            if (datosClima.containsKey("WeatherIcon")) icon = datosClima.get("WeatherIcon");


            Map<String, Object> mainBlock = new HashMap<>();
            mainBlock.put("temp", temp != null ? temp : 0);

            Map<String, Object> weatherBlock = new HashMap<>();
            weatherBlock.put("description", desc);
            weatherBlock.put("icon", icon);

            respuestaFinal.put("name", nombreCiudad);
            respuestaFinal.put("main", mainBlock);
            respuestaFinal.put("weather", Collections.singletonList(weatherBlock));

            return respuestaFinal;

        } catch (Exception e) {
            System.err.println("Error en ClimaService: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}