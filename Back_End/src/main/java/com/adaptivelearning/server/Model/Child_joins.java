package com.adaptivelearning.server.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Child_joins {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int enrollId;
	private int parentId;
	private int childId;
	private int classId;
	
	public Child_joins() {
	}
	
	public Child_joins(int parentId,int childId,int classId){
		this.childId=childId;
		this.parentId=parentId;
		this.classId=classId;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getChildId() {
		return childId;
	}
	public void setChildId(int childId) {
		this.childId = childId;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	
	
}
