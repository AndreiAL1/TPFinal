package cal335.projet.mes_chums.dto;


public class ContactDTO {
    private Integer id;
    private String nom;
    private String prenom;
    private boolean isFavoris;

    
    public ContactDTO(Integer id, String nom, String prenom, boolean isFavoris) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.isFavoris = isFavoris;
    }

   
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
