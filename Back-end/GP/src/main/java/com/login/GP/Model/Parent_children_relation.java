package com.login.GP.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Parent_children_relation {
    @Id
	private long child_id ;
    private long parent_id ;
	public long getChild_id() {
		return child_id;
	}
	public void setChild_id(long child_id) {
		this.child_id = child_id;
	}
	public long getParent_id() {
		return parent_id;
	}
	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
	}
	
	public Parent_children_relation (long child_id , long parentID) {
		this.child_id = child_id ;
		this.parent_id = parentID ;
	}
}
