package cal335.projet.mes_chums.service;

import cal335.projet.mes_chums.dao.ContactDAO;
import cal335.projet.mes_chums.dto.ContactDTO;
import cal335.projet.mes_chums.modele.Adresse;
import cal335.projet.mes_chums.modele.Contact;
import cal335.projet.mes_chums.modele.Coordonnees;

import java.util.ArrayList;
import java.util.List;

public class ProximiteService {
    private final ContactDAO contactDAO;

    public ProximiteService(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

  
    public List<ContactDTO> findNearbyContacts(double latitude, double longitude, double radius) {
        List<Contact> allContacts = contactDAO.trouverTous(); 
        List<ContactDTO> nearbyContacts = new ArrayList<>();  

        for (Contact contact : allContacts) {
            if (contact.getAdresses() != null && !contact.getAdresses().isEmpty()) {
                for (Adresse adresse : contact.getAdresses()) {
                    if (adresse.getCoordonnees() != null) {
                        Coordonnees coord = adresse.getCoordonnees();
                        double distance = calculateDistance(latitude, longitude, coord.getLatitude(), coord.getLongitude());

                        if (distance <= radius) {
                            nearbyContacts.add(new ContactDTO(
                                    contact.getId(),
                                    contact.getNom(),
                                    contact.getPrenom(),
                                    contact.isFavoris()
                            ));
                        }
                    }
                }
            }
        }

        return nearbyContacts;
    }

   
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371; 

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; 
    }
}
