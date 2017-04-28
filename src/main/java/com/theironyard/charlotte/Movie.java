package com.theironyard.charlotte;

/**
 * Created by jaradtouchberry on 4/25/17.
 */
public class Movie {
    String name;
    String genre;
    String quality;
    int revenue;
    int releaseYear;
    int id;
    String userName;

    public Movie(String name, String genre, String quality, int revenue, int releaseYear, String userName) {
        this.name = name;
        this.genre = genre;
        this.quality = quality;
        this.revenue = revenue;
        this.releaseYear = releaseYear;
        this.userName = userName;
    }

    public Movie() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getReleaseYear() {
        return releaseYear;
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

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;

    }

}

