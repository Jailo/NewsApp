package com.example.jaielalondon.newsapp;

import android.graphics.Bitmap;

public class Article {

    /** Title of the article */
    private String mTitle;

    /**
     * Url string of the article
     */
    private String mWebUrl;

    /**
     * Picture Url string of the article
     */
    private String mPictureUrl;

    /**
     * Picture Url string of the article
     */
    private Bitmap mPicture;

    /**
     * author of the article
     */
    private String mAuthor;

    /**
     * date the article was published
     */
    private String mDatePublished;

    /**
     * article section (i.e Politics)
     */
    private String mSection;


    /**
     * full on Article object
     *
     * @param title         the articles title
     * @param url           Url string of the article
     * @param pictureUrl    Picture Url string of the article
     * @param author        author of the article
     * @param datePublished date the article was published
     */
    public Article(String title, String url, String author, String pictureUrl,
                   String datePublished, String section, Bitmap picture) {

        mTitle = title;
        mWebUrl = url;
        mAuthor = author;
        mPictureUrl = pictureUrl;
        mDatePublished = datePublished;
        mSection = section;
        mPicture = picture;
    }


    /**
     * basic Article object
     * used only when an articles does not have info on its author or date published
     *
     * @param title      the articles title
     * @param url        Url string of the article
     * @param pictureUrl Picture Url string of the article
     */
    public Article(String title, String url, String pictureUrl, String section) {

        mTitle = title;
        mWebUrl = url;
        mPictureUrl = pictureUrl;
        mSection = section;

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
        return mWebUrl;
    }

    /**
     * @return the articles picture url string
     */
    public String getPictureUrl() {
        return mPictureUrl;
    }

    /**
     * @return the articles picture
     */
    public Bitmap getPicture() {
        return mPicture;
    }

    /**
     * @return the articles author
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * @return the articles publish date
     */
    public String getDatePublished() {
        return mDatePublished;
    }

    /**
     * @return the articles section (i.e Politics)
     */
    public String getSection() { return mSection; }

}

