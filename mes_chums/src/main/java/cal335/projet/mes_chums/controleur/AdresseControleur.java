package cal335.projet.mes_chums.controleur;

import cal335.projet.mes_chums.dto.AdresseDTO;
import cal335.projet.mes_chums.modele.Adresse;
import cal335.projet.mes_chums.modele.Coordonnees;
import cal335.projet.mes_chums.service.AdresseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AdresseControleur implements HttpHandler {
    private final AdresseService adresseService;

    public AdresseControleur(AdresseService adresseService) {
        this.adresseService = adresseService;
    }

   @Override
public void handle(HttpExchange exchange) throws IOException {
    if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
        try {
           
            String query = exchange.getRequestURI().getQuery();
            int contactId = Integer.parseInt(query.split("=")[1]);

            
            InputStream inputStream = exchange.getRequestBody();
            ObjectMapper mapper = new ObjectMapper();
            AdresseDTO adresseDTO = mapper.readValue(inputStream, AdresseDTO.class);

            
            Coordonnees coordonnees = null;
            if (adresseDTO.getCoordonnees() != null) {
                coordonnees = new Coordonnees(
                    adresseDTO.getCoordonnees().getLatitude(),
                    adresseDTO.getCoordonnees().getLongitude()
                );
            }
            Adresse adresse = new Adresse(
                null, 
                adresseDTO.getRue(),
                adresseDTO.getVille(),
                adresseDTO.getCodePostal(),
                adresseDTO.getPays(),
                coordonnees
            );

           
            adresseService.ajouterAdresse(contactId, adresse);

            
            String response = "Address added successfully.";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            String response = "Error processing the request.";
            exchange.sendResponseHeaders(500, response.getBytes().length);
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }
    } else {
        exchange.sendResponseHeaders(405, -1); 
    }
}
}