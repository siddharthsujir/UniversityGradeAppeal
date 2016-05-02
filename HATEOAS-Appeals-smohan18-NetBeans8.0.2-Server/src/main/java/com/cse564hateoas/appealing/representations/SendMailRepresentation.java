package com.cse564hateoas.appealing.representations;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.joda.time.DateTime;

import com.cse564hateoas.appealing.model.Email;

@XmlRootElement(name = "receipt", namespace = Representation.CSE564APPEAL_NAMESPACE)
public class SendMailRepresentation extends Representation {
    
    private static final Logger LOG = LoggerFactory.getLogger(SendMailRepresentation.class);

    @XmlElement(name = "sender_emailId", namespace = Representation.CSE564APPEAL_NAMESPACE)
    private String sender_email;
    @XmlElement(name = "sentOn", namespace = Representation.CSE564APPEAL_NAMESPACE)
    private String emailDate;
    @XmlElement(name = "from", namespace = Representation.CSE564APPEAL_NAMESPACE)
    private String toEmailAddr;
    @XmlElement(name = "body", namespace = Representation.CSE564APPEAL_NAMESPACE)
    private String body;
    @XmlElement(name = "attachment", namespace = Representation.CSE564APPEAL_NAMESPACE)
    private String attachment;
    
    SendMailRepresentation(){
       
    } // For JAXB :-(
    
    public SendMailRepresentation(Email email, Link appealLink) {
        LOG.info("Creating an Receipt Representation with the email = {} and links = {}", email, links);
        
        this.sender_email = email.getSenderEmail();
        this.emailDate = email.getEmailDate().toString();
        this.toEmailAddr=email.getToEmailAddr();
        this.body=email.getEmailBody();
        this.attachment=email.getAttachment();
        this.links = new ArrayList<Link>();
        links.add(appealLink);
        
       
    }

    public DateTime getSendDate() {
        return new DateTime(emailDate);
    }
    
    public String getSenderEmailId() {
        return sender_email;
    }

    public Link getAppealLink() {
        return getLinkByName(Representation.RELATIONS_URI + "appeal");
    }
    
    public String toString() {
        try {
            JAXBContext context = JAXBContext.newInstance(SendMailRepresentation.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
