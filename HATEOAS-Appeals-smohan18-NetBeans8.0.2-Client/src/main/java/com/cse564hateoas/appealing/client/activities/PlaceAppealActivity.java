package com.cse564hateoas.appealing.client.activities;

import java.net.URI;

import com.cse564hateoas.appealing.client.ClientAppeal;
import com.cse564hateoas.appealing.model.Appeal;
import com.cse564hateoas.appealing.representations.AppealRepresentation;

public class PlaceAppealActivity extends Activity {

    private Appeal appeal;

    public void placeAppeal(Appeal appeal, URI appealUri) {
        
        try {
            AppealRepresentation createdAppealRepresentation = binding.createAppeal(appeal, appealUri);
            this.actions = new RepresentationHypermediaProcessor().extractNextActionsFromOrderRepresentation(createdAppealRepresentation);
            this.appeal = createdAppealRepresentation.getAppeal();
        } catch (MalformedAppealException e) {
            this.actions = retryCurrentActivity();
        } catch (ServiceFailureException e) {
            this.actions = retryCurrentActivity();
        }
    }
    
    public ClientAppeal getAppeal() {
        return new ClientAppeal(appeal);
    }
}
