# Documentation du Projet "Mes Chums"

## Présentation de l'Application

L'application **"Mes Chums"** est une solution innovante permettant de gérer efficacement les contacts favoris avec des fonctionnalités avancées telles que :

- La gestion des contacts favoris.
- La recherche de contacts à proximité via des coordonnées GPS.
- L'ajout et la mise à jour des adresses de contacts.
- L'intégration avec une API de géocodage pour convertir les adresses en coordonnées GPS.

### Technologies Utilisées

- **Langage de programmation** : Java.
- **Gestionnaire de projet et des dépendances** : Maven.
- **Serveur** : HttpServer (Java natif).
- **Base de données** : Simulée avec une liste en mémoire.
- **API de Géocodage** : OpenWeatherMap.

## Concepts Clés du Projet

### 1. Endpoints REST

#### Liste des Endpoints

1. **GET /contacts**
   - Récupérer tous les contacts.
   - **Exemple de requête :**
     ```bash
     curl -X GET http://localhost:8080/contacts
     ```
   - **Exemple de réponse :**
     ```json
     [
       {
         "id": 1,
         "nom": "John Doe",
         "favoris": true
       }
     ]
     ```

2. **GET /contacts/proximite**
   - Rechercher des contacts proches d'une localisation.
   - **Exemple de requête :**
     ```bash
     curl -X GET "http://localhost:8080/contacts/proximite?latitude=45.0&longitude=-73.0&radius=10"
     ```
   - **Exemple de réponse :**
     ```json
     [
       {
         "id": 2,
         "nom": "Jane Smith",
         "latitude": 45.1,
         "longitude": -73.1
       }
     ]
     ```

3. **POST /adresse**
   - Ajouter une adresse pour un contact.
   - **Exemple de requête :**
     ```bash
     curl -X POST http://localhost:8080/adresse -H "Content-Type: application/json" -d '{
       "idContact": 1,
       "rue": "123 Main St",
       "ville": "Montreal",
       "province": "QC",
       "codePostal": "H1A1A1"
     }'
     ```

### 2. Architecture Logicielle

L'application suit une architecture en couches pour garantir une séparation claire des responsabilités :

#### Couche DAO
Responsable de l'accès aux données. Exemple :
```java
public class ContactDAO {
    private final List<Contact> contacts = new ArrayList<>();

    public List<Contact> findAll() {
        return contacts;
    }
}
```

#### Couche Service
Gère la logique métier. Exemple :
```java
public class ContactService {
    public List<ContactDTO> getAllContacts() {
        return contactDAO.findAll()
            .stream()
            .map(contact -> new ContactDTO(contact.getId(), contact.getNom(), contact.isFavoris()))
            .collect(Collectors.toList());
    }
}
```

#### Couche Contrôleur
Exécute les actions en fonction des requêtes REST.
```java
public class ContactControleur implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            List<ContactDTO> contacts = contactService.getAllContacts();
            String response = new ObjectMapper().writeValueAsString(contacts);
            exchange.sendResponseHeaders(200, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
        }
    }
}
```

### 3. Mécanisme de Cache

#### Implémentation
Un cache est géré avec une structure `HashMap` pour les contacts favoris.
```java
private final Map<ContactDTO, List<Coordonnees>> cache = new HashMap<>();
```

#### Mise à jour
- Lorsqu'un contact est marqué comme favori :
  ```java
  public void addFavoris(ContactDTO contact) {
      cache.put(contact, adresseService.getCoordonnees(contact.getId()));
  }
  ```

- Lorsqu'un contact est démarqué comme favori :
  ```java
  public void removeFavoris(ContactDTO contact) {
      cache.remove(contact);
  }
  ```

#### Diagramme de Séquences
```
Utilisateur -> Contrôleur -> Service -> Cache
```

### 4. Utilisation de Géolocalisation

#### Exemple d'Utilisation de l'API OpenWeatherMap
```java
public class ApiGeocodage {
    public Coordonnees obtenirCoordonnees(String adresse) {
        String url = "http://api.openweathermap.org/...";
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // Traitement de la réponse JSON
        return new Coordonnees(latitude, longitude);
    }
}
```

### 5. Injection de Dépendances

L'injection de dépendances est mise en place pour rendre l'application plus modulaire. Par exemple :

```java
public class AdresseService {
    public AdresseService(ContactDAO contactDAO, AdresseDAO adresseDAO, ApiGeocodage apiGeocodage) {
        this.contactDAO = contactDAO;
        this.adresseDAO = adresseDAO;
        this.apiGeocodage = apiGeocodage;
    }
}
```
Cela permet de remplacer facilement des implémentations pour les tests ou d'autres besoins.

---

## Conclusion
L'application **"Mes Chums"** est un outil simple et efficace pour gérer des contacts. Elle utilise une architecture claire, un système de cache, et des dépendances bien organisées, tout en utilisant les APIs REST et les services de géocodage.
