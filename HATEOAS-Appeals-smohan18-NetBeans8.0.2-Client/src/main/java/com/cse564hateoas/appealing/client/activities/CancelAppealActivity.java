package com.cse564hateoas.appealing.client.activities;

import java.net.URI;

public class CancelAppealActivity extends Activity {

    private final URI cancelUri;

    public CancelAppealActivity(URI cancelUri) {
        this.cancelUri = cancelUri;
    }

    public void cancelAppeal() {
        try {           
            binding.storedAppeal(cancelUri);
            actions = noFurtherActivities();
        } catch (ServiceFailureException e) {
            actions = retryCurrentActivity();
        } catch (CannotCancelException e) {
            actions = noFurtherActivities();
        } catch (NotFoundException e) {
            actions = noFurtherActivities();
        }
    }
}
