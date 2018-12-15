package com.adaptivelearning.server.constants;

public class Mapping {
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String LOGOUT = "/logout";
    public static final String BASE_AUTH = "/api/auth";

    public static final String PARENT = "/parent";


    //for classrooms
    public static final String CLASSROOMS = "/classrooms";
    public static final String CLASSROOM = "/classroom";
//    public static final String CHILDRENCLASSROOMS = "/childrenclassrooms";// check the commented method in ClassroomController
                                                                            // it should be in the same method of the previous mapping
    public static final String AddChild = "/addchild";
    public static final String EnrollStudent = "/enroll";
    public static final String PARENTENROLL = "/parentenroll";
}
