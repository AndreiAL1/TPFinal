package cal335.projet.mes_chums.modele;

import java.util.List;

public class Contact {
    private Integer idContact;
    private String nom;
    private String prenom;
    private boolean isFavoris;
    private List<Adresse> adresses;

    public Contact() {}

    public Contact(Integer idContact, String nom, String prenom, boolean isFavoris, List<Adresse> adresses) {
        this.idContact = idContact;
        this.nom = nom;
        this.prenom = prenom;
        this.isFavoris = isFavoris;
        this.adresses = adresses;
    }

    public Integer getId() { 
        return idContact;
    }
    public void setId(Integer idContact) {
        this.idContact = idContact;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public boolean isFavoris() {
        return isFavoris;
    }
    public void setFavoris(boolean favoris) {
        isFavoris = favoris;
    }

    public List<Adresse> getAdresses() {
        return adresses;
    }
    public void setAdresses(List<Adresse> adresses) {
        this.adresses = adresses;
    }

    
    public Adresse getPrimaryAdresse() {
        if (adresses != null && !adresses.isEmpty()) {
            return adresses.get(0); 
        }
        return null;
    }
}