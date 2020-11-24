package com.migo.apitest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.opengl.Visibility;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.migo.apitest.Models.Article;
import com.migo.apitest.Models.ArticleResponse;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private boolean isSearchVisable = true;

    private RecyclerView rview;
    private ProgressBar loadingBar;
    private EditText txtSearch;
    private static final String API_KEY = "f8a43d9169b44555854db42bb61e4c17";
    private static final String API_URI = "http://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=f8a43d9169b44555854db42bb61e4c17";

    private void sendHttpGetRequest(final String apiUri)
    {
        try
        {
            // loading the data
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(apiUri)
                    .header("accept", "application/json")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if(response.isSuccessful())
                    {
                        final String strResponse = response.body().string();
                        ObjectMapper mapper = new ObjectMapper();
                        final ArrayList<Article> articles = mapper.readValue(strResponse, new TypeReference<ArticleResponse>() {
                        }).getArticles();
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                PostRecyclerViewAdapter adapter = new PostRecyclerViewAdapter(MainActivity.this);
                                adapter.setArticles(articles);
                                rview.setAdapter(adapter);
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            rview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            loadingBar.setVisibility(View.GONE);
            rview.setVisibility(View.VISIBLE);
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            this.finishAffinity();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rview = findViewById(R.id.postsContainer);
        loadingBar = findViewById(R.id.loading);
        txtSearch = findViewById(R.id.txtSearch);

        // getting the top headline news
        sendHttpGetRequest(API_URI);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.topNews)
        {
            final String uri = "http://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=" + API_KEY;
            sendHttpGetRequest(uri);
        }
        else if(item.getItemId() == R.id.allNews)
        {
            final String uri = "http://newsapi.org/v2/everything?domains=wsj.com&apiKey=" + API_KEY;
            sendHttpGetRequest(uri);
        }
        else if(item.getItemId() == R.id.enableSearch)
        {
            LinearLayout searchBar = findViewById(R.id.search_bar);
            if(isSearchVisable)
            {
                searchBar.setVisibility(View.GONE);
                isSearchVisable = false;
            }
            else
            {
                searchBar.setVisibility(View.VISIBLE);
                isSearchVisable = true;
            }
        }
        return true;
    }

    public void btnSearchClicked(View view)
    {
        if(!txtSearch.getText().toString().equals(""))
        {
            final String uri = "http://newsapi.org/v2/everything?q=" + txtSearch.getText().toString() + "&from=2020-11-22&to=2020-11-22&sortBy=popularity&apiKey=" + API_KEY;
            sendHttpGetRequest(uri);
        }
        else
        {
            Toast.makeText(this, "please type something in the search bar", Toast.LENGTH_SHORT).show();
        }
    }
}
