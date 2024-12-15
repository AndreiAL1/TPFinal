package cal335.projet.mes_chums;

import cal335.projet.mes_chums.controleur.AdresseControleur;
import cal335.projet.mes_chums.controleur.ContactControleur;
import cal335.projet.mes_chums.controleur.ProximiteControleur;
import cal335.projet.mes_chums.dao.AdresseDAO;
import cal335.projet.mes_chums.dao.ContactDAO;
import cal335.projet.mes_chums.service.AdresseService;
import cal335.projet.mes_chums.service.ContactService;
import cal335.projet.mes_chums.service.ProximiteService;
import cal335.projet.mes_chums.service.ApiGeocodage;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ApplicationHttpServer {
    public static void main(String[] args) {
        try {
          
            Connection connection = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");

            
            ContactDAO contactDAO = new ContactDAO(connection);
            AdresseDAO adresseDAO = new AdresseDAO(connection);

            
            ApiGeocodage apiGeocodage = new ApiGeocodage();
            ContactService contactService = new ContactService(contactDAO);
            AdresseService adresseService = new AdresseService(contactDAO, adresseDAO, apiGeocodage);
            ProximiteService proximiteService = new ProximiteService(contactDAO);

           
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            
            server.createContext("/contacts", new ContactControleur(contactService));
            server.createContext("/adresse", new AdresseControleur(adresseService));
            server.createContext("/contacts/proximite", new ProximiteControleur(proximiteService));

            
            server.setExecutor(null); 
            server.start();

            System.out.println("HTTP Server started on port 8080");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}