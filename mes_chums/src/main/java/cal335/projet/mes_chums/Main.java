package cal335.projet.mes_chums;

import cal335.projet.mes_chums.dao.AdresseDAO;
import cal335.projet.mes_chums.dao.ContactDAO;
import cal335.projet.mes_chums.service.AdresseService;
import cal335.projet.mes_chums.service.ContactService;
import cal335.projet.mes_chums.service.ProximiteService;
import cal335.projet.mes_chums.service.ApiGeocodage;
import cal335.projet.mes_chums.controleur.ContactControleur;
import cal335.projet.mes_chums.controleur.AdresseControleur;
import cal335.projet.mes_chums.controleur.ProximiteControleur;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        try {
            
            ContactDAO contactDAO = new ContactDAO();
            AdresseDAO adresseDAO = new AdresseDAO();

            
            ApiGeocodage apiGeocodage = new ApiGeocodage();

            
            ContactService contactService = new ContactService(contactDAO);
            AdresseService adresseService = new AdresseService(contactDAO, adresseDAO, apiGeocodage);
            ProximiteService proximiteService = new ProximiteService(contactDAO);

            
            ContactControleur contactControleur = new ContactControleur(contactService);
            AdresseControleur adresseControleur = new AdresseControleur(adresseService);
            ProximiteControleur proximiteControleur = new ProximiteControleur(proximiteService);

            
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            
            server.createContext("/contacts", contactControleur);
            server.createContext("/adresse", adresseControleur);
            server.createContext("/contacts/proximite", proximiteControleur);

            
            server.start();

            
            System.out.println("Server is running on port 8080...");
        } catch (Exception e) {
            System.err.println("Error starting the server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}