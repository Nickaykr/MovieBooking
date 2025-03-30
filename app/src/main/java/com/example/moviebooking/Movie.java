package com.example.moviebooking;

public class Movie {
    private int movieId, duration, year;
    private String title,description, rating, country;

    public Movie(int movieId, String title, String description, int duration, String rating, int year, String country) {
        this.movieId = movieId;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.rating = rating;
        this.year = year;
        this.country = country;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }

    public int getDuration(){
        return duration;
    }

    public int getYear(){
        return year;
    }

    public String getRating(){
        return rating;
    }

    public  String getCountry(){
        return country;
    }
}
