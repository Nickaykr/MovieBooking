package com.example.moviebooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;



public class interstellar extends AppCompatActivity {

    private int movieId = 1;
    private TextView movieTitleTextView, movieYearTextView, movieCountryTextView, moviedurationTextView,
            movieRatingTextView, movieDiscriptoinTextView, movieGenresTextView;;
    private WebView rutubeWebView;
    private String rutubeIframeCode = "<style>body {" +
            " background-color: beige; margin: 10sp; padding: 0; }</style>" +
            "<iframe\n" +
            "        width=\"100%\"\n" +
            "        height=\"200sp\"\n" +
            "        src=\"https://rutube.ru/play/embed/431d40d0b432eb2c37c8d427bce15356\"\n" +
            "        frameBorder=\"0\"\n" +
            "        allow=\"clipboard-write; autoplay\"\n" +
            "        webkitAllowFullScreen\n" +
            "        mozallowfullscreen\n" +
            "        allowFullScreen\n" +
            "      ></iframe>";

    private Button buttonMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_interstellar);

        movieTitleTextView = findViewById(R.id.movie_title_1);
        movieYearTextView = findViewById(R.id.year);
        movieCountryTextView = findViewById(R.id.coutnry);
        moviedurationTextView = findViewById(R.id.times);
        movieRatingTextView = findViewById(R.id.VOSRAST_raiting);
        movieDiscriptoinTextView = findViewById(R.id.description);
        movieGenresTextView = findViewById(R.id.genres);
        buttonMenu = findViewById(R.id.button);

        rutubeWebView = findViewById(R.id.rutube_web_view);
        rutubeWebView.setWebViewClient(new WebViewClient());
        rutubeWebView.setWebChromeClient(new WebChromeClient());
        rutubeWebView.getSettings().setJavaScriptEnabled(true);
        rutubeWebView.getSettings().setDomStorageEnabled(true);

        String html = "<html><body>" + rutubeIframeCode + "</body></html>";
        rutubeWebView.loadData(html, "text/html", "utf-8");

        MovieRetriever movieRetriever = new MovieRetriever(this);

        new GetMovieDetailsTask(movieRetriever,movieTitleTextView,movieYearTextView,movieCountryTextView,
                moviedurationTextView, movieRatingTextView, movieDiscriptoinTextView).execute(movieId);
        new GetMovieGenresTask(movieRetriever,movieGenresTextView).execute(movieId);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(interstellar.this, buttonMenu);
                popup.getMenuInflater().inflate(R.menu.navigatoin_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.movies) {
                            Intent movie_con = new Intent(interstellar.this, movie_content.class);
                            startActivity(movie_con);
                            return true;
                        } else if (item.getItemId() == R.id.teatrs) {
                            Intent Theatrs = new Intent(interstellar.this, theatrs.class);
                            startActivity(Theatrs);
                            return true;
                        } else if (item.getItemId() == R.id.home) {
                            Intent Home = new Intent(interstellar.this, MainActivity.class);
                            startActivity(Home);
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                popup.show();
            }
        });
    }


}