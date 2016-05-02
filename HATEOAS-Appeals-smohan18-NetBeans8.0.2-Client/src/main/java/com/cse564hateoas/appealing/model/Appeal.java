package com.cse564hateoas.appealing.model;

import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Appeal {
    
    private static final Logger LOG = LoggerFactory.getLogger(Appeal.class);
    
     
    

    private final Item items;
    @XmlTransient
    private AppealStatus status = AppealStatus.SUBMITTED;

    private Email email=null;
    
    public Appeal(Item items) {
        this(items, AppealStatus.SUBMITTED);
        LOG.debug("Executing Appeal constructor: location = {} and items = {}", items);
    }
    

    public Appeal(Item items, AppealStatus status) {
        this.items = items;
        this.status = status;
        LOG.debug("Executing Appeal constructor: status = {} and items = {}", status, items);
    }

    public Item getItems() {
        LOG.debug("items = {}", items);
        return items;
    }

    public double calculateCost() {
        double total = 0.0;
        if (items != null) {
            //for (Item item : items) {
                if(items != null && items.getAssignment() != null) {
                    total += items.getAssignment().mark();
              //  }
            }
        }
        return total;
    }

    public void setStatus(AppealStatus status) {

        this.status = status;
    }

    public AppealStatus getStatus() {

        return status;
    }
    
    public void setEmail(Email email)
    {
        this.email=email;
    }
    
    public Email getEmail()
    {

        return email;
    }
    
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
       // sb.append("Location: " + location + "\n");
        sb.append("Status: " + status + "\n");
        //for(Item i : items) {
            sb.append("Item: " + items.toString()+ "\n");
        //}
        return sb.toString();
    }
}