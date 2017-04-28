package com.theironyard.charlotte;

import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS users (id IDENTITY, name VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS movies (id identity, user_name varchar, name VARCHAR, genre VARCHAR, quality VARCHAR, revenue INT, releaseYear INT)");
    }

    public static void insertUser(Connection conn, String name) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?)");
        stmt.setString(1, name);
        stmt.execute();
    }

    public static User selectUserByUsername(Connection conn, String userName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE name = ?");
        stmt.setString(1, userName);

        ResultSet results = stmt.executeQuery();

        if (results.next()) {
            String name = results.getString("name");
            int id = results.getInt("id");
            ArrayList<Movie> movies = getMoviesByUserName(conn, userName);
            return new User(id, name, movies);
        }

        return null;
    }

    private static ArrayList<Movie> getMoviesByUserName(Connection conn, String name) throws SQLException {
        // make a new arraylist
        ArrayList<Movie> movies = new ArrayList<>();

        // run the "select * from movies where user_id = ?" query
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movies where user_name = ?");
        stmt.setString(1, name);

        ResultSet results = stmt.executeQuery();

        // for each row in the resultset, or
        // "while the resultset has more rows to process":
        while (results.next()) {
            // (id identity, user_id int, name VARCHAR, genre VARCHAR, quality VARCHAR, revenue INT, releaseYear INT)

            // get data from result
            String movieName = results.getString("name");
            String genre = results.getString("genre");
            String quality = results.getString("quality");
            int revenue = results.getInt("revenue");
            int releaseYear = results.getInt("releaseYear");


            // make a new movie with the data

            //public Movie(String name, String genre, String quality, int revenue, int releaseYear, String userName) {
            Movie m = new Movie(movieName, genre, quality, revenue, releaseYear, name);

            // add movie to the movies arraylist
            movies.add(m);
        }

        // return the arraylist
        return movies;
    }

    //(id identity, user_id int, name VARCHAR, genre VARCHAR, quality VARCHAR, revenue INT, releaseYear INT)
    private static void insertMovie(Connection conn, Movie m) throws SQLException {
        // execute a prepared statement which will insert
        // a movie into the movies table.
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO movies VALUES (NULL, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, m.getUserName());
        stmt.setString(2, m.getName());
        stmt.setString(3, m.getGenre());
        stmt.setString(4, m.getQuality());
        stmt.setInt(5, m.getRevenue());
        stmt.setInt(6, m.getReleaseYear());
        // etc.
        stmt.execute();

    }

    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        createTables(conn);

        Spark.init();
        Spark.get(
                "/",
                (req, res) -> {
                    Session session = req.session();
                    String name = session.attribute("userName");

                    User user = selectUserByUsername(conn, name);

                    HashMap p = new HashMap<>();

                    if (user == null) {
                        return new ModelAndView(p, "login.html");
                    } else {
                        p.put("movies", user.movies);
                        return new ModelAndView(p, "home.html");
                    }
                },
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/login",
                ((request, response) -> {
                    String name = request.queryParams("loginName");

                    // get user by username
                    User u = selectUserByUsername(conn, name);

                    // if u is null, then the login wasn't found.
                    // that means we have to insert the user
                    if (u == null) {
                        insertUser(conn, name);
                    }

                    Session session = request.session();
                    session.attribute("userName", name);

                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/create-movie",
                ((request, response) -> {
                    Session session = request.session();
                    // store userid in session when logged in
                    String name = session.attribute("userName");

                    // get a user from the database.
                    User user = selectUserByUsername(conn, name);

                    if (user == null) {
                        throw new Exception("User is not logged in");
                    }

                    String movieName = request.queryParams("movieName");
                    String movieGenre = request.queryParams("movieGenre");
                    String movieQuality = request.queryParams("movieQuality");
                    int movieYear = Integer.valueOf(request.queryParams("movieYear"));
                    int movieRevenue = Integer.valueOf(request.queryParams("movieRevenue"));

                    Movie movie = new Movie(movieName, movieGenre, movieQuality, movieRevenue, movieYear, user.getUserName());

                    insertMovie(conn, movie);
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                })
        );
    }
}
