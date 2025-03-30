package com.example.moviebooking;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.AsyncTask;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;

import java.util.List;
import java.util.Map;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private Button buttonMenu;
    private TextView movieTitleTextView1, movieDescriptionTextView1,
            movieTitleTextView2, movieDescriptionTextView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        buttonMenu = findViewById(R.id.button);
        movieTitleTextView1 = findViewById(R.id.movie_title_1);
        movieTitleTextView2 = findViewById(R.id.movie_title_2);
        movieDescriptionTextView1 = findViewById(R.id.movie_description_1);
        movieDescriptionTextView2 = findViewById(R.id.movie_description_2);


        new GetMovieTitlesTask().execute();
        new GetMovieDiscripyionTask().execute();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, buttonMenu);
                popup.getMenuInflater().inflate(R.menu.navigatoin_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.movies) {
                            Intent movie_con = new Intent(MainActivity.this, movie_content.class);
                            startActivity(movie_con);
                            return true;
                        } else if (item.getItemId() == R.id.teatrs) {
                            Intent Theatrs = new Intent(MainActivity.this, theatrs.class);
                            startActivity(Theatrs);
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                popup.show();
            }
        });

        movieTitleTextView1.setOnClickListener(v ->{
            Intent movieInterstellar = new Intent(MainActivity.this, interstellar.class);

            startActivity(movieInterstellar);
        });
    }

    private class GetMovieTitlesTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... voids) {
            MovieRetriever movieRetriever = new MovieRetriever(MainActivity.this);
            List<String> movieTitles = movieRetriever.getAllMovieTitles();
            return movieTitles;
        }
        @Override
        protected void onPostExecute(List<String> movieTitles) {
            if (movieTitles != null && !movieTitles.isEmpty()) {
                movieTitleTextView1.setText(movieTitles.size() > 0 ? movieTitles.get(0) : "");
                movieTitleTextView2.setText(movieTitles.size() > 1 ? movieTitles.get(1) : "");
            } else {
                movieTitleTextView1.setText("No movies found");
                movieTitleTextView2.setText("No movies found");
            }
        }
    }

    private class GetMovieDiscripyionTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... voids) {
            MovieRetriever movieRetriever = new MovieRetriever(MainActivity.this);
            List<String> movieDescriptions = movieRetriever.getAllMovieDescriptions();
            return movieDescriptions;
        }
        @Override
        protected void onPostExecute(List<String> movieDescriptions) {
            if (movieDescriptions != null && !movieDescriptions.isEmpty()) {
                movieDescriptionTextView1.setText(movieDescriptions.size() > 0 ? movieDescriptions.get(0) : "");
                movieDescriptionTextView2.setText(movieDescriptions.size() > 1 ? movieDescriptions.get(1) : "");
            } else {
                movieDescriptionTextView1.setText("No movies found");
                movieDescriptionTextView1.setText("No movies found");
            }
        }
    }
}