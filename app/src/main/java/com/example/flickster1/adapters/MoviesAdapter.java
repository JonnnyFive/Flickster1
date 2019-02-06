package com.example.flickster1.adapters;
// Step 21 create a new package in the Java folder called adapters
// Step 22 create a new java class in the adapters folder called MoviesAdapter
// the job here is to take this "view" attach it to a "view holder" and put it on the "layout manager"

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flickster1.DetailActivity;
import com.example.flickster1.R;
import com.example.flickster1.models.Movie;

import org.parceler.Parcels;

import java.util.List;

// Step 23.5 reference the "ViewHolder" we just defined here
// also click the red light bulb to implement methods so the recycler view will work properly This will create
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    // Step 23.6 bind our data so it can show to the recycler view Do this by creating member variables
    // we populate this object variable and list of movies member variables by making a constructor

    Context context;
    List<Movie> movies;

    // Step 23.7 right click and generate a constructor that takes in these 2 member variables
    public MoviesAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        // Step 23.9 Inflate item_movie.xml gotta change the parameter to parent instead of viewGroup
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        //Step 23.8 tells the recycler view how many items are in the data file changed from 0;
        return movies.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // onBindViewHolder attaches data from the position in the position set
        // and bind it to a view holder that the recycler view has given us
        Movie movie = movies.get(position);
        // Bind the movie data into the view holder
        holder.bind(movie);

    }

    //Step 23 define the view holder by creating a view holder class
    // this will extend to the already built in view holder that's inside of the recycler view.
    class ViewHolder extends RecyclerView.ViewHolder {

        // Step 23.3 define our ID'd variables from item_movie.xml
        // so we can represent item_movie.xml with this "ViewHolder"
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        // Step 28.2 add container to the Id variables list so you can use it when you make the click anywhere tap listener
        RelativeLayout container;

        // Step 23.2 Use the red light bulb here to make a default constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 23.4 get references to text views and image views using the id's we assigned above
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            // Step 28.3 reference to the container view
            container = itemView.findViewById(R.id.container);
        }



        //Method created from onBindViewHolder to attach data to movie
        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            // Step 27 Reference the back drop path if phone is in landscape this will change the poster path to be called by imageUrl
            String imageUrl = movie.getPosterPath();
            // Step 27.3 changes the imageUrl that is called to the backdrop image this is done by checking the context "if phone in landscape"
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.getBackdropPath();
            }
            // Step 24.2 use glide to get poster path
            // Step 27.2 change Glides view to (movie.getPosterPath()) to (imageUrl)
            Glide.with(context).load(imageUrl).into(ivPoster);
            // Step 28 Add click listener on the whole row changed tvTitle to container in step 28.3
            // Step 28 and an on click listener to show you the title
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate to detail activity on tap
                    //Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT). show();
                    // Step 28.5 pass data to launched activites and put "extras" into the bundle for access in the second activity
                    Intent i = new Intent(context, DetailActivity.class);
                   //  i.putExtra("title", movie.getTitle()); this gets removed from step 33.3 changing the way title gets passed in
                    // Step 31 put in a parcelable value to pull an object to the detail activity page,
                    // (splits object up to pass it the reassembles it on the detail activity page) requires adding Parceler library
                    // add parcel wrap to pace in movie object referenced in step 32
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }


}
