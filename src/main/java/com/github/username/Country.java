package com.github.username;
import java.util.HashSet;

import java.util.Scanner;


public class Country {
  
  protected String name;
   
 protected String population;
   
 protected String area;
   
//protected boolean landlocked;

    /*  Constructs a new Country object from the next line being scanned.
        If there are no more lines, the new object's fields are left null.
    */
    
public Country(Scanner in) {
      
  if (in.hasNextLine()) {
            this.name = in.next();
      
      //this.population = in.next();
    
        //this.area = in.next();
            //this.landlocked = in.nextBoolean();
     
   }

else
{
System.out.println("No next data");
}
    }

   
 @Override
  
  public String toString() {
   
     return String.format("%10s %",  name);
    }
}
