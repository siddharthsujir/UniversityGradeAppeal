package com.cse564hateoas.appealing.client;

import static com.cse564hateoas.appealing.model.AppealBuilder.appeal;

import java.net.URI;
import java.net.URISyntaxException;

import com.cse564hateoas.appealing.client.activities.Actions;
import com.cse564hateoas.appealing.client.activities.GetEmailActivity;
import com.cse564hateoas.appealing.client.activities.EmailActivity;
import com.cse564hateoas.appealing.client.activities.PlaceAppealActivity;
import com.cse564hateoas.appealing.client.activities.ReadAppealActivity;
import com.cse564hateoas.appealing.client.activities.UpdateAppealActivity;
import com.cse564hateoas.appealing.model.Appeal;
import com.cse564hateoas.appealing.model.AppealStatus;
import com.cse564hateoas.appealing.model.Email;
import com.cse564hateoas.appealing.model.EmailBuilder;
import com.cse564hateoas.appealing.model.Identifier;
import com.cse564hateoas.appealing.representations.Link;
import com.cse564hateoas.appealing.representations.AppealRepresentation;
import com.cse564hateoas.appealing.representations.EmailRepresentation;
import com.cse564hateoas.appealing.representations.SendMailRepresentation;
import com.cse564hateoas.appealing.representations.AppealUri;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    
    private static final String CSE564APPEALS_MEDIA_TYPE = "application/vnd.cse564-appeals+xml";
    
    private static final String BASE_URI = "http://localhost:8080/HATEOAS-Appeals-smohan18-NetBeans8.0.2-Server/webresources/appeal";

    public static void main(String[] args) throws Exception {
        URI serviceUri = new URI(BASE_URI);
        happyCase(serviceUri);
       badStart(serviceUri);
        badId(serviceUri);
        forgottenCase(serviceUri);
        abandonedCase(serviceUri);
    }

  

    private static void happyCase(URI serviceUri) throws Exception {
        
         System.out.println("");
          System.out.println("");
           System.out.println("");
        LOG.info("Starting Happy case with Service URI {}", serviceUri);
        // Place the appeal2
        LOG.info("Step 1. Place the appeal"); ///Place an appeal2 and appeal2 is processed.
        System.out.println(String.format("About to start happy path test. Placing appeal at [%s] via POST", serviceUri.toString()));
        Appeal appeal = appeal().withRandomItems().build();
        /*
         Email email1 = EmailBuilder.email().build(appeal2);
         appeal2.setEmail(email1);*/
        LOG.debug("Created base appeal {}", appeal);
        Client client = Client.create();
        LOG.debug("Created client {}", client);
        AppealRepresentation appealRepresentation = client.resource(serviceUri).accept(CSE564APPEALS_MEDIA_TYPE).type(CSE564APPEALS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        LOG.debug("Created appealRepresentation {} denoted by the URI {}", appealRepresentation, appealRepresentation.getSelfLink().getUri().toString());
        System.out.println(String.format("Appeal placed at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
        
        LOG.debug("\n\n Creating Email for the appeal");
        System.out.println(String.format("About to create a email resource at [%s]", appealRepresentation.getEmailLink().getUri().toString()));
        Link emailLink = appealRepresentation.getEmailLink();
        
        Email email = EmailBuilder.email().build(appeal);
        LOG.debug("Created new email object {}", email);
        
    
        System.out.println(String.format("About to create a email resource at [%s] via PUT", appealRepresentation.getEmailLink().getUri().toString()));
        Link emailLink2 = appealRepresentation.getEmailLink();
        
        EmailRepresentation  emailRepresentation = client.resource(emailLink2.getUri()).accept(emailLink2.getMediaType()).type(emailLink2.getMediaType()).put(EmailRepresentation.class, new EmailRepresentation(email));        
      
        System.out.println(String.format("Email sent, it can be found at [%s]", emailRepresentation.getReceiptLink().getUri().toString()));
        
        
        
       
    }   
    
    
    // Function for Abandoned Case
    
    public static void abandonedCase(URI serviceUri) throws Exception
    {
        
        
        
         System.out.println("");
        
          System.out.println("");
           System.out.println("Abandoned Case");
        
        LOG.info("Step 1. Place the appeal"); ///Place an appeal2 and appeal2 is processed.
        System.out.println(String.format("About to start happy path test. Placing appeal at [%s] via POST", serviceUri.toString()));
        Appeal appeal = appeal().withRandomItems().build();
        /*
         Email email1 = EmailBuilder.email().build(appeal2);
         appeal2.setEmail(email1);*/
        LOG.debug("Created base appeal {}", appeal);
        Client client = Client.create();
        LOG.debug("Created client {}", client);
        AppealRepresentation appealRepresentation = client.resource(serviceUri).accept(CSE564APPEALS_MEDIA_TYPE).type(CSE564APPEALS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        LOG.debug("Created appealRepresentation {} denoted by the URI {}", appealRepresentation, appealRepresentation.getSelfLink().getUri().toString());
        System.out.println(String.format("Appeal placed at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
        
        LOG.debug("\n\n Creating Email for the appeal");
        System.out.println(String.format("About to create a email resource at [%s]", appealRepresentation.getEmailLink().getUri().toString()));
        Link emailLink = appealRepresentation.getEmailLink();
        
        Email email = EmailBuilder.email().build(appeal);
        LOG.debug("Created new email object {}", email);
        
        Link appealLink= appealRepresentation.getSelfLink();
        
        
        
        LOG.debug("\n\n Deleting the appeal");
        System.out.println(String.format("Trying to delete the appeal from [%s] via DELETE. ", appealRepresentation.getSelfLink().getUri().toString()));
       
        ClientResponse finalResponse = client.resource(appealLink.getUri()).delete(ClientResponse.class);
        System.out.println(String.format("Tried to delete the Appeal, HTTP status [%d]", finalResponse.getStatus()));
        if(finalResponse.getStatus() == 200) {
            System.out.println(String.format("Appeal deleted, Good luck for your next Exam", finalResponse.getEntity(AppealRepresentation.class).getStatus()));
        }
        
    }
    
    
    public static void forgottenCase(URI serviceUri) throws IOException
            

    
    {
        
        
         System.out.println("");
          System.out.println("");
           System.out.println("Forgotten Case");
        
    LOG.info("Step 1. Place the appeal"); ///Place an appeal2 and appeal2 is processed.
    
        System.out.println(String.format("Starting ForgottenCase Test . Placing appeal at [%s] via POST", serviceUri.toString()));
        Appeal appeal = appeal().withRandomItems().build();
        
        LOG.debug("Created base appeal {}", appeal);
        Client client = Client.create();
        LOG.debug("Created client {}", client);
        AppealRepresentation appealRepresentation = client.resource(serviceUri).accept(CSE564APPEALS_MEDIA_TYPE).type(CSE564APPEALS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        LOG.debug("Created appealRepresentation {} denoted by the URI {}", appealRepresentation, appealRepresentation.getSelfLink().getUri().toString());
        System.out.println(String.format("Appeal placed at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
        
        LOG.debug("\n\n Creating Email for the appeal");
        System.out.println(String.format("About to create a email resource at [%s]", appealRepresentation.getEmailLink().getUri().toString()));
        Link emailLink = appealRepresentation.getEmailLink();
        Email email = EmailBuilder.email().build(appeal);
        LOG.debug("Created new email object \n {}", email);
        Link emailLink2 = appealRepresentation.getEmailLink();
        
        EmailRepresentation  emailRepresentation = client.resource(emailLink2.getUri()).accept(emailLink2.getMediaType()).type(emailLink2.getMediaType()).put(EmailRepresentation.class, new EmailRepresentation(email));        
      
        System.out.println(String.format("Email sent, it can be found at [%s]", emailRepresentation.getReceiptLink().getUri().toString()));
        
        System.out.println();
        System.out.println("Pausing a while after the appeal has been placed.");
        System.out.println();
        System.out.println("Please press any key to follow up on the appeal");
        System.in.read();
        
        System.out.println("\n\n Resending an Email to follow up on the appeal status");
        System.out.println(String.format("About to create a email resource at [%s]", appealRepresentation.getEmailLink().getUri().toString()));
         emailLink = appealRepresentation.getEmailLink();
         
        email = EmailBuilder.email().build(appeal,"");
        Link eLink = appealRepresentation.getEmailLink();
        
        
        LOG.debug("Created new email object {}", email);
        
        
        
        
    }
    
    
    public static void badStart(URI serviceUri)
    {
        System.out.println("");
         System.out.println("");
        LOG.info("Bad Start case with Service URI {}", serviceUri);
        // Place the appeal2
        LOG.info("Step 1. Create an appeal"); ///Place an appeal2 and appeal2 is processed.
        System.out.println(String.format("About to start happy path test. Placing appeal at [%s] via POST", serviceUri.toString()));
        Appeal appeal = appeal().withRandomItems().build();
       
        LOG.debug("Created base appeal {}", appeal);
        Client client = Client.create();
        LOG.debug("Created client {}", client);
        Link badLink = new Link("bad", new AppealUri(serviceUri.toString() + "/bad-uri"), CSE564APPEALS_MEDIA_TYPE);
       
        try
        {      
        
        AppealRepresentation appealRepresentation = client.resource(badLink.getUri()).accept(CSE564APPEALS_MEDIA_TYPE).type(CSE564APPEALS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        LOG.debug("Created appealRepresentation {} denoted by the URI {}", appealRepresentation, appealRepresentation.getSelfLink().getUri().toString());
        System.out.println(String.format("Tried to create appeal at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
        }
        
        catch(Exception e)
        {
        System.out.println("Tried to create an appeal with the Incorrect URI. "+ badLink.getUri().toString()+" Please Check the URI and try again");
        }
        
        System.out.println("");
        System.out.println("Starting the appeal Process with a new URI");
        
        Appeal newAppeal = appeal().withRandomItems().build();
        
        LOG.debug("Created base appeal {}", newAppeal);
        Client newClient = Client.create();
        LOG.debug("Created client {}", newClient);
        AppealRepresentation appealRepresentation = newClient.resource(serviceUri).accept(CSE564APPEALS_MEDIA_TYPE).type(CSE564APPEALS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(newAppeal));
        LOG.debug("Created appealRepresentation {} denoted by the URI {}", appealRepresentation, appealRepresentation.getSelfLink().getUri().toString());
        System.out.println(String.format("Appeal placed at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
        
        LOG.debug("\n\n Creating Email for the appeal");
        System.out.println(String.format("About to create a email resource at [%s]", appealRepresentation.getEmailLink().getUri().toString()));
        Link emailLink = appealRepresentation.getEmailLink();
        
        Email email = EmailBuilder.email().build(newAppeal);
        LOG.debug("Created new email object {}", email);
        
    
        System.out.println(String.format("About to create a email resource at [%s] via PUT", appealRepresentation.getEmailLink().getUri().toString()));
        Link emailLink2 = appealRepresentation.getEmailLink();
        
        EmailRepresentation  emailRepresentation = newClient.resource(emailLink2.getUri()).accept(emailLink2.getMediaType()).type(emailLink2.getMediaType()).put(EmailRepresentation.class, new EmailRepresentation(email));        
      
        System.out.println(String.format("Email sent, it can be found at [%s]", emailRepresentation.getReceiptLink().getUri().toString()));
        
    
    }
    
    public static void badId(URI serviceUri)
    {
        
        
         System.out.println("");
          System.out.println("");
           System.out.println("");
        LOG.debug("\n\n To check the appeal with bad URI");
        
        Appeal appeal = appeal().withRandomItems().build();
        Client client = Client.create();
        LOG.debug("Created client {}", client);
        AppealRepresentation appealRepresentation = client.resource(serviceUri).accept(CSE564APPEALS_MEDIA_TYPE).type(CSE564APPEALS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
         Identifier identifier=new Identifier();
         LOG.info(appealRepresentation.getSelfLink().getUri().toString());
        Link badLink2 = new Link("bad", new AppealUri(serviceUri,identifier), CSE564APPEALS_MEDIA_TYPE);
        try
        {
        AppealRepresentation badidAppealRepresentation = client.resource(badLink2.getUri()).accept(CSE564APPEALS_MEDIA_TYPE).get(AppealRepresentation.class);
        System.out.println(String.format("Final appeal placed, current status [%s]", badidAppealRepresentation.getStatus()));
        }
        catch(Exception e)
        {
        System.out.println("Resource Not Found for the given appeal Id "+badLink2.getUri().toString()+" Please look at it and provide the correct appeal ID again");
        }
    }
    
}
