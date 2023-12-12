package com.example.vinifyveneto.entity;

public class CurrentUser {
    private static String id=null;
    private static String password=null;

    public static void setId(String id){
        CurrentUser.id=id;
    }

    public static void setPassword(String password){
        CurrentUser.password=password;
    }

    public static String getPassword() {
        return password;
    }

    public static String getId() {
        return id;
    }
}
