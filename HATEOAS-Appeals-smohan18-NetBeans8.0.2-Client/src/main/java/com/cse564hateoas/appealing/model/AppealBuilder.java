package com.cse564hateoas.appealing.model;

import static com.cse564hateoas.appealing.model.ItemBuilder.item;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppealBuilder {
    
    private static final Logger LOG = LoggerFactory.getLogger(AppealBuilder.class);
    
    public static AppealBuilder appeal() {
        return new AppealBuilder();
    }

    private Item items = null;
    private AppealStatus status = AppealStatus.SUBMITTED;
    
    private void defaultItems() {
      
        Item items = new Item();
          items=item().build();
        this.items = items;
    }
    
    private void corruptItems() {
     
        Item items = new Item();
       items=null;
        this.items = items;
    }
   
    
    public Appeal build() {
  
        if(items == null) {
            defaultItems();
        }
        return new Appeal(items, status);
    }

    public AppealBuilder withItem(Item item) {
   
        if(items == null) {
            items = new Item();
        }
        items=item;
        return this;
    }


    public AppealBuilder withCorruptedValues() {

        corruptItems();
        return this;
    }
    
    public AppealBuilder withStatus(AppealStatus status) {

        this.status = status;
        return this;
    }

    public AppealBuilder withRandomItems() {

        int numberOfItems = 1;
        this.items = new Item();
        for(int i = 0; i < numberOfItems; i++) {
            items=item().random();//.build();
        }
        return this;
    }

}
