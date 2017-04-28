package com.theironyard.charlotte;

import java.util.ArrayList;

/**
 * Created by jaradtouchberry on 4/25/17.
 */
public class User {
    int id;
    String userName;
    ArrayList<Movie> movies = new ArrayList<>();

    public User(String userName) {
        this.userName = userName;
    }

    public User(int id, String userName, ArrayList<Movie> movies) {
        this.id = id;
        this.userName = userName;
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
}
