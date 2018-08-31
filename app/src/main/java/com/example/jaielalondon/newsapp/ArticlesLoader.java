package com.example.jaielalondon.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class ArticlesLoader extends AsyncTaskLoader<List<Article>> {

    private List<Article> mArticlesList;

    public ArticlesLoader(Context context) {
        super(context);
    }

    @Override
    public List<Article> loadInBackground() {

        // Create an empty array list of Article objects
        mArticlesList = new ArrayList<>();

        // Add three placeholder article objects to the list
        mArticlesList.add(new Article("Hello title 1"));
        mArticlesList.add(new Article("Heyo title 2"));
        mArticlesList.add(new Article("Hi there title 3"));

        return mArticlesList;
    }

    @Override
    protected void onStartLoading() {

        // If list of articles is NOT null,
        //Then use deliverResult to get saved list
        if (mArticlesList != null){
            deliverResult(mArticlesList);
        } else {
            // Else create a new list by calling force load
            forceLoad();
        }
    }

    @Override
    public void deliverResult(List<Article> articles) {
        mArticlesList = articles;
        super.deliverResult(articles);
    }
}
