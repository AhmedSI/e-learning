package com.mainpackage.GraduationProject.util;

public class ErrorMessage {
private String status;
private String message;

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