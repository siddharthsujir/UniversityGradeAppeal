package com.cse564hateoas.appealing.activities;

import com.cse564hateoas.appealing.representations.AppealUri;

public class UriExchange {

    public static AppealUri emailForAppeal(AppealUri appealUri) {
        checkForValidAppealUri(appealUri);
        return new AppealUri(appealUri.getBaseUri() + "/email/" + appealUri.getId().toString());
    }
    
    public static AppealUri appealForEmail(AppealUri emailUri) {
        checkForValidEmailUri(emailUri);
        return new AppealUri(emailUri.getBaseUri() + "appeal/" + emailUri.getId().toString());
    }

    public static AppealUri receiptForEmail(AppealUri emailUri) {
        checkForValidEmailUri(emailUri);
        return new AppealUri(emailUri.getBaseUri() + "/receipt/" + emailUri.getId().toString());
    }
    
    public static AppealUri appealForMailSent(AppealUri receiptUri) {
        checkForValidSentEmailUri(receiptUri);
        return new AppealUri(receiptUri.getBaseUri() + "/appeal/" + receiptUri.getId().toString());
    }

    private static void checkForValidAppealUri(AppealUri appealUri) {
        if(!appealUri.toString().contains("/appeal/")) {
            throw new RuntimeException("Invalid Appeal URI");
        }
    }
    
    private static void checkForValidEmailUri(AppealUri email) {
        if(!email.toString().contains("/email/")) {
            throw new RuntimeException("Invalid email URI");
        }
    }
    
    private static void checkForValidSentEmailUri(AppealUri receipt) {
        if(!receipt.toString().contains("/receipt/")) {
            throw new RuntimeException("Invalid Email URI");
        }
    }
}
