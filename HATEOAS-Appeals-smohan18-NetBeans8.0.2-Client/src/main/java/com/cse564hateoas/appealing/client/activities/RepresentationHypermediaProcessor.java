package com.cse564hateoas.appealing.client.activities;

import com.cse564hateoas.appealing.representations.AppealRepresentation;
import com.cse564hateoas.appealing.representations.EmailRepresentation;

class RepresentationHypermediaProcessor {

    Actions extractNextActionsFromOrderRepresentation(AppealRepresentation representation) {
        Actions actions = new Actions();

        if (representation != null) {

            if (representation.getEmailLink() != null) {
                actions.add(new EmailActivity(representation.getEmailLink().getUri()));
            }

            if (representation.getUpdateLink() != null) {
                actions.add(new UpdateAppealActivity(representation.getUpdateLink().getUri()));
            }

            if (representation.getSelfLink() != null) {
                actions.add(new ReadAppealActivity(representation.getSelfLink().getUri()));
            }

            if (representation.getCancelLink() != null) {
                actions.add(new CancelAppealActivity(representation.getCancelLink().getUri()));
            }
        }

        return actions;
    }

    public Actions extractNextActionsFromPaymentRepresentation(EmailRepresentation representation) {
        Actions actions = new Actions();
        
        if(representation.getAppealLink() != null) {
            actions.add(new ReadAppealActivity(representation.getAppealLink().getUri()));
        }
        
        if(representation.getReceiptLink() != null) {
            actions.add(new GetEmailActivity(representation.getReceiptLink().getUri()));
        }
        
        return actions;
    }

}
