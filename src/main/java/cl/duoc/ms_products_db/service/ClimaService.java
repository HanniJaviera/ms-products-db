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

    private final String API_KEY = "404fe577ee8921c5b902a764daca8b81eed6e5c30f6bf18eee2585a3d7f112d3";
    
    private final String BASE_URL = "https://api.meteored.com";

    @SuppressWarnings("unchecked")
    public Map<String, Object> obtenerClima(String ciudad) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> respuestaFinal = new HashMap<>();

        try {
            if (ciudad == null || ciudad.trim().isEmpty()) {
                System.out.println("ClimaService: Ciudad nula o vacía");
                return null;
            }

            String ciudadEncoded = URLEncoder.encode(ciudad.trim(), StandardCharsets.UTF_8.toString());

            String searchUrl = String.format("%s/api/location/v1/search/txt/%s?apikey=%s",
                    BASE_URL, ciudadEncoded, API_KEY);
            Object respuestaSearch = restTemplate.getForObject(searchUrl, Object.class);

            if (!(respuestaSearch instanceof List)) {
                System.err.println("ClimaService Error: La API de búsqueda no devolvió una lista. Respuesta: " + respuestaSearch);
                return null;
            }

            List<Map<String, Object>> listaResultados = (List<Map<String, Object>>) respuestaSearch;

            if (listaResultados.isEmpty()) {
                System.out.println("ClimaService: No se encontraron resultados para '" + ciudad + "'");
                return null;
            }

            Map<String, Object> primerResultado = listaResultados.get(0);
            
            Object idObj = primerResultado.get("id");
            if (idObj == null) idObj = primerResultado.get("key");
            if (idObj == null) idObj = primerResultado.get("Key");

            if (idObj == null) {
                System.err.println("ClimaService Error: Ubicación encontrada pero sin ID válido.");
                return null;
            }

            String idCiudad = idObj.toString();
            
            String nombreCiudad = ciudad;
            if (primerResultado.get("name") != null) {
                nombreCiudad = (String) primerResultado.get("name");
            }

            String weatherUrl = String.format("%s/api/weather/current/%s?apikey=%s",
                    BASE_URL, idCiudad, API_KEY);

            Object respuestaWeather = restTemplate.getForObject(weatherUrl, Object.class);
            Map<String, Object> datosClima = null;

            if (respuestaWeather instanceof List) {
                List<Map<String, Object>> lista = (List<Map<String, Object>>) respuestaWeather;
                if (!lista.isEmpty()) datosClima = lista.get(0);
            } else if (respuestaWeather instanceof Map) {
                datosClima = (Map<String, Object>) respuestaWeather;
            }

            if (datosClima == null) {
                System.err.println("ClimaService Error: Datos del clima vacíos.");
                return null;
            }

            Object temp = datosClima.get("temp");
            if (temp == null && datosClima.get("main") instanceof Map) {
                temp = ((Map<String, Object>) datosClima.get("main")).get("temp");
            }
            if (temp == null && datosClima.get("Temperature") instanceof Map) {
                Map<String, Object> t = (Map<String, Object>) datosClima.get("Temperature");
                if (t.get("Metric") instanceof Map) {
                    temp = ((Map<String, Object>) t.get("Metric")).get("Value");
                }
            }

            String desc = "Clima actual";
            if (datosClima.get("weather") instanceof String) {
                desc = (String) datosClima.get("weather");
            } else if (datosClima.get("weather") instanceof List) {
                List<?> wList = (List<?>) datosClima.get("weather");
                if (!wList.isEmpty() && wList.get(0) instanceof Map) {
                    Map<?, ?> wMap = (Map<?, ?>) wList.get(0);
                    if (wMap.get("description") != null) {
                        desc = (String) wMap.get("description");
                    }
                }
            } else if (datosClima.get("WeatherText") != null) {
                desc = (String) datosClima.get("WeatherText");
            }

            Object icon = "01d";
            if (datosClima.get("icon") != null) {
                icon = datosClima.get("icon");
            }
            
            if (datosClima.get("WeatherIcon") != null) icon = datosClima.get("WeatherIcon");
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
            System.err.println("!!! EXCEPCIÓN EN ClimaService !!!");
            e.printStackTrace();
            return null;
        }
    }
}


