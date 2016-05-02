package com.cse564hateoas.appealing.client;

import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cse564hateoas.appealing.model.Item;
import com.cse564hateoas.appealing.model.Appeal;
import com.cse564hateoas.appealing.model.AppealStatus;
import com.cse564hateoas.appealing.model.Email;
import com.cse564hateoas.appealing.representations.Representation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name = "appeal", namespace = Representation.CSE564APPEAL_NAMESPACE)
public class ClientAppeal {
    
    private static final Logger LOG = LoggerFactory.getLogger(ClientAppeal.class);
    
    @XmlElement(name = "item", namespace = Representation.CSE564APPEAL_NAMESPACE)
    private Item items;
    @XmlElement(name = "email", namespace = Representation.CSE564APPEAL_NAMESPACE)
    private Email email;
    @XmlElement(name = "status", namespace = Representation.CSE564APPEAL_NAMESPACE)
    private AppealStatus status;
    
    private ClientAppeal(){}
    
    public ClientAppeal(Appeal appeal) {
        LOG.debug("Executing ClientAppeal constructor");
       // this.location = appeal.getLocation();
        this.items = appeal.getItems();
        LOG.info("Executed appeal.getitems");
        this.email=appeal.getEmail();
        LOG.info("Executed appeal.getemail");
    }
    
    public Appeal getAppeal() {
        LOG.debug("Executing ClientAppeal.getAppeal");
        return new Appeal(items, status);
    }
    
   /* public Location getLocation() {
        LOG.debug("Executing ClientAppeal.getLocation");
        return location;
    }*/
    
    public Item getItems() {
        LOG.debug("Executing ClientAppeal.getItems");
        return items;
    }

    public Email getEmail() {
        LOG.debug("Executing ClientAppeal.getEmail");
        return email;
    }
    
    @Override
    public String toString() {
        LOG.debug("Executing ClientAppeal.toString");
        try {
            JAXBContext context = JAXBContext.newInstance(ClientAppeal.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public AppealStatus getStatus() {
        LOG.debug("Executing ClientAppeal.getStatus");
        return status;
    }

    public double getCost() {
        LOG.debug("Executing ClientAppeal.getCost");
        double total = 0.0;
        if (items != null) {
           // for (Item item : items) {
                if(items != null && items.getAssignment() != null) {
                    total += items.getAssignment().mark();
               }
          //  }
        }
        return total;
    }
}