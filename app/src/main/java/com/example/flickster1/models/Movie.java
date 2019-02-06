package com.example.flickster1.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

// Step 32 import parcler usage
@Parcel
// Step 8 created new java package and new class in the package called Movie.
public class Movie {

    // add the movieId variable
    int movieId;
    // double added with field creation from step 33.3 erase private final  from the front
    double voteAverage;
    // Step 8.1 created new fields for the new package
    String posterPath;
    String title;
    String overview;
    // step 26.2 create another field for the backdrop path
    String backdropPath;

    // Step 32 continued empty constructor needed by the Parceler library
    public Movie() {
    }

    // Step 9 created a constructor which takes in json objects to create movie objects
    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        // Step 26.3 parse out back drop and make a constructor for the image
        backdropPath = jsonObject.getString("backdrop_path");
        // Step 33.3 still, add the rating bar object to get parceld and used in the detail activity
        voteAverage = jsonObject.getDouble("vote_average");
        // Parce out the movie ID
        movieId = jsonObject.getInt("id");

    }

    // Step 10 create a list of movie from json array in movie activity
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;

    }

    // Step 12 Generated getter methods for all the member variables so that we can get data out of the movie class
    public String getPosterPath() {
        // Step 13 make the getter method return the full url so we can use it for the poster image
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }
    // Step 26.4 create getter method for the backdrop call, same as poster path above
    public String getBackdropPath() { return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);}

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    // getter added for step 33.3
    public double getVoteAverage() {
        return voteAverage;
    }

    // add getter for movie Id

    public int getMovieId() {
        return movieId;
    }
}
