package com.cse564hateoas.appealing.activities;

import com.cse564hateoas.appealing.model.Identifier;
import com.cse564hateoas.appealing.model.AppealStatus;
import com.cse564hateoas.appealing.model.Email;
import com.cse564hateoas.appealing.repositories.AppealRepository;
import com.cse564hateoas.appealing.repositories.EmailRepository;
import com.cse564hateoas.appealing.representations.Link;
import com.cse564hateoas.appealing.representations.EmailRepresentation;
import com.cse564hateoas.appealing.representations.Representation;
import com.cse564hateoas.appealing.representations.AppealUri;

public class EmailActivity {
    public EmailRepresentation send(Email email, AppealUri emailUri) {
        Identifier identifier = emailUri.getId();
        
        // Don't know the appeal!
        if(!AppealRepository.current().has(identifier)) {
            throw new NoSuchAppealException();
        }
        
        // Already paid
        if(EmailRepository.current().has(identifier)) {
            throw new UpdateException();
        }
        
        
        // If we get here, let's create the email and update the appeal status
        AppealRepository.current().get(identifier).setStatus(AppealStatus.UNDERREVIEW);
        EmailRepository.current().store(identifier, email);
        
        return new EmailRepresentation(email, new Link(Representation.RELATIONS_URI + "appeal", UriExchange.appealForEmail(emailUri)),
                new Link(Representation.RELATIONS_URI + "receipt", UriExchange.receiptForEmail(emailUri)));
    }
}
