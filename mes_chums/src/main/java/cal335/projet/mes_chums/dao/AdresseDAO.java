package cal335.projet.mes_chums.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cal335.projet.mes_chums.modele.Adresse;
import cal335.projet.mes_chums.modele.Coordonnees;


public class AdresseDAO implements DAOGenerique<Adresse> {
    private Connection connection;

    public AdresseDAO() {}

    public AdresseDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void ajouter(Adresse adresse) {
        String sql = "INSERT INTO adresses (rue, ville, codePostal, pays) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, adresse.getRue());
            statement.setString(2, adresse.getVille());
            statement.setString(3, adresse.getCodePostal());
            statement.setString(4, adresse.getPays());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(Adresse adresse) {
        String sql = "DELETE FROM adresses WHERE id_adresse = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, adresse.getId_adresse());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
public List<Adresse> trouverTous() {
    List<Adresse> adresses = new ArrayList<>();
    String sql = "SELECT * FROM adresses";

    try (PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
            Adresse adresse = new Adresse();
            adresse.setId_adresse(resultSet.getInt("id_adresse"));
            adresse.setRue(resultSet.getString("rue"));
            adresse.setVille(resultSet.getString("ville"));
            adresse.setCodePostal(resultSet.getString("codePostal"));
            adresse.setPays(resultSet.getString("pays"));

           
            double latitude = resultSet.getDouble("latitude");
            double longitude = resultSet.getDouble("longitude");
            adresse.setCoordonnees(new Coordonnees(latitude, longitude));

            adresses.add(adresse);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return adresses;
}

    @Override
    public void mettreAJour(Adresse adresse) {
        String sql = "UPDATE adresses SET rue = ?, ville = ?, codePostal = ?, pays = ? WHERE id_adresse = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, adresse.getRue());
            statement.setString(2, adresse.getVille());
            statement.setString(3, adresse.getCodePostal());
            statement.setString(4, adresse.getPays());
            statement.setInt(5, adresse.getId_adresse());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Adresse trouverParId(Integer id) {
        String sql = "SELECT * FROM adresses WHERE id_adresse = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Adresse adresse = new Adresse();
                    adresse.setId_adresse(resultSet.getInt("id_adresse"));
                    adresse.setRue(resultSet.getString("rue"));
                    adresse.setVille(resultSet.getString("ville"));
                    adresse.setCodePostal(resultSet.getString("codePostal"));
                    adresse.setPays(resultSet.getString("pays"));
                    return adresse;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
