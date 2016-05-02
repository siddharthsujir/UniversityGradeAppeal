package com.cse564hateoas.appealing.model;

import java.util.Random;


public class ItemBuilder {
    public static ItemBuilder item() {
        return new ItemBuilder();
    }

    private Exam exam = Exam.MIDTERM1;    
    private Assignment assignment = Assignment.ASSIGNMENT2;
    private TermPaper paper = TermPaper.PAPER1;
    
    public Item build() {
        
        return new Item(paper, exam, assignment);
    }
    
    public ItemBuilder withExam(Exam exam) {
        this.exam  = exam;
        return this;
    }
    
    public ItemBuilder withAssignment(Assignment assignment) {
        this.assignment = assignment;
        return this;
    }
    
    public ItemBuilder withPaper(TermPaper paper) {
        this.paper = paper;
        return this;
    }
    public Item random() {
     
        Random r = new Random();
        int number = r.nextInt(3)+1;
        System.out.println(number+"Item builder");
        
        
        if(number==1)
        {
            paper = TermPaper.values()[r.nextInt(TermPaper.values().length)];
            return new Item(paper);
        }
        else if(number==2)
        {
           assignment = Assignment.values()[r.nextInt(Assignment.values().length)];
           return new Item(assignment);
        }
        else if(number==3)               
        {
               exam = Exam.values()[r.nextInt(Exam.values().length)];
               return new Item(exam);
        }
       
        else
        return new Item(paper,exam,assignment);
    }
}
