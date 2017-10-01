package com.example.jessica.venus_match.model;

import java.io.Serializable;

/**
 * Created by Sintiaaa on 28/09/2017.
 */

public class Profile implements Serializable{

    private String name;
    private String username;
    private String age;
    private String gender;
    private String imageID;
    private String country;
    private String about;

    public Profile(String name, String username, String age, String gender, String imageID, String country, String about)
    {
        this.name = name;
        this.username = username;
        this.age = age;
        this.gender = gender;
        this.imageID = imageID;
        this.country = country;
        this.about = about;
    }

    public String getAge()
    {
        return age;
    }

    public String getName()
    {
        return name;
    }

    public String getUsername()
    {
        return username;
    }

    public String getGender()
    {
        return gender;
    }

    public String getImageID()
    {
        return imageID;
    }

    public String getCountry()
    {
        return country;
    }

    public String getAbout() {
        return about;
    }
}
