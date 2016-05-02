package com.cse564hateoas.appealing.client.network;

import java.net.URI;

import com.cse564hateoas.appealing.client.activities.CannotCancelException;
import com.cse564hateoas.appealing.client.activities.CannotUpdateAppealException;
import com.cse564hateoas.appealing.client.activities.DuplicateEmailException;
import com.cse564hateoas.appealing.client.activities.InvalidEmailException;
import com.cse564hateoas.appealing.client.activities.MalformedAppealException;
import com.cse564hateoas.appealing.client.activities.NotFoundException;
import com.cse564hateoas.appealing.client.activities.ServiceFailureException;
import com.cse564hateoas.appealing.model.Appeal;
import com.cse564hateoas.appealing.model.Email;
import com.cse564hateoas.appealing.representations.AppealRepresentation;
import com.cse564hateoas.appealing.representations.EmailRepresentation;
import com.cse564hateoas.appealing.representations.SendMailRepresentation;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

public class HttpBinding {

    private static final String RESTBUCKS_MEDIA_TYPE = "application/vnd.cse564-appeals+xml";

    public AppealRepresentation createAppeal(Appeal appeal, URI appealUri) throws MalformedAppealException, ServiceFailureException {
        Client client = Client.create();
        ClientResponse response = client.resource(appealUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(ClientResponse.class, new AppealRepresentation(appeal));

        int status = response.getStatus();

        if (status == 400) {
            throw new MalformedAppealException();
        } else if (status == 500) {
            throw new ServiceFailureException();
        } else if (status == 201) {
            return response.getEntity(AppealRepresentation.class);
        }

        throw new RuntimeException(String.format("Unexpected response [%d] while creating appeal resource [%s]", status, appealUri.toString()));
    }
    
    public AppealRepresentation retrieveAppeal(URI appealUri) throws NotFoundException, ServiceFailureException {
        Client client = Client.create();
        ClientResponse response = client.resource(appealUri).accept(RESTBUCKS_MEDIA_TYPE).get(ClientResponse.class);

        int status = response.getStatus();

        if (status == 404) {
            throw new NotFoundException ();
        } else if (status == 500) {
            throw new ServiceFailureException();
        } else if (status == 200) {
            return response.getEntity(AppealRepresentation.class);
        }

        throw new RuntimeException(String.format("Unexpected response while retrieving appeal resource [%s]", appealUri.toString()));
    }

    public AppealRepresentation updateAppeal(Appeal appeal, URI appealUri) throws MalformedAppealException, ServiceFailureException, NotFoundException,
            CannotUpdateAppealException {
        Client client = Client.create();
        ClientResponse response = client.resource(appealUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(ClientResponse.class, new AppealRepresentation(appeal));

        int status = response.getStatus();

        if (status == 400) {
            throw new MalformedAppealException();
        } else if (status == 404) {
            throw new NotFoundException();
        } else if (status == 409) {
            throw new CannotUpdateAppealException();
        } else if (status == 500) {
            throw new ServiceFailureException();
        } else if (status == 200) {
            return response.getEntity(AppealRepresentation.class);
        }

        throw new RuntimeException(String.format("Unexpected response [%d] while udpating appeal resource [%s]", status, appealUri.toString()));
    }

    public AppealRepresentation storedAppeal(URI appealUri) throws ServiceFailureException, CannotCancelException, NotFoundException {
        Client client = Client.create();
        ClientResponse response = client.resource(appealUri).accept(RESTBUCKS_MEDIA_TYPE).delete(ClientResponse.class);

        int status = response.getStatus();
        if (status == 404) {
            throw new NotFoundException();
        } else if (status == 405) {
            throw new CannotCancelException();
        } else if (status == 500) {
            throw new ServiceFailureException();
        } else if (status == 200) {
            return response.getEntity(AppealRepresentation.class);
        }

        throw new RuntimeException(String.format("Unexpected response [%d] while deleting appeal resource [%s]", status, appealUri.toString()));
    }

    public EmailRepresentation sendEmail(Email email, URI emailUri) throws InvalidEmailException, NotFoundException, DuplicateEmailException,
            ServiceFailureException {
        Client client = Client.create();
        ClientResponse response = client.resource(emailUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).put(ClientResponse.class, new EmailRepresentation(email));

        int status = response.getStatus();
        if (status == 400) {
            throw new InvalidEmailException();
        } else if (status == 404) {
            throw new NotFoundException();
        } else if (status == 405) {
            throw new DuplicateEmailException();
        } else if (status == 500) {
            throw new ServiceFailureException();
        } else if (status == 201) {
            return response.getEntity(EmailRepresentation.class);
        }

        throw new RuntimeException(String.format("Unexpected response [%d] while creating email resource [%s]", status, emailUri.toString()));
    }

    public SendMailRepresentation retrieveReceipt(URI receiptUri) throws NotFoundException, ServiceFailureException {
        Client client = Client.create();
        ClientResponse response = client.resource(receiptUri).accept(RESTBUCKS_MEDIA_TYPE).get(ClientResponse.class);

        int status = response.getStatus();
        if (status == 404) {
            throw new NotFoundException();
        } else if (status == 500) {
            throw new ServiceFailureException();
        } else if (status == 200) {
            return response.getEntity(SendMailRepresentation.class);
        }
        
        throw new RuntimeException(String.format("Unexpected response [%d] while retrieving receipt resource [%s]", status, receiptUri.toString()));
    }
}
