package com.example.jaielalondon.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class ArticlesLoader extends AsyncTaskLoader<List<Article>> {

    private List<Article> mArticlesList;

    private String QUERY_URL;

    public ArticlesLoader(Context context, String url) {
        super(context);
        QUERY_URL = url;
    }

    @Override
    public List<Article> loadInBackground() {

        return ArticlesQueryUtils.fetchNewsArticles(QUERY_URL);
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
