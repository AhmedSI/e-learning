package com.adaptivelearning.server.constants;

public class Mapping {
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String BASE_AUTH = "/api/auth";    
    public static final String Class_Rooms = "/classrooms";
    public static final String Class_Rooms_Id = "/classrooms/{id}"; //search or delete by id
    public static final String Class_Rooms_Creator_Id = "/classrooms/creator/{id}"; //return all classrooms for creator id

}
