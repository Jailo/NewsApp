package com.example.jaielalondon.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private articlesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find resource for list view
        ListView listView = findViewById(R.id.listView);

        // Find Resource for error text view and set it to display whenever the list view is empty
        TextView errorTextView = findViewById(R.id.error_text_view);
        listView.setEmptyView(errorTextView);

        // Create an empty array list of Article objects
        ArrayList<Article> articles = new ArrayList<Article>();

        // Add three placeholder article objects to the list
        articles.add(new Article("Hello title 1"));
        articles.add(new Article("Heyo title 2"));
        articles.add(new Article("Hi there title 3"));

        // Create and set adapter
        mAdapter = new articlesAdapter(this,0, articles);
        listView.setAdapter(mAdapter);
    }
}
