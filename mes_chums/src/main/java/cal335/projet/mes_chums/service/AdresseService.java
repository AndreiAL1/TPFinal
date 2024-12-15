package cal335.projet.mes_chums.service;

import cal335.projet.mes_chums.dao.AdresseDAO;
import cal335.projet.mes_chums.dao.ContactDAO;
import cal335.projet.mes_chums.modele.Adresse;
import cal335.projet.mes_chums.modele.Contact;
import cal335.projet.mes_chums.modele.Coordonnees;

public class AdresseService {
    private final ContactDAO contactDAO;
    private final AdresseDAO adresseDAO;
    private final ApiGeocodage apiGeocodage;

    public AdresseService(ContactDAO contactDAO, AdresseDAO adresseDAO, ApiGeocodage apiGeocodage) {
        this.contactDAO = contactDAO;
        this.adresseDAO = adresseDAO;
        this.apiGeocodage = apiGeocodage;
    }

    

    public void ajouterAdresse(Integer idContact, Adresse adresse) {
        Contact contact = contactDAO.trouverParId(idContact);

        if (contact == null) {
            throw new IllegalArgumentException("Contact not found with ID: " + idContact);
        }

       
        Coordonnees coordonnees = apiGeocodage.obtenirCoordonnees(
            adresse.getRue() + ", " + adresse.getVille() + ", " + adresse.getCodePostal() + ", " + adresse.getPays()
        );

        if (coordonnees != null) {
            adresse.setCoordonnees(coordonnees);
        } else {
            System.out.println("Unable to fetch coordinates for the given address.");
        }

        
        adresseDAO.ajouter(adresse);
    }
}