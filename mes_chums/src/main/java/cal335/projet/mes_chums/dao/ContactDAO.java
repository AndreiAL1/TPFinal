package cal335.projet.mes_chums.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cal335.projet.mes_chums.modele.Contact;

public class ContactDAO implements DAOGenerique<Contact> {
    private Connection connection;

    public ContactDAO() {}

   

    public ContactDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void ajouter(Contact contact) {
        String sql = "INSERT INTO contacts (nom, prenom, isFavoris) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, contact.getNom());
            statement.setString(2, contact.getPrenom());
            statement.setBoolean(3, contact.isFavoris());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(Contact contact) {
        String sql = "DELETE FROM contacts WHERE id_contact = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, contact.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mettreAJour(Contact contact) {
        String sql = "UPDATE contacts SET nom = ?, prenom = ?, isFavoris = ? WHERE id_contact = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, contact.getNom());
            statement.setString(2, contact.getPrenom());
            statement.setBoolean(3, contact.isFavoris());
            statement.setInt(4, contact.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
public List<Contact> trouverTous() {
    List<Contact> contacts = new ArrayList<>();
    String sql = "SELECT * FROM contacts";

    try (PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
            Contact contact = new Contact();
            contact.setId(resultSet.getInt("id_contact"));
            contact.setNom(resultSet.getString("nom"));
            contact.setPrenom(resultSet.getString("prenom"));
            contact.setFavoris(resultSet.getBoolean("isFavoris"));

            
            contacts.add(contact);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return contacts;
}

    @Override
    public Contact trouverParId(Integer id) {
        String sql = "SELECT * FROM contacts WHERE id_contact = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Contact contact = new Contact();
                    contact.setId(resultSet.getInt("id_contact"));
                    contact.setNom(resultSet.getString("nom"));
                    contact.setPrenom(resultSet.getString("prenom"));
                    contact.setFavoris(resultSet.getBoolean("isFavoris"));
                    return contact;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
