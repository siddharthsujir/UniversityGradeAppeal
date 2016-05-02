package com.cse564hateoas.appealing.model;

import javax.xml.bind.annotation.XmlEnumValue;

public enum Exam {
    @XmlEnumValue(value="midterm1")
    MIDTERM1,
    @XmlEnumValue(value="midterm2")
    MIDTERM2,
    @XmlEnumValue(value="modterm3")
    MIDTERM3
}
