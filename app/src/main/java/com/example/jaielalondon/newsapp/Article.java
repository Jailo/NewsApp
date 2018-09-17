package com.example.jaielalondon.newsapp;

public class Article {
    /** Title of the article */
    private String mTitle;

    /**
     * Url string of the article
     */
    private String mUrl;


    public Article(String title, String url) {

        mTitle = title;
        mUrl = url;
    }

    /**
     *
     * @return the articles title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * @return the articles url string
     */
    public String getUrl() {
        return mUrl;
    }
}

