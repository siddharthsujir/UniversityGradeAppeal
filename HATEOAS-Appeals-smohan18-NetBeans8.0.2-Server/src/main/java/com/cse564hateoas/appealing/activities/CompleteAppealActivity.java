package com.cse564hateoas.appealing.activities;

import com.cse564hateoas.appealing.model.Identifier;
import com.cse564hateoas.appealing.model.Appeal;
import com.cse564hateoas.appealing.model.AppealStatus;
import com.cse564hateoas.appealing.repositories.AppealRepository;
import com.cse564hateoas.appealing.representations.AppealRepresentation;

public class CompleteAppealActivity {

    public AppealRepresentation completeAppeal(Identifier id) {
        AppealRepository repository = AppealRepository.current();
        if (repository.has(id)) {
            Appeal appeal = repository.get(id);

            if (appeal.getStatus() == AppealStatus.ACCEPTED) {
                appeal.setStatus(AppealStatus.REJECTED);
            } else if (appeal.getStatus() == AppealStatus.REJECTED) {
                throw new AppealAlreadyEvaluatedException();
            } else if (appeal.getStatus() == AppealStatus.SUBMITTED) {
                throw new AppealNotSentException();
            }

            return new AppealRepresentation(appeal);
        } else {
            throw new NoSuchAppealException();
        }
    }
}
