package cal335.projet.mes_chums.dto;

public class CoordonneesDTO {
    private double latitude;
    private double longitude;

    
    public CoordonneesDTO() {}

    public CoordonneesDTO(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}
