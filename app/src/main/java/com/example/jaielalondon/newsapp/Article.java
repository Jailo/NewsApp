package com.example.jaielalondon.newsapp;

public class Article {
    /** Title of the article */
    private String mTitle;

    public Article(String title) {
        mTitle = title;
    }

    /**
     *
     * @return the articles title
     */
    public String getTitle() {
        return mTitle;
    }
}

