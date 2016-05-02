package com.cse564hateoas.appealing.activities;

import com.cse564hateoas.appealing.model.Identifier;
import com.cse564hateoas.appealing.model.Appeal;
import com.cse564hateoas.appealing.model.AppealStatus;
import com.cse564hateoas.appealing.repositories.AppealRepository;
import com.cse564hateoas.appealing.representations.AppealRepresentation;
import com.cse564hateoas.appealing.representations.AppealUri;

public class UpdateAppealActivity {
    public AppealRepresentation update(Appeal appeal, AppealUri appealUri) {
        Identifier appealIdentifier = appealUri.getId();

        AppealRepository repository = AppealRepository.current();
        if (AppealRepository.current().appealNotPlaced(appealIdentifier)) { // Defensive check to see if we have the appeal
            throw new NoSuchAppealException();
        }

        if (!appealCanBeChanged(appealIdentifier)) {
            throw new UpdateException();
        }

        Appeal storedAppeal = repository.get(appealIdentifier);
        
        storedAppeal.setStatus(storedAppeal.getStatus());
        storedAppeal.calculateCost();


        return AppealRepresentation.createResponseAppealRepresentation(storedAppeal, appealUri); 
    }
    
    private boolean appealCanBeChanged(Identifier identifier) {
        return AppealRepository.current().get(identifier).getStatus() == AppealStatus.SUBMITTED;
    }
}
