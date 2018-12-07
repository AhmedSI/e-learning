package com.mainpackage.GraduationProject.util;

public class Constants {
public static final  String ID = "id";
public static  final String Email = "email";
public static final String Name = "name";
public static final String Password = "password";
public static final String Type = "type";



public static String getId(int id) {
	return  (ID+"="+id);
}

public  static String getEmail(String email) {
	return (Email+"="+email);
}

public  String getName(String name) {
	return (Name+"="+name);
}

public  String getPassword(String password) {
	return (Password+"="+password);
}


public  String getType(int type) {
	return (Type+"="+type);
}

/////////////////////////////////////////////////////////////////////////////////////////////////

public void getback() {
	System.out.print(ID +"="+1);
}
}
