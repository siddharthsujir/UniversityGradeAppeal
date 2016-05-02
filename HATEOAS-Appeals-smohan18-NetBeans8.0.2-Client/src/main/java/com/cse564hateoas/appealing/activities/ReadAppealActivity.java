package com.cse564hateoas.appealing.activities;

import com.cse564hateoas.appealing.model.Identifier;
import com.cse564hateoas.appealing.model.Appeal;
import com.cse564hateoas.appealing.repositories.AppealRepository;
import com.cse564hateoas.appealing.representations.AppealRepresentation;
import com.cse564hateoas.appealing.representations.AppealUri;

public class ReadAppealActivity {
    public AppealRepresentation retrieveByUri(AppealUri appealUri) {
        Identifier identifier  = appealUri.getId();
        
        Appeal appeal = AppealRepository.current().get(identifier);
        
        if(appeal == null) {
            throw new NoSuchAppealException();
        }
        
        return AppealRepresentation.createResponseAppealRepresentation(appeal, appealUri);
    }
}
