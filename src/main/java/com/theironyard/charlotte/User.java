package com.theironyard.charlotte;

import java.util.ArrayList;

/**
 * Created by jaradtouchberry on 4/25/17.
 */
public class User {
    int id;
    String userName;
    String password;
    ArrayList<Movie> movies = new ArrayList<>();

    public User(int id, String userName, String password, ArrayList<Movie> movies) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.movies = movies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
