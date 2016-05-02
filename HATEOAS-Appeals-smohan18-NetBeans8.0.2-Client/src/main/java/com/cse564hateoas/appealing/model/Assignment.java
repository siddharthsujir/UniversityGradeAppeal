package com.cse564hateoas.appealing.model;

import javax.xml.bind.annotation.XmlEnumValue;

public enum Assignment {
    @XmlEnumValue(value="assignment1")
    ASSIGNMENT1(1.5),
    @XmlEnumValue(value="assignment2")
    ASSIGNMENT2(2.0),
    @XmlEnumValue(value="assignment3")
    ASSIGNMENT3(2.0);


    final double mark;
    Assignment(double mark) { this.mark = mark; }
    public double mark() { return this.mark; }
 }
