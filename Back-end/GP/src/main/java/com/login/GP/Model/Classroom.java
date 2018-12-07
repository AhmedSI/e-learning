package com.login.GP.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Classroom {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
private int classID;
private int creatorID;
private int classType;
private String category;
private String PassCode ;



public int getClassID() {
	return classID;
}
public void setClassID(int classID) {
	this.classID = classID;
}
public int getCreatorID() {
	return creatorID;
}
public void setCreatorID(int creatorID) {
	this.creatorID = creatorID;
}
public int getClassType() {
	return classType;
}
public void setClassType(int classType) {
	this.classType = classType;
}
public String getCategory() {
	return category;
}
public void setCategory(String category) {
	this.category = category;
}
public String getPassCode() {
	return PassCode;
}
public void setPassCode(String passCode) {
	PassCode = passCode;
}
	
	
}
