package com.example.moviebooking;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MovieRetriever {

    private final DatebaseHelper dbHelper;
    private SQLiteDatabase db;

    public MovieRetriever(Context context) {
        dbHelper = new DatebaseHelper(context);
    }

    public List<String> getAllMovieTitles() {
        List<String> movieTitles = new ArrayList<>();
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();

            String query = "SELECT title FROM movies"; // Запрос для получения всех названий

            Cursor cursor = db.rawQuery(query, null); // selectionArgs больше не нужны

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int titleColumnIndex = cursor.getColumnIndex("title");

                    if (titleColumnIndex != -1) {
                        String movieTitle = cursor.getString(titleColumnIndex);
                        movieTitles.add(movieTitle);
                    } else {
                        Log.e("MovieRetriever", "Column 'title' not found in table 'movies'");
                    }
                } while (cursor.moveToNext()); // Перебираем все записи
                cursor.close();
            }

        } catch (Exception e) {
            Log.e("MovieRetriever", "Error getting movie titles", e);
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
            dbHelper.close();
        }
        return movieTitles;
    }

    public List<String> getAllMovieDescriptions() {
        List<String> movieDescriptions = new ArrayList<>();
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();

            String query = "SELECT decscription_mobile  FROM movies";

            Cursor cursor = db.rawQuery(query, null); // Выполняем запрос

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int descriptionColumnIndex = cursor.getColumnIndex("decscription_mobile");

                    if (descriptionColumnIndex != -1) {
                        String movieDescription = cursor.getString(descriptionColumnIndex); // Получаем описание из столбца
                        movieDescriptions.add(movieDescription); // Добавляем в список
                    } else {
                        Log.e("MovieRetriever", "Column 'description' not found in table 'movies'"); // Логируем ошибку, если столбец не найден
                    }
                } while (cursor.moveToNext()); // Перебираем все строки в курсоре
                cursor.close(); // Закрываем курсор
            }

        } catch (Exception e) {
            Log.e("MovieRetriever", "Error getting movie descriptions", e); // Логируем общую ошибку
            movieDescriptions.clear(); //Очищаем список, чтоб вернуть пустой, в случае ошибки
        } finally {
            // Закрываем базу данных в блоке finally, чтобы гарантировать ее закрытие
            if (db != null && db.isOpen()) {
                db.close();
            }
            dbHelper.close();
        }
        return movieDescriptions;
    }

    public Movie getMovieById(int movieId) {
        Movie movie = null;
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();

            String query = "SELECT m.title, m.description, m.duration, m.rating, m.Year, m.country " +
                    "FROM Movies m WHERE movie_id = ?";

            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(movieId)});

            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        // Получаем данные из курсора
                        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                        int duration = cursor.getInt(cursor.getColumnIndexOrThrow("duration"));
                        String rating = cursor.getString(cursor.getColumnIndexOrThrow("rating"));
                        int year = cursor.getInt(cursor.getColumnIndexOrThrow("Year"));
                        String country = cursor.getString(cursor.getColumnIndexOrThrow("country"));

                        movie = new Movie(movieId, title, description, duration, rating, year, country); // Предполагается, что у вас есть класс Movie
                    }
                } finally {
                    cursor.close(); // Закрываем курсор в finally
                }
            }
        } catch (Exception e) {
            Log.e("MovieRetriever", "Error getting movie by id", e);
        }

        return movie;
    }

    public List<String> getMovieGenres(int movieId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<String> genres = new ArrayList<>();
        String query = "SELECT g.genre_name " +
                "FROM Genres g " +
                "INNER JOIN Movie_Genre mg ON g.genre_id = mg.genre_id " +
                "WHERE mg.movie_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(movieId)});

        try {
            while (cursor.moveToNext()) {
                genres.add(cursor.getString(cursor.getColumnIndexOrThrow("genre_name")));
            }
        } finally {
            cursor.close();
            db.close();
        }
        return genres;
    }
}
