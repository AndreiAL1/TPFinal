package cal335.projet.mes_chums.dto;

public class AdresseDTO {
    private Integer idAdresse;
    private String rue;
    private String ville;
    private String codePostal;
    private String pays;
    private CoordonneesDTO coordonnees;

    
    public AdresseDTO() {}

    public AdresseDTO(Integer idAdresse, String rue, String ville, String codePostal, String pays, CoordonneesDTO coordonnees) {
        this.idAdresse = idAdresse;
        this.rue = rue;
        this.ville = ville;
        this.codePostal = codePostal;
        this.pays = pays;
        this.coordonnees = coordonnees;
    }

    
    public Integer getIdAdresse() { return idAdresse; }
    public void setIdAdresse(Integer idAdresse) { this.idAdresse = idAdresse; }

    public String getRue() { return rue; }
    public void setRue(String rue) { this.rue = rue; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getCodePostal() { return codePostal; }
    public void setCodePostal(String codePostal) { this.codePostal = codePostal; }

    public String getPays() { return pays; }
    public void setPays(String pays) { this.pays = pays; }

    public CoordonneesDTO getCoordonnees() { return coordonnees; }
    public void setCoordonnees(CoordonneesDTO coordonnees) { this.coordonnees = coordonnees; }
}