package cal335.projet.mes_chums.service;

import cal335.projet.mes_chums.modele.Coordonnees;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiGeocodage {
    private static final String API_URL = "api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=0f3548c9d3fbbc012dd7f109542bb0d3"; 
    private static final String API_KEY = "0f3548c9d3fbbc012dd7f109542bb0d3"; 

  
    public Coordonnees obtenirCoordonnees(String adresse) {
        try {
           
            String query = API_URL + "?access_key=" + API_KEY + "&query=" + adresse.replace(" ", "%20");

            
            URI uri = new URI(query);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); 
            connection.setReadTimeout(5000);

            
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Failed to get response from geocoding API: " + responseCode);
            }

            
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.toString());
            JsonNode dataNode = rootNode.path("data").get(0);

           
            double latitude = dataNode.path("latitude").asDouble();
            double longitude = dataNode.path("longitude").asDouble();

            
            return new Coordonnees(latitude, longitude);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}