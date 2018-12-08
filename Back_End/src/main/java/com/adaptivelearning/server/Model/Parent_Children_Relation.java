package com.adaptivelearning.server.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Parent_Children_Relation {
	 @Id
	private Long Child_id ;
    private Long Parent_id ;
    
   public Parent_Children_Relation (){
    	this.Child_id = (long) 0 ;
    	this.Parent_id = (long) 0 ;
    }
    
    public Parent_Children_Relation (Long Child_id , Long Parent_id){
    	this.Child_id = Child_id ;
    	this.Parent_id = Parent_id ;
    }
   
  public Long getChild_id() {
		return Child_id;
	}
	public void setChild_id(Long child_id) {
		Child_id = child_id;
	}
	public Long getParent_id() {
		return Parent_id;
	}
	public void setParent_id(Long parent_id) {
		Parent_id = parent_id;
	}

}
