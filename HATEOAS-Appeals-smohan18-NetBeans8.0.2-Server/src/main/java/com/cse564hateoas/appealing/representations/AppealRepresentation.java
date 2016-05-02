package com.cse564hateoas.appealing.representations;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cse564hateoas.appealing.activities.InvalidAppealException;
import com.cse564hateoas.appealing.activities.UriExchange;
import com.cse564hateoas.appealing.model.Item;
import com.cse564hateoas.appealing.model.Email;
import com.cse564hateoas.appealing.model.Appeal;
import com.cse564hateoas.appealing.model.AppealStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name = "appeal", namespace = Representation.CSE564APPEAL_NAMESPACE)
public class AppealRepresentation extends Representation {
    
    private static final Logger LOG = LoggerFactory.getLogger(AppealRepresentation.class);

    @XmlElement(name = "item", namespace = Representation.CSE564APPEAL_NAMESPACE)
    private Item items;
    @XmlElement(name = "mark", namespace = Representation.CSE564APPEAL_NAMESPACE)
    private double mark;
    @XmlElement(name = "email", namespace = Representation.CSE564APPEAL_NAMESPACE)
    private Email email;
    @XmlElement(name = "status", namespace = Representation.CSE564APPEAL_NAMESPACE)
    private AppealStatus status;

    /**
     * For JAXB :-(
     */
    AppealRepresentation() {
     
    }

    public static AppealRepresentation fromXmlString(String xmlRepresentation) {
       
                
        AppealRepresentation appealRepresentation = null;     
        try {
            JAXBContext context = JAXBContext.newInstance(AppealRepresentation.class);
            
            Unmarshaller unmarshaller = context.createUnmarshaller();
            
            appealRepresentation = (AppealRepresentation) unmarshaller.unmarshal(new ByteArrayInputStream(xmlRepresentation.getBytes()));
           
        } catch (Exception e) {
            throw new InvalidAppealException(e);
        }
        
       
        return appealRepresentation;
    }
    
    public static AppealRepresentation createResponseAppealRepresentation(Appeal appeal, AppealUri appealUri) {
      
        
        AppealRepresentation appealRepresentation;     
        
        AppealUri emailUri = new AppealUri(appealUri.getBaseUri() + "/email/" + appealUri.getId().toString());
       
        
        if(appeal.getStatus() == AppealStatus.SUBMITTED) {
           
            appealRepresentation = new AppealRepresentation(appeal, 
                    new Link(RELATIONS_URI + "cancel", appealUri), 
                    new Link(RELATIONS_URI + "email", emailUri), 
                    new Link(RELATIONS_URI + "update", appealUri),
                    new Link(Representation.SELF_REL_VALUE, appealUri));
        } else if(appeal.getStatus() == AppealStatus.UNDERREVIEW) {
           
            appealRepresentation = new AppealRepresentation(appeal, new Link(Representation.SELF_REL_VALUE, appealUri));
        } else if(appeal.getStatus() == AppealStatus.ACCEPTED) {
           
            appealRepresentation = new AppealRepresentation(appeal, new Link(Representation.RELATIONS_URI + "reciept", UriExchange.receiptForEmail(emailUri)));
        } else if(appeal.getStatus() == AppealStatus.REJECTED) {
           
            appealRepresentation = new AppealRepresentation(appeal);            
        } else {
           
            throw new RuntimeException("Unknown Appeal Status");
        }
        
      
        
        return appealRepresentation;
    }

    public AppealRepresentation(Appeal appeal, Link... links) {
        
        
        try {
           
            this.items = appeal.getItems();
           this.mark = appeal.calculateCost();
            this.email=appeal.getEmail();
            this.status = appeal.getStatus();
            this.links = java.util.Arrays.asList(links);
        } catch (Exception ex) {
            throw new InvalidAppealException(ex);
        }
        
     
    }

    @Override
    public String toString() {
      
        try {
            JAXBContext context = JAXBContext.newInstance(AppealRepresentation.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Appeal getAppeal() {
       
            if (items == null) {
                throw new InvalidAppealException();
            
        }
        
        Appeal appeal = new Appeal(status, items);
        
        

        return appeal;
    }

    public Link getCancelLink() {
        
        return getLinkByName(RELATIONS_URI + "cancel");
    }

    public Link getEmailLink() {
       
        return getLinkByName(RELATIONS_URI + "email");
    }

    public Link getUpdateLink() {
     
        return getLinkByName(RELATIONS_URI + "update");
    }

    public Link getSelfLink() {
     
        return getLinkByName("self");
    }
    
    public AppealStatus getStatus() {
    
        return status;
    }

    public double getMark() {
   
        return mark;
    }
}
