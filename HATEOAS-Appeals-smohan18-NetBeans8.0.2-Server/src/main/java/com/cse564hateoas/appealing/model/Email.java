package com.cse564hateoas.appealing.model;

import org.joda.time.DateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Email {

    private static final Logger LOG = LoggerFactory.getLogger(Email.class);
    
    private String sender_email;
    private String senderName;
    private String toEmailAddr;
    private String subject;
    private String email_body;
    private String attachment;
    private DateTime emailDate;

    Email(){}
    
    public Email(String sender_email, String from, String toEmailAddr, String subject, String body, String attachment) {
        this.sender_email = sender_email;
        this.senderName = from;
        this.toEmailAddr=toEmailAddr;
        this.subject = subject;
        this.email_body = body;
        this.attachment = attachment;
        emailDate = new DateTime();

    }
    public String getSenderEmail() {
        return sender_email;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSubject() {
        return subject;
    }

    public String getEmailBody() {
        return email_body;
    }

    public String getAttachment() {
        return attachment;
    }
    
    public String getToEmailAddr()
    {
        return toEmailAddr;
    }
    
    public DateTime getEmailDate() {
        return emailDate;
    }
    
    
    
    public String toString() {
        
        
       
        StringBuilder sb = new StringBuilder();
     
        sb.append("From: " + getSenderEmail() + "\n");
     
            sb.append("To: " + getToEmailAddr()+ "\n");
            sb.append("Subject: " + getSubject()+ "\n");
            sb.append("Body: " + getEmailBody()+ "\n");
            sb.append("Attachment: " + getAttachment()+ "\n");
            sb.append("Date: " + getEmailDate()+ "\n");
 
        return sb.toString();
    }
    
}
