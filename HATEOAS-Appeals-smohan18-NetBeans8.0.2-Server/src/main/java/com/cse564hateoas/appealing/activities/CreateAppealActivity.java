package com.cse564hateoas.appealing.activities;

import com.cse564hateoas.appealing.model.Identifier;
import com.cse564hateoas.appealing.model.Appeal;
import com.cse564hateoas.appealing.model.AppealStatus;
import com.cse564hateoas.appealing.repositories.AppealRepository;
import com.cse564hateoas.appealing.representations.Link;
import com.cse564hateoas.appealing.representations.AppealRepresentation;
import com.cse564hateoas.appealing.representations.Representation;
import com.cse564hateoas.appealing.representations.AppealUri;

public class CreateAppealActivity {
    public AppealRepresentation create(Appeal appeal, AppealUri requestUri) {
        appeal.setStatus(AppealStatus.SUBMITTED);
                
        Identifier identifier = AppealRepository.current().store(appeal);
        
        AppealUri appealUri = new AppealUri(requestUri.getBaseUri() + "/appeal/" + identifier.toString());
        AppealUri emailUri = new AppealUri(requestUri.getBaseUri() + "/email/" + identifier.toString());
        return new AppealRepresentation(appeal, 
                new Link(Representation.RELATIONS_URI + "cancel", appealUri), 
                new Link(Representation.RELATIONS_URI + "email", emailUri), 
                new Link(Representation.RELATIONS_URI + "update", appealUri),
                new Link(Representation.SELF_REL_VALUE, appealUri));
    }
}
