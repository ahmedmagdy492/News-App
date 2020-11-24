package com.migo.apitest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NewsArticleActivity extends AppCompatActivity {

    private TextView txtTitle;
    private TextView txtContent;
    private TextView txtMainTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_article);

        txtTitle = findViewById(R.id.txtArtTitle);
        txtContent = findViewById(R.id.txtContent);
        txtMainTitle = findViewById(R.id.txtMainTitle);

        txtTitle.setText(getIntent().getStringExtra("title"));
        txtContent.setText(getIntent().getStringExtra("content"));
        txtMainTitle.setText(getIntent().getStringExtra("author"));
    }

    public void btnMoreClicked(View view)
    {
        String uri = getIntent().getStringExtra("url");
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(browserIntent);
    }
}