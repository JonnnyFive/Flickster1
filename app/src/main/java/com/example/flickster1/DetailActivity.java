package com.example.flickster1;
// Step 29 create the detail activity folder in java.comFlickster1 called DetailActivity

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flickster1.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class DetailActivity extends YouTubeBaseActivity {

    // Step 39 add youtube api
    private static final String TRAILERS_API = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String YOUTUBE_API_KEY = "AIzaSyCIA_mzKuYIh7XFO-To_XFzeyS9w5w-L3E";
    // Step 29.2 add references to the new xml page just created
    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    // Step 37.2 add the youtube player view variable
    YouTubePlayerView youTubePlayerView;

    // Step 33 added member variable so we can pass in movie
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Step 29.3 get references to text views and image views using the id's we assigned above
        tvOverview = findViewById(R.id.tvOverview);
        tvTitle = findViewById(R.id.tvTitle);
        ratingBar = findViewById(R.id.ratingBar);
        // Step 37 create a reference to the youtube player.
        youTubePlayerView = findViewById(R.id.player);
        // Step 26.52 unpack the extras that you made in step 28.5
        //String title = getIntent().getStringExtra("title"); this is removed when passing in "movie" parcel
        // Step 33.2 pass in movie parcel to actually get "movie",
        // don't forget to add Pacels.unwrap to call the library method and also cast to Movie
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        // Step 29.4 grab title for the detail activity page
        // Step 33.3 changed the way title is received by actually calling the movie object to get parceled
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        // step 33.3 adding rating bar object to get parceld,
        // it is set as a double so we have to cast it down to a float which is a lower precision
        ratingBar.setRating((float) movie.getVoteAverage());

        // Step 39.2 copy this from main activity
        AsyncHttpClient client = new AsyncHttpClient();
        // Step 39.3 pass in api key to parce
        client.get(String.format(TRAILERS_API, movie.getMovieId()), new JsonHttpResponseHandler(){
            // Step 39.4 set on success and on failure method with the over ride method approach
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //"super.onSuccess(statusCode, headers, response);" we don't need this default notation so it was removed
                // Step 39.5 set the actual parcing up for getting the movie object out of the json array called results
                try {
                    JSONArray results = response.getJSONArray("results");
                    // If theres no movie do nothing but if there is play one
                    if (results.length() == 0) {
                        return;
                    }
                    // only returns first trailer
                    JSONObject movieTrailer = results.getJSONObject(0);
                    // reference the api key
                    String youtubeKey = movieTrailer.getString("key");
                    initializeYoutube(youtubeKey);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

        // Step 37.3 Test a youtube video


    }

    private void initializeYoutube(final String youtubeKey) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("jonnyFive", "on init success");
                youTubePlayer.cueVideo(youtubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("jonnyFive", "on init failure");
            }
        });
    }
}
