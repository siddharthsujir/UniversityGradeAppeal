package com.cse564hateoas.appealing.model;


import javax.xml.bind.annotation.XmlTransient;

public class Appeal {
    
    private final Item items;
    @XmlTransient
    private AppealStatus status = AppealStatus.SUBMITTED;
    private Email email=null;
    public Appeal(Item items) {
      this(AppealStatus.SUBMITTED, items);
    }
    

    public Appeal(AppealStatus status, Item items) {
        this.items = items;
        this.status = status;
    }

    
    public Item getItems() {
        return items;
    }

    public double calculateCost() {
        double total = 0.0;
        if (items != null) {
         
                if(items != null && items.getAssignment() != null) {
                    total += items.getAssignment().getMark();
         
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
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
   
        sb.append("Status: " + status + "\n");
   
            sb.append("Item: " + items.toString()+ "\n");
 
        return sb.toString();
    }
}