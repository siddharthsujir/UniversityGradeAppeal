package com.cse564hateoas.appealing.repositories;

import java.util.HashMap;
import java.util.Map.Entry;

import com.cse564hateoas.appealing.model.Identifier;
import com.cse564hateoas.appealing.model.Appeal;

public class AppealRepository {

    private static final AppealRepository theRepository = new AppealRepository();
    private HashMap<String, Appeal> appealStore = new HashMap<String, Appeal>(); // Default implementation, not suitable for production!

    public static AppealRepository current() {
        return theRepository;
    }
    
    private AppealRepository(){}
    
    public Appeal get(Identifier identifier) {
        return appealStore.get(identifier.toString());
     }
    
    public Appeal take(Identifier identifier) {
        Appeal appeal = appealStore.get(identifier.toString());
        remove(identifier);
        return appeal;
    }

    public Identifier store(Appeal appeal) {
        Identifier id = new Identifier();
        appealStore.put(id.toString(), appeal);
        return id;
    }
    
    public void store(Identifier appealIdentifier, Appeal appeal) {
        appealStore.put(appealIdentifier.toString(), appeal);
    }

    public boolean has(Identifier identifier) {
        boolean result =  appealStore.containsKey(identifier.toString());
        return result;
    }

    public void remove(Identifier identifier) {
        appealStore.remove(identifier.toString());
    }
    
    public boolean appealPlaced(Identifier identifier) {
        return AppealRepository.current().has(identifier);
    }
    
    public boolean appealNotPlaced(Identifier identifier) {
        return !appealPlaced(identifier);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Entry<String, Appeal> entry : appealStore.entrySet()) {
            sb.append(entry.getKey());
            sb.append("\t:\t");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

    public synchronized void clear() {
        appealStore = new HashMap<String, Appeal>();
    }

    public int paper() {
        return appealStore.size();
    }
}
