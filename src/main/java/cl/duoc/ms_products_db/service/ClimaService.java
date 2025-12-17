package cl.duoc.ms_products_db.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClimaService {

    @Value("${meteored.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public Double obtenerTemperaturaActual(String ciudad) {

        // 1️⃣ Buscar ciudad → HASH
        String searchUrl =
            "https://api.meteored.com/api/location/v1/search/txt/" + ciudad;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Accept", "application/json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> searchResponse = restTemplate.exchange(
            searchUrl,
            HttpMethod.GET,
            entity,
            Map.class
        );

        Map data = (Map) searchResponse.getBody().get("data");
        List locations = (List) data.get("locations");

        if (locations.isEmpty()) {
            throw new RuntimeException("Ciudad no encontrada");
        }

        Map firstLocation = (Map) locations.get(0);
        String hash = (String) firstLocation.get("hash");

        // 2️⃣ Clima por hash
        String climaUrl =
            "https://api.meteored.com/api/forecast/v1/hourly/" + hash;

        ResponseEntity<Map> climaResponse = restTemplate.exchange(
            climaUrl,
            HttpMethod.GET,
            entity,
            Map.class
        );

        Map climaData = (Map) climaResponse.getBody().get("data");
        List hours = (List) climaData.get("hours");

        Map firstHour = (Map) hours.get(0);

        return Double.valueOf(firstHour.get("temperature").toString());
    }
}
