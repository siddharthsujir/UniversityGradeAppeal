package com.cse564hateoas.appealing.repositories;

import java.util.HashMap;
import java.util.Map.Entry;

import com.cse564hateoas.appealing.model.Identifier;
import com.cse564hateoas.appealing.model.Email;

public class EmailRepository {

    private static final EmailRepository theRepository = new EmailRepository();
    private HashMap<String, Email> appealStore = new HashMap<String, Email>(); // Default implementation, not suitable for production!

    public static EmailRepository current() {
        return theRepository;
    }
    
    private EmailRepository(){}
    
    public Email get(Identifier identifier) {
        return appealStore.get(identifier.toString());
    }
    
    public Email take(Identifier identifier) {
        Email email = appealStore.get(identifier.toString());
        remove(identifier);
        return email;
    }

    public Identifier store(Email email) {
        Identifier id = new Identifier();
        appealStore.put(id.toString(), email);
        return id;
    }
    
    public void store(Identifier emailIdentifier, Email email) {
        appealStore.put(emailIdentifier.toString(), email);
    }

    public boolean has(Identifier identifier) {
        boolean result =  appealStore.containsKey(identifier.toString());
        return result;
    }

    public void remove(Identifier identifier) {
        appealStore.remove(identifier.toString());
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Entry<String, Email> entry : appealStore.entrySet()) {
            sb.append(entry.getKey());
            sb.append("\t:\t");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

    public synchronized void clear() {
        appealStore = new HashMap<String, Email>();
    }
}
