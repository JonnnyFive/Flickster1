package com.example.flickster1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.flickster1.adapters.MoviesAdapter;
import com.example.flickster1.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    //step 3 labeling  MOVIE_URL with the sample url
    private static final String MOVIE_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    //Step 11.2 declaring List<movie> as a member variable or "field" removes it form being a local variable
    List<Movie> movies;

    //Add RecyclerView support library to the gradle build file - DONE step 14
    //Define a model class to use as the data source - DONE steps 8-12 and populating a List<Movie> objects by doing the json parsing in these steps
    //Add a RecyclerView to your activity to display the items - DONE steps 15-16
    //Create a custom row layout XML file to visualize the item - DONE steps 17-20 with the item_view.xml
    //Create a RecyclerView.Adapter and ViewHolder to render the item - Done steps 20-24
    //Bind the adapter to the data source to populate the RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Step 25 bind the adapter to the data source to populate the recycler view by
        // pulling out a reference to the RV which is added in our main layout
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        // Step 25.5keep reference to movies alive by declaring it
        movies = new ArrayList<>();
        // Step 25.2 create an instance of the movies adapter that we just wrote
        final MoviesAdapter adapter = new MoviesAdapter(this, movies);
        // Step 25.3 add a layout manager to the recycler view so that the RV knows how to put these items on the screen
        rvMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // Step 25.4 set the adapter on the recycler view
        rvMovies.setAdapter(adapter);

        // Step 2, make libraries available so we can reference locally
        AsyncHttpClient client = new AsyncHttpClient();
        // step 4 actual reference call. comes back as json so we use json handler
        client.get(MOVIE_URL, new JsonHttpResponseHandler(){
            // Step 5 set the over ride for success and failure
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               //step 7 deleted default notation and wrote in new way to find the actual api key
                // from the json object. Also added try catch in case the key doesn't exits in my
                // response. This will print out a stack trace to reference for errors.
                // I also added a Log.d tag to easily locate the json exception.
                try {
                    JSONArray movieJsonArray = response.getJSONArray("results");
                    // Step 11 use static method just created in movie.java to return a list of movie List<Movie> removed.
                    // Stepa 25.6 remove "movies = " and put .addAll to modify the existing ref to movies rather than creating a new one
                    movies.addAll(Movie.fromJsonArray(movieJsonArray));
                    // Step 25.7 notify the adapter that the data its supposed to be rendering has changed
                    adapter.notifyDataSetChanged();
                    Log.d("jonyFive", movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
            }
