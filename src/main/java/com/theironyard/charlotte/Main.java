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
    static HashMap<String, User> users = new HashMap<>();

    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        createTables(conn);

        Spark.init();
        Spark.get(
                "/",
                (req, res) -> {
                    Session session = req.session();
                    String name = session.attribute("userName");//id
                    User user = users.get(name);

                    HashMap p = new HashMap<>();
                    if (user == null) {
                        return new ModelAndView(p, "login.html");
                    } else {
                        return new ModelAndView(user, "home.html");
                    }
                },
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/login",
                ((request, response) -> {
                    String name = request.queryParams("loginName");
                    User user = users.get(name);
                    if (user == null) {
                        user = new User(name);
                        users.put(name, user);
                    }

                    Session session = request.session();
                    session.attribute("userName", name);

                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/create-game",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("userName");
                    User user = users.get(name);
                    if (user == null) {
                        throw new Exception("User is not logged in");
                    }

                    String movieName = request.queryParams("movieName");
                    String movieGenre = request.queryParams("movieGenre");
                    String movieQuality = request.queryParams("movieQuality");
                    int movieYear = Integer.valueOf(request.queryParams("movieYear"));
                    int movieRevenue = Integer.valueOf(request.queryParams("movieRevenue"));
                    Movie movie = new Movie(movieName, movieGenre, movieQuality, movieYear, movieRevenue, movieId, movieUser_id);

                    user.movies.add(movie);

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

    public static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS users (id IDENTITY, name VARCHAR, password VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS movies (name VARCHAR, genre VARCHAR, quality VARCHAR, revenue INT, releaseYear INT)");
    }

    public static void insertUser(Connection conn, String name, String password) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, password);
        stmt.execute();
    }

    public static User selectUser(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet results = stmt.executeQuery();
        if (results.next()) {
            String name = results.getString("name");
            String password = results.getString("password");
            ArrayList<Movie> movies = getMoviesByUserId(id);
            return new User(id, name, password, movies);
        }
        return null;
    }

    private static ArrayList<Movie> getMoviesByUserId(Connection conn,int id) {
        // make a new arraylist
        // run the "select * from movies where user_id = ?" query
        // for each row in the resultset:
        // 1. make a new movie
        // 2. add that movie to the arraylist
        // return the arraylist
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users where id = ?");
        stmt.setInt(1, id);
        ResultSet results = stmt.executeQuery();
        if (results.next()) {
            String movie = results.getString("movie");

        }
        return new Movie()

    }
    return movies;
}


