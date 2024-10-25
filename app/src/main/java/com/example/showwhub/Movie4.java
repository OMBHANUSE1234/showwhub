package com.example.showwhub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Movie4 extends AppCompatActivity {

    private ImageView imageViewMovie;
    private static final String movieName = "Devara Part 1"; // Static movie name

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie4); // Assuming you have a layout

        imageViewMovie = findViewById(R.id.imageViewMovie);

        // If you want to display the movie name somewhere, you could use Toast or Log.
        // Example: Toast.makeText(this, movieName, Toast.LENGTH_SHORT).show();
    }

    public void bookTicket(View view) {
        Intent intent = new Intent(Movie4.this, selectdt.class);
        intent.putExtra("movie_name", movieName); // Pass the movie name to the next activity
        startActivity(intent);
    }
    public void trailers(View view) {
        Intent i1=new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/Xg0vBOxV2to?si=pWEphNXHNMjsqRQO"));
        startActivity(i1);


    }
}
