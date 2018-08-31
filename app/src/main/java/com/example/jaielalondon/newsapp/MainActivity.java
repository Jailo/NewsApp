package com.example.jaielalondon.newsapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    private String LOG_TAG = MainActivity.class.getSimpleName();

    private articlesAdapter mAdapter;

    private ArrayList<Article> articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find resource for list view
        ListView listView = findViewById(R.id.listView);

        // Find Resource for error text view and set it to display whenever the list view is empty
        TextView errorTextView = findViewById(R.id.error_text_view);
        listView.setEmptyView(errorTextView);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, this).forceLoad();

        // Create and set adapter on listView
        mAdapter = new articlesAdapter(this,0, new ArrayList<Article>());
        listView.setAdapter(mAdapter);

    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG, "On create loader has been called");
        return new ArticlesLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {
        Log.v(LOG_TAG, "On load finished has been called");
        mAdapter.clear();

        if (articles != null && !articles.isEmpty()) {
            mAdapter.addAll(articles);
        }
    }


    @Override
    public void onLoaderReset(Loader loader) {
        Log.v(LOG_TAG, "On loader reset has been called");
        // Clear adapter when loader is reset
        mAdapter.clear();
    }
}
