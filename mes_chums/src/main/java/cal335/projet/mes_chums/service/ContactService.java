package cal335.projet.mes_chums.service;

import cal335.projet.mes_chums.dao.ContactDAO;
import cal335.projet.mes_chums.dto.ContactDTO;


import java.util.ArrayList;
import java.util.List;

public class ContactService {

    private final ContactDAO contactDAO;

    public ContactService(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    private final List<ContactDTO> contacts = new ArrayList<>(); 

   
    public List<ContactDTO> getAllContacts() {
        return contacts;
    }

    
    public void addContact(ContactDTO contact) {
        contacts.add(contact);
    }

    
    public void updateContact(int id, ContactDTO updatedContact) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId() == id) {
                contacts.set(i, updatedContact);
                return;
            }
        }
    }

  
    public void deleteContact(int id) {
        contacts.removeIf(contact -> contact.getId() == id);
    }
}