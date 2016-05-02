package com.cse564hateoas.appealing.client.activities;

import java.net.URI;

import com.cse564hateoas.appealing.model.Email;
import com.cse564hateoas.appealing.representations.EmailRepresentation;

public class EmailActivity extends Activity {

    private final URI emailUri;
    private Email email;

    public EmailActivity(URI emailUri) {
        this.emailUri = emailUri;
    }
/*
   
    }*/
    
    public Email getEmail() {
        return email;
    }
}
