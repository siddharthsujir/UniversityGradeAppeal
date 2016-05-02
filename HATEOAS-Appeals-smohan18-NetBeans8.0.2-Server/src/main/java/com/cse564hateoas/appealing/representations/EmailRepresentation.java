package com.cse564hateoas.appealing.representations;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cse564hateoas.appealing.model.Email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name = "email", namespace = Representation.CSE564APPEAL_NAMESPACE)
public class EmailRepresentation extends Representation {
    
    private static final Logger LOG = LoggerFactory.getLogger(EmailRepresentation.class);
       
    @XmlElement(namespace = EmailRepresentation.CSE564APPEAL_NAMESPACE) private String sender_email;
    @XmlElement(namespace = EmailRepresentation.CSE564APPEAL_NAMESPACE) private String senderName;
    @XmlElement(namespace = EmailRepresentation.CSE564APPEAL_NAMESPACE) private String toEmailAddr;
    @XmlElement(namespace = EmailRepresentation.CSE564APPEAL_NAMESPACE) private String subject;
    @XmlElement(namespace = EmailRepresentation.CSE564APPEAL_NAMESPACE) private String email_body;
    @XmlElement(namespace = EmailRepresentation.CSE564APPEAL_NAMESPACE) private String attachment;
    
    
    /**
     * For JAXB :-(
     */
     EmailRepresentation(){
        
     }
    
    public EmailRepresentation(Email email, Link...links) {
       
        
        sender_email = email.getSenderEmail();
        senderName = email.getSenderName();
        toEmailAddr=email.getToEmailAddr();
        subject = email.getSubject();
        email_body = email.getEmailBody();
        attachment = email.getAttachment();
        this.links = java.util.Arrays.asList(links);
        
        
    }

    public Email getEmail() {
        return new Email(sender_email, senderName, toEmailAddr,subject, email_body, attachment);
    }
    
    public Link getReceiptLink() {
        return getLinkByName(Representation.RELATIONS_URI + "receipt");
    }
    
    public Link getAppealLink() {
        return getLinkByName(Representation.RELATIONS_URI + "appeal");
    }
}
