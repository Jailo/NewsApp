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

    public String QUERY_URL = "https://content.guardianapis.com/search?section=travel&show-fields=all&show-references=author&api-key=fa50b045-3c1b-4a1d-b7bf-d98814e513f3";

    private String LOG_TAG = MainActivity.class.getSimpleName();

    private articlesAdapter mAdapter;

    private ProgressBar progressBar;

    private ListView listView;

    private TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find resource for list view
        listView = findViewById(R.id.listView);

        // Find Resource for error text view and set it to display whenever the list view is empty
        errorTextView = findViewById(R.id.error_text_view);

        // Create and set adapter on listView
        mAdapter = new articlesAdapter(this,0, new ArrayList<Article>());
        listView.setAdapter(mAdapter);

        // Find progress bar spinner resource
        progressBar = findViewById(R.id.progress_bar);

        // Get connectivity manager
        ConnectivityManager connectivityManager = (ConnectivityManager) getBaseContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // get active network from the connectivity manager
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        // create a bool that checks if active network is connected or connecting (to the internet)
        Boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        // If connected to internet, initiate the loader to start loading the articles
        if(isConnected){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(0, null, this).forceLoad();
        } else {
            // If not, set the progress bar spinner to be GONE and set the error text view text
            progressBar.setVisibility(View.GONE);
            errorTextView.setText(R.string.internet_connection_error);
        }


    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG, "On create loader has been called");
        return new ArticlesLoader(this, QUERY_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {
        Log.v(LOG_TAG, "On load finished has been called");

        // Set progress spinners visibility to GONE
        progressBar.setVisibility(View.GONE);
        // Clear the adapter to prepare for the new list
        mAdapter.clear();

        // Check if articles is NOT null or empty
        if (articles != null && !articles.isEmpty()) {
            // add all articles to be displayed in the adapter
            mAdapter.addAll(articles);
        } else {
            // if article list IS empty, display an error text view
            listView.setEmptyView(errorTextView);
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
