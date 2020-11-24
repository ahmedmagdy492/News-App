package com.migo.apitest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.migo.apitest.Models.Article;

import java.util.ArrayList;

public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.RecyclerViewHolder> {

    private Context context;
    private ArrayList<Article> articles = new ArrayList<>();

    public PostRecyclerViewAdapter(Context context)
    {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        holder.txtTitle.setText(articles.get(position).getTitle());
        holder.txtBody.setText(articles.get(position).getAuthor());
        holder.txtDate.setText(articles.get(position).getPublishedAt());

        Glide.with(context)
                .asBitmap()
                .load(articles.get(position).getUrlToImage())
                .placeholder(R.mipmap.ic_loading)
                .into(holder.img);


        holder.mainContainer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(context, NewsArticleActivity.class);
                    intent.putExtra("title", PostRecyclerViewAdapter.this.articles.get(position).getTitle());
                    intent.putExtra("content", PostRecyclerViewAdapter.this.articles.get(position).getContent());
                    intent.putExtra("url", PostRecyclerViewAdapter.this.articles.get(position).getUrl());
                    intent.putExtra("author", PostRecyclerViewAdapter.this.articles.get(position).getAuthor());
                    context.startActivity(intent);
                }
                catch (Exception ex)
                {
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        private RelativeLayout mainContainer;
        private TextView txtTitle;
        private TextView txtBody;
        private TextView txtDate;
        private ImageView img;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mainContainer = itemView.findViewById(R.id.mainContainer);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtBody = itemView.findViewById(R.id.txtAuthor);
            txtDate = itemView.findViewById(R.id.postBody);
            img = itemView.findViewById(R.id.newImg);
        }
    }
}
