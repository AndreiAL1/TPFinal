package cal335.projet.mes_chums.cache;

import cal335.projet.mes_chums.dto.ContactDTO;
import cal335.projet.mes_chums.service.ContactService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FavoriteContactsCache {

    private final Map<ContactDTO, List<ContactDTO>> cache = new HashMap<>();
    private final ContactService contactService;

    public FavoriteContactsCache(ContactService contactService) {
        this.contactService = contactService;
        initializeCache();
    }

   
    private void initializeCache() {
        List<ContactDTO> allContacts = contactService.getAllContacts();

        List<ContactDTO> favoriteContacts = allContacts.stream()
            .filter(ContactDTO::isFavoris) 
            .collect(Collectors.toList());

        for (ContactDTO contact : favoriteContacts) {
            cache.put(contact, favoriteContacts); 
        }
    }

   
    public void addFavorite(ContactDTO contact) {
        if (contact.isFavoris()) {
            cache.put(contact, List.of(contact));
        }
    }

   
    public void removeFavorite(ContactDTO contact) {
        cache.remove(contact);
    }

   
    public Map<ContactDTO, List<ContactDTO>> getCache() {
        return cache;
    }
}