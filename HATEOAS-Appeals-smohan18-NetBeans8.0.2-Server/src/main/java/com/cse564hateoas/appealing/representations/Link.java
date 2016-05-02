package com.cse564hateoas.appealing.representations;

import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(namespace = Representation.DAP_NAMESPACE)
public class Link {
    
    private static final Logger LOG = LoggerFactory.getLogger(Link.class);
    
    @XmlAttribute(name = "rel")
    private String rel;
    @XmlAttribute(name = "uri")
    private String uri;

    @XmlAttribute(name = "mediaType")
    private String mediaType;

    /**
     * For JAXB :-(
     */
    Link() {
       
    }

    public Link(String name, AppealUri uri, String mediaType) {
        
        
        this.rel = name;
        this.uri = uri.getFullUri().toString();
        this.mediaType = mediaType;

       
    }

    public Link(String name, AppealUri uri) {
        this(name, uri, Representation.CSE564APPEAL_MEDIA_TYPE);
    }

    public String getRelValue() {
    
        return rel;
    }

    public URI getUri() {
        
        try {
            URI local_uri = new URI(uri);
            
            return local_uri;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMediaType() {
    
        return mediaType;
    }
}
