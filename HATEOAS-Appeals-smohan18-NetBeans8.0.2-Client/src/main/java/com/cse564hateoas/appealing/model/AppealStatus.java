package com.cse564hateoas.appealing.model;

import javax.xml.bind.annotation.XmlEnumValue;


public enum AppealStatus {
    @XmlEnumValue(value="submitted")
    SUBMITTED,
    @XmlEnumValue(value="under_review")
    UNDERREVIEW, 
    @XmlEnumValue(value="accepted")
    ACCEPTED, 
    @XmlEnumValue(value="rejected")
    REJECTED
}
