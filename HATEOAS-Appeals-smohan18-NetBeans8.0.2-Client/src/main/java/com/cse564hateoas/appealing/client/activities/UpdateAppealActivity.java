package com.cse564hateoas.appealing.client.activities;

import java.net.URI;

import com.cse564hateoas.appealing.client.ClientAppeal;
import com.cse564hateoas.appealing.model.Appeal;
import com.cse564hateoas.appealing.representations.AppealRepresentation;

public class UpdateAppealActivity extends Activity {

    private final URI updateUri;
    private AppealRepresentation updatedAppealRepresentation;

    public UpdateAppealActivity(URI updateUri) {
        this.updateUri = updateUri;
    }

    public void updateAppeal(Appeal appeal) {
        try {
            System.out.println("Insider Update Appeal Activity Client Class "+appeal.getStatus());
            updatedAppealRepresentation = binding.updateAppeal(appeal, updateUri);
            actions = new RepresentationHypermediaProcessor().extractNextActionsFromOrderRepresentation(updatedAppealRepresentation);
        } catch (MalformedAppealException e) {
            actions = retryCurrentActivity();
        } catch (ServiceFailureException e) {
            actions = retryCurrentActivity();
        } catch (NotFoundException e) {
            actions = noFurtherActivities();
        } catch (CannotUpdateAppealException e) {
            actions = noFurtherActivities();
        }
    }
    
    public ClientAppeal getAppeal() {
        return new ClientAppeal(updatedAppealRepresentation.getAppeal());
    }
}
