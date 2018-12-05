package com.mainpackage.GraduationProject.util;

public class ErrorMessage {
private String status;
private String message;

public static final String mailAndPasswordError="400";
public static final String nameError="401";
public static final String typeError="402";
public static final String existError="500";
public static final String success="200";



public ErrorMessage(String status,String message)
{
this.message=message;
this.status=status;
}

public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
}
