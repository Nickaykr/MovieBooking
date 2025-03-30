package com.example.moviebooking;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class GetMovieDetailsTask extends AsyncTask<Integer, Void, Movie> {
    private TextView movieTitleTextView, movieYearTextView, movieCountryTextView, movieTimeTextView,
            movieRatingTextView, movieDiscriptoinTextView;
    private MovieRetriever movieRetriever;

    public GetMovieDetailsTask(MovieRetriever movieRetriever, TextView movieTitleTextView,
                               TextView movieYearTextView, TextView movieCountryTextView,
                               TextView movieTimeTextView, TextView movieRatingTextView,
                               TextView movieDiscriptoinTextView) {
        this.movieRetriever = movieRetriever;
        this.movieTitleTextView = movieTitleTextView;
        this.movieYearTextView = movieYearTextView;
        this.movieCountryTextView = movieCountryTextView;
        this.movieTimeTextView = movieTimeTextView;
        this.movieRatingTextView = movieRatingTextView;
        this.movieDiscriptoinTextView = movieDiscriptoinTextView;
    }

    protected Movie doInBackground(Integer... movieIds) {
        if (movieIds.length > 0) {
            int movieId = movieIds[0]; // Берем первый ID фильма из массива
            Movie movie = movieRetriever.getMovieById(movieId);
            return movie;
        }
        return null; // Возвращаем null, если не передан ID фильма
    }

    @Override
    protected void onPostExecute(Movie movie) {
        if (movie != null) {
            movieTitleTextView.setText(movie.getTitle());
            movieYearTextView.setText(String.valueOf(movie.getYear()));
            movieCountryTextView.setText(movie.getCountry());
            movieTimeTextView.setText(String.valueOf(movie.getDuration()));
            movieRatingTextView.setText(movie.getRating());
            movieDiscriptoinTextView.setText(movie.getDescription());
        } else {
            movieTitleTextView.setText("Movie not found");
            movieYearTextView.setText("Movie not found");
            movieCountryTextView.setText("Movie not found");
            movieTimeTextView.setText("0");
            movieRatingTextView.setText("NOt found");
            movieDiscriptoinTextView.setText("NOt found");
            Log.w("GetMovieDetailsTask", "Movie not found");
        }
    }


}

