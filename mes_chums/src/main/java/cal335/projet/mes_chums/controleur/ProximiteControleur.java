package cal335.projet.mes_chums.controleur;

import cal335.projet.mes_chums.dto.ContactDTO;
import cal335.projet.mes_chums.service.ProximiteService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.List;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ProximiteControleur implements HttpHandler {
    private final ProximiteService proximiteService;
    private final ObjectMapper objectMapper;

    public ProximiteControleur(ProximiteService proximiteService) {
        this.proximiteService = proximiteService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            handleFindNearbyContacts(exchange);
        } else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, -1);
        }
    }

    private void handleFindNearbyContacts(HttpExchange exchange) throws IOException {
        
        String query = exchange.getRequestURI().getQuery();
        double latitude = 0.0;
        double longitude = 0.0;
        double radius = 0.0;

        try {
            String[] params = query.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                switch (keyValue[0]) {
                    case "latitude":
                        latitude = Double.parseDouble(keyValue[1]);
                        break;
                    case "longitude":
                        longitude = Double.parseDouble(keyValue[1]);
                        break;
                    case "radius":
                        radius = Double.parseDouble(keyValue[1]);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
            return;
        }

       
        List<ContactDTO> nearbyContacts = proximiteService.findNearbyContacts(latitude, longitude, radius);

        
        String jsonResponse = objectMapper.writeValueAsString(nearbyContacts);

        
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, jsonResponse.getBytes().length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(jsonResponse.getBytes());
        }
    }
}
