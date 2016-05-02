package com.cse564hateoas.appealing.client.activities;

import java.net.URI;

import com.cse564hateoas.appealing.client.ClientAppeal;
import com.cse564hateoas.appealing.representations.AppealRepresentation;

public class ReadAppealActivity extends Activity {

    private final URI appealUri;
    private AppealRepresentation currentAppealRepresentation;

    public ReadAppealActivity(URI appealUri) {
        this.appealUri = appealUri;
    }

    public void readAppeal() {
        try {
            currentAppealRepresentation = binding.retrieveAppeal(appealUri);
            actions = new RepresentationHypermediaProcessor().extractNextActionsFromOrderRepresentation(currentAppealRepresentation);
        } catch (NotFoundException e) {
            actions = new Actions();
            actions.add(new PlaceAppealActivity());
        } catch (ServiceFailureException e) {
            actions = new Actions();
            actions.add(this);
        }
    }

    public ClientAppeal getAppeal() {
        return new ClientAppeal(currentAppealRepresentation.getAppeal());
    }
}
