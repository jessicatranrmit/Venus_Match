package com.example.jessica.venus_match.model;

import java.io.Serializable;

/**
 * Created by Sintiaaa on 28/09/2017.
 */

public class Profile implements Serializable{

    private String first_name;
    private String last_name;
    private String username;
    private String age;
    private String gender;
    private String imageID;
    private String country;
    private String about;

    public Profile(String first_name, String last_name, String username, String age, String gender, String imageID, String country, String about)
    {
        this.first_name = first_name;
        this.last_name = last_name;
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

    public String getFirstName()
    {
        return first_name;
    }

    public String getLastName()
    {
        return last_name;
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
