package com.cse564hateoas.appealing.activities;

public class InvalidAppealException extends RuntimeException {
    public InvalidAppealException(Exception ex) {
        super(ex);
    }

    public InvalidAppealException() {}

    private static final long serialVersionUID = 2300194325533639524L;

}
