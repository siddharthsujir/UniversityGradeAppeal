package com.cse564hateoas.appealing.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cse564hateoas.appealing.representations.Representation;

@XmlRootElement
public class Item {
    @XmlElement(namespace = Representation.CSE564APPEAL_NAMESPACE)
    private Exam exam;
    @XmlElement(namespace = Representation.CSE564APPEAL_NAMESPACE)
    private TermPaper paper;
    @XmlElement(namespace = Representation.CSE564APPEAL_NAMESPACE)
    private Assignment assignment;
    
    
    private String itemType=null;
    /**
     * For JAXB :-(
     */
    Item(){}
    
    public Item(TermPaper paper, Exam exam, Assignment assignment) {
        this.exam = exam;
        this.paper = paper;
        this.assignment = assignment;       
    }
    
    
    public Item(TermPaper paper) {
        
        this.paper = this.paper;      
    }
    
    public Item(Exam exam) {
        this.exam = exam;
           
    }
    
    public Item(Assignment assignment) {
        this.assignment = assignment;       
    }
    
    
    
    public Exam getExam() {
        return exam;
    }

    public TermPaper getSize() {
        return paper;
    }

    public Assignment getAssignment() {
        return assignment;
    }
    
    public String getItemType()
    {
    return itemType;
    }
    
    public void setItemType(String itemType)
    {
    this.itemType=itemType;
    }
    
    public String toString() {
        if(paper!=null)
        {
         setItemType(paper+"");
          return paper+"";
        }
        else if(exam!=null)
        {
            setItemType(exam+"");
           return exam+"";
        }
        else if(assignment!=null)
        {
            setItemType(assignment+"");
            return assignment+"";
        }
        else
            return null;
    }
}