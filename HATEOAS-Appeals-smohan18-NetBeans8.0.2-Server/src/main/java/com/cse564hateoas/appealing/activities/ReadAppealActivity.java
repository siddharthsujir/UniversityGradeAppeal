package com.cse564hateoas.appealing.activities;

import com.cse564hateoas.appealing.model.Identifier;
import com.cse564hateoas.appealing.model.Appeal;
import com.cse564hateoas.appealing.repositories.AppealRepository;
import com.cse564hateoas.appealing.representations.AppealRepresentation;
import com.cse564hateoas.appealing.representations.AppealUri;

public class ReadAppealActivity {
    public AppealRepresentation retrieveByUri(AppealUri appealUri) {
        Identifier identifier  = appealUri.getId();
        
        
         System.out.println("Inside Read Acti 1"+identifier );
        Appeal appeal = AppealRepository.current().get(identifier);
        System.out.println("Inside Read Acti 2" +appeal.getItems());
               
                
        
        
        if(appeal == null) {
            
            throw new NoSuchAppealException();
        }
        else
        {
        System.out.println("Inside else");
                
                
        }
        
        return AppealRepresentation.createResponseAppealRepresentation(appeal, appealUri);
    }
}
