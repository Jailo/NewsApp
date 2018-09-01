package com.example.jaielalondon.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    private String LOG_TAG = MainActivity.class.getSimpleName();

    private articlesAdapter mAdapter;

    private ArrayList<Article> articles;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find resource for list view
        ListView listView = findViewById(R.id.listView);

        // Find Resource for error text view and set it to display whenever the list view is empty
        TextView errorTextView = findViewById(R.id.error_text_view);
        listView.setEmptyView(errorTextView);

        // Create and set adapter on listView
        mAdapter = new articlesAdapter(this,0, new ArrayList<Article>());
        listView.setAdapter(mAdapter);

        progressBar = findViewById(R.id.progress_bar);

        ConnectivityManager connectivityManager = (ConnectivityManager) getBaseContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        Boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        // If connected to internet, initiate the loader to start loading the articles
        if(isConnected){

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(0, null, this).forceLoad();
        } else {

            progressBar.setVisibility(View.GONE);
            errorTextView.setText("Not connected to the internet,\nplease re-connect and try again");
        }


    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG, "On create loader has been called");
        return new ArticlesLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {
        Log.v(LOG_TAG, "On load finished has been called");

        progressBar.setVisibility(View.GONE);
        mAdapter.clear();

        if (articles != null && !articles.isEmpty()) {
            mAdapter.addAll(articles);
        }
    }


    @Override
    public void onLoaderReset(Loader loader) {
        Log.v(LOG_TAG, "On loader reset has been called");
        // Clear adapter and make the progress bar spinner visible when loader is reset
        mAdapter.clear();
        progressBar.setVisibility(View.VISIBLE);
    }
}
