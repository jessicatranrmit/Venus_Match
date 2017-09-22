package com.example.jessica.venus_match.mock;

import java.io.Serializable;

/**
 * Created by Sintiaaa on 5/09/2017.
 */

public class User implements Serializable {

    private String username;
    private String email;
    private String password;

    public User(String username, String email, String password)
    {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getEmail()
    {
        return this.email;
    }

    public String getPassword()
    {
        return this.password;
    }


}
