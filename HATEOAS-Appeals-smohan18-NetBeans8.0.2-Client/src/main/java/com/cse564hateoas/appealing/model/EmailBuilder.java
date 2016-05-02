package com.cse564hateoas.appealing.model;

public class EmailBuilder {
    
    private String sender_email = "smohan18@asu.edu";
    private String toEmailAddr= "Frank.callis@asu.edu";
    private String senderName = "Siddharth Sujir Mohan";
    private String subject = "Appeal: 1207654769";
    
    private String attachment = "1.jpg";
    
    
    
    public static EmailBuilder email() {
        return new EmailBuilder();
    }
    
 
    
    public EmailBuilder withSenderName(String name) {
        this.senderName = name;
        return this;
    }
    
    public EmailBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }
    
  
    private String bodyBuilder(Item items)
    {   
        String body="";
        body="Dear Professor Callis, \n Submitting appeal for "+items.getItemType()+". Area of concern highlighted in attachment. Please review and do the needful, Thanks, \n Siddharth Sujir Mohan";
    
        return body;
    }
    

    
    
    public Email build(Appeal appeal) {
        Item item=appeal.getItems();
      String email_body =  bodyBuilder(item);
        return new Email(sender_email, senderName, toEmailAddr ,subject, email_body, attachment);
    }
    
    
    public Email build(Appeal appeal, String body) {
        Item item=appeal.getItems();
     
      String email_body = "Dear Professor Calliss, As I havent received any update from you regarding the appeal regarding "+item.getItemType()+" I am resending this mail to follow up on the same. Expecting to hear from you soon. Thanks, Siddharth Sujir Mohan";
      this.subject="Appeal: Follow up Email";  
      return new Email(sender_email, senderName, toEmailAddr ,subject, email_body, attachment);
    }
    
}
