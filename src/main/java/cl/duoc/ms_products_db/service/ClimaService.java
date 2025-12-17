package cl.duoc.ms_products_db.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClimaService {

    // Tu API Key de Meteored (La ponemos aquí por defecto para que no falle)
    @Value("${weather.api.key:404fe577ee8921c5b902a764daca8b81eed6e5c30f6bf18eee2585a3d7f112d3}")
    private String apiKey;

    private final String BASE_URL = "https://api.meteored.com";

    public Map<String, Object> obtenerClima(String ciudad) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> respuestaFinal = new HashMap<>();

        try {
            if (ciudad == null || ciudad.trim().isEmpty()) return null;

            String ciudadCodificada = URLEncoder.encode(ciudad.trim(), "UTF-8");
            
            String urlBusqueda = BASE_URL + "/api/location/v1/search/txt/" + ciudadCodificada + "?apikey=" + apiKey;

            Object respuestaBusqueda = restTemplate.getForObject(urlBusqueda, Object.class);

            if (!(respuestaBusqueda instanceof List)) {
                System.out.println("Error: La búsqueda no devolvió una lista válida.");
                return null;
            }

            List<Map<String, Object>> listaResultados = (List<Map<String, Object>>) respuestaBusqueda;

            if (listaResultados.isEmpty()) {
                System.out.println("Ciudad no encontrada: " + ciudad);
                return null;
            }

            Map<String, Object> primerResultado = listaResultados.get(0);
            
            Object idObj = primerResultado.get("id");
            if (idObj == null) idObj = primerResultado.get("key");

            if (idObj == null) return null; 

            String idCiudad = idObj.toString();
            String nombreCiudad = (String) primerResultado.get("name");

            String urlClima = BASE_URL + "/api/weather/current/" + idCiudad + "?apikey=" + apiKey;

            Object respuestaClima = restTemplate.getForObject(urlClima, Object.class);
            Map<String, Object> datosClima = null;

            if (respuestaClima instanceof List) {
                List<Map<String, Object>> lista = (List<Map<String, Object>>) respuestaClima;
                if (!lista.isEmpty()) datosClima = lista.get(0);
            } else if (respuestaClima instanceof Map) {
                datosClima = (Map<String, Object>) respuestaClima;
            }

            if (datosClima == null) return null;


            Object temperatura = datosClima.get("temp");
            if (temperatura == null && datosClima.get("main") != null) {
                Map<String, Object> main = (Map<String, Object>) datosClima.get("main");
                temperatura = main.get("temp");
            }

            Object descripcion = datosClima.getOrDefault("weather", "Clima actual");
            
            Object icono = datosClima.getOrDefault("icon", "01d");

            Map<String, Object> bloqueMain = new HashMap<>();
            bloqueMain.put("temp", temperatura);

            Map<String, Object> bloqueWeather = new HashMap<>();
            bloqueWeather.put("description", descripcion);
            bloqueWeather.put("icon", icono);

            respuestaFinal.put("name", nombreCiudad);
            respuestaFinal.put("main", bloqueMain);
            respuestaFinal.put("weather", Collections.singletonList(bloqueWeather));

            return respuestaFinal;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}