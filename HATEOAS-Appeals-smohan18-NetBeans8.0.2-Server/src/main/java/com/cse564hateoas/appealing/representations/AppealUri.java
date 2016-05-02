package com.cse564hateoas.appealing.representations;

import java.net.URI;
import java.net.URISyntaxException;

import com.cse564hateoas.appealing.model.Identifier;

public class AppealUri {
    private URI uri;
    
    public AppealUri(String uri) {
        try {
            this.uri = new URI(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    
    public AppealUri(URI uri) {
        this(uri.toString());
    }

    public AppealUri(URI uri, Identifier identifier) {
        this(uri.toString() + "/" + identifier.toString());
    }

    public Identifier getId() {
        String path = uri.getPath();
        return new Identifier(path.substring(path.lastIndexOf("/") + 1, path.length()));
    }

    public URI getFullUri() {
        return uri;
    }
    
    @Override
    public String toString() {
        return uri.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AppealUri) {
            return ((AppealUri)obj).uri.equals(uri);
        }
        return false;
    }

    public String getBaseUri() {
       
        
        String uriString = uri.toString();
        String baseURI   = uriString.substring(0, uriString.lastIndexOf("webresources/")+"webresources".length());
        
        return baseURI;
    }
}
