package com.cse564hateoas.appealing.activities;

import com.cse564hateoas.appealing.model.Identifier;
import com.cse564hateoas.appealing.model.Appeal;
import com.cse564hateoas.appealing.model.AppealStatus;
import com.cse564hateoas.appealing.repositories.AppealRepository;
import com.cse564hateoas.appealing.representations.AppealRepresentation;
import com.cse564hateoas.appealing.representations.AppealUri;

public class RemoveAppealActivity {
    public AppealRepresentation delete(AppealUri appealUri) {
        // Discover the URI of the appeal that has been cancelled
        
        Identifier identifier = appealUri.getId();

        AppealRepository appealRepository = AppealRepository.current();

        if (appealRepository.appealNotPlaced(identifier)) {
            throw new NoSuchAppealException();
        }

        Appeal appeal = appealRepository.get(identifier);

        // Can't delete a ready or preparing appeal
        if (appeal.getStatus() == AppealStatus.UNDERREVIEW || appeal.getStatus() == AppealStatus.ACCEPTED) {
            throw new AppealDeletionException();
        }

        if(appeal.getStatus() == AppealStatus.SUBMITTED) { // An unpaid appeal is being cancelled 
            appealRepository.remove(identifier);
        }

        return new AppealRepresentation(appeal);
    }

}
