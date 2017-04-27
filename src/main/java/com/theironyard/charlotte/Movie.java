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
    int user_id;

    public Movie(String name, String genre, String quality, int revenue, int releaseYear, int id, int user_id) {
        this.name = name;
        this.genre = genre;
        this.quality = quality;
        this.revenue = revenue;
        this.releaseYear = releaseYear;
        this.id = id;
        this.user_id = user_id;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;

    }

}

