package com.cse564hateoas.appealing.repositories;

import java.util.HashMap;
import java.util.Map.Entry;

import com.cse564hateoas.appealing.model.Identifier;
import com.cse564hateoas.appealing.model.Email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailRepository {
    
    private static final Logger LOG = LoggerFactory.getLogger(EmailRepository.class);

    private static final EmailRepository theRepository = new EmailRepository();
    private HashMap<String, Email> backingStore = new HashMap<>(); // Default implementation, not suitable for production!

    public static EmailRepository current() {
        return theRepository;
    }
    
    private EmailRepository(){
        LOG.debug("emailRepository Constructor");
    }
    
    public Email get(Identifier identifier) {
        LOG.debug("Retrieving Email object for identifier {}", identifier);
        return backingStore.get(identifier.toString());
    }
    
    public Email take(Identifier identifier) {
        LOG.debug("Removing the Email object for identifier {}", identifier);
        Email email = backingStore.get(identifier.toString());
        remove(identifier);
        return email;
    }

    public Identifier store(Email email) {
        LOG.debug("Storing a new Email object");
        
        Identifier id = new Identifier();
        LOG.debug("New Email object's id is {}", id);
        
        backingStore.put(id.toString(), email);
        return id;
    }
    
    public void store(Identifier identifier, Email email) {
        LOG.debug("Storing again the Appeal object with id", identifier);
        backingStore.put(identifier.toString(), email);
    }

    public boolean has(Identifier identifier) {
        LOG.debug("Checking to see if there is a Email object associated with the id {} in the Email store", identifier);
        
        boolean result =  backingStore.containsKey(identifier.toString());
        return result;
    }

    public void remove(Identifier identifier) {
        LOG.debug("Removing from storage the Email object with id", identifier);
        backingStore.remove(identifier.toString());
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Entry<String, Email> entry : backingStore.entrySet()) {
            sb.append(entry.getKey());
            sb.append("\t:\t");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

    public synchronized void clear() {
        backingStore = new HashMap<>();
    }
}
