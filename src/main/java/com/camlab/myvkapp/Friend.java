package com.camlab.myvkapp;


public class Friend {

    private final String name;
    //TODO revert to bitmap
    private final String photo;

    public Friend(String name, String photo) {
        this.name = name;
        this.photo = photo;
    }


    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }
}