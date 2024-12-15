package cal335.projet.mes_chums.controleur;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import cal335.projet.mes_chums.dto.ContactDTO;
import cal335.projet.mes_chums.service.ContactService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ContactControleur implements HttpHandler {

    
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ContactService contactService;

    public ContactControleur(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod(); 
        String path = exchange.getRequestURI().getPath();

        switch (method) {
            case "GET":
                handleGet(exchange, path);
                break;
            case "POST":
                handlePost(exchange);
                break;
            case "PUT":
                handlePut(exchange, path);
                break;
            case "DELETE":
                handleDelete(exchange, path);
                break;
            default:
                sendResponse(exchange, "Method Not Allowed", 405);
        }
    }

    private void handleGet(HttpExchange exchange, String path) throws IOException {
        if (path.equals("/contacts")) {
          
            List<ContactDTO> contacts = contactService.getAllContacts();
            String jsonResponse = objectMapper.writeValueAsString(contacts);
            sendResponse(exchange, jsonResponse, 200);
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
       
        ContactDTO contactDTO = objectMapper.readValue(exchange.getRequestBody(), ContactDTO.class);
        contactService.addContact(contactDTO);
        sendResponse(exchange, "Contact added successfully", 201);
    }

    private void handlePut(HttpExchange exchange, String path) throws IOException {
        
        String id = path.split("/")[2];
        ContactDTO updatedContact = objectMapper.readValue(exchange.getRequestBody(), ContactDTO.class);
        contactService.updateContact(Integer.parseInt(id), updatedContact);
        sendResponse(exchange, "Contact updated successfully", 200);
    }

    private void handleDelete(HttpExchange exchange, String path) throws IOException {
        
        String id = path.split("/")[2];
        contactService.deleteContact(Integer.parseInt(id));
        sendResponse(exchange, "Contact deleted successfully", 200);
    }

    private void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}