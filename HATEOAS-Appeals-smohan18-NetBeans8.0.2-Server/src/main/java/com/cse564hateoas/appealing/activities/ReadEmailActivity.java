package com.cse564hateoas.appealing.activities;

import com.cse564hateoas.appealing.model.Identifier;
import com.cse564hateoas.appealing.model.AppealStatus;
import com.cse564hateoas.appealing.model.Email;
import com.cse564hateoas.appealing.repositories.AppealRepository;
import com.cse564hateoas.appealing.repositories.EmailRepository;
import com.cse564hateoas.appealing.representations.Link;
import com.cse564hateoas.appealing.representations.SendMailRepresentation;
import com.cse564hateoas.appealing.representations.Representation;
import com.cse564hateoas.appealing.representations.AppealUri;

public class ReadEmailActivity {

    public SendMailRepresentation read(AppealUri receiptUri) {
        Identifier identifier = receiptUri.getId();
        if(!appealHasBeenSent(identifier)) {
            throw new AppealNotSentException();
        } else if (AppealRepository.current().has(identifier) && AppealRepository.current().get(identifier).getStatus() == AppealStatus.REJECTED) {
            throw new AppealAlreadyEvaluatedException();
        }
        
        Email email = EmailRepository.current().get(identifier);
        
        return new SendMailRepresentation(email, new Link(Representation.RELATIONS_URI + "appeal", UriExchange.appealForMailSent(receiptUri)));
    }

    private boolean appealHasBeenSent(Identifier id) {
        return EmailRepository.current().has(id);
    }

}
