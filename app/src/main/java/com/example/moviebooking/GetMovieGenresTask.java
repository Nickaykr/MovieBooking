package com.example.moviebooking;

import android.os.AsyncTask;
import android.widget.TextView;
import java.util.List;

public class GetMovieGenresTask  extends AsyncTask<Integer, Void, String> {
    private MovieRetriever movieRetriever;
    private TextView movieGenresTextView;

    public GetMovieGenresTask(MovieRetriever movieRetriever, TextView movieGenresTextView) {
        this.movieRetriever = movieRetriever;
        this.movieGenresTextView = movieGenresTextView;
    }

    @Override
    protected String doInBackground(Integer... movieIds) { // Integer... movieIds
        if (movieIds.length > 0) {
            int movieId = movieIds[0]; // Берем первый ID фильма из массива
            List<String> movieGenres = movieRetriever.getMovieGenres(movieId); // используем movieId

            if (movieGenres != null && !movieGenres.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (String genre : movieGenres) {
                    sb.append(genre).append(" "); // Добавляем жанр и запятую с пробелом
                }

                return sb.toString();
            }
        }
        return ""; // Возвращаем пустую строку, если нет жанров
    }

    @Override
    protected void onPostExecute(String genresString) { // String genresString
        if (!genresString.isEmpty()) {
            movieGenresTextView.setText(genresString); // Устанавливаем строку жанров в TextView
        } else {
            movieGenresTextView.setText("Жанры не найдены");
        }
    }
}
