package com.cse564hateoas.appealing.client.activities;

import java.net.URI;

import com.cse564hateoas.appealing.representations.SendMailRepresentation;

public class GetEmailActivity extends Activity {
    private final URI receiptUri;
    private SendMailRepresentation representation;

    public GetEmailActivity(URI receiptUri) {
        this.receiptUri = receiptUri;
    }

    public void getReceiptForAppeal() {
        try {
            representation = binding.retrieveReceipt(receiptUri);
            actions = new Actions();
            if(representation.getAppealLink() != null) {
                actions.add(new ReadAppealActivity(representation.getAppealLink().getUri()));
            } else {
                actions =  noFurtherActivities();
            }
        } catch (NotFoundException e) {
            actions = noFurtherActivities();
        } catch (ServiceFailureException e) {
            actions = retryCurrentActivity();
        }
    }

    public SendMailRepresentation getEmail() {
        return representation;
    }
}
