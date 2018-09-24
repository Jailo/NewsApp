package com.example.jaielalondon.newsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ArticlesQueryUtils {

    private static String LOG_TAG = ArticlesQueryUtils.class.getSimpleName();

    public ArticlesQueryUtils() {
    }

    public static List<Article> fetchNewsArticles(String queryUrl) {

        // Create an empty array list of Article objects
        List<Article> articlesList = new ArrayList<>();

        URL url = createURL(queryUrl);

        String jsonResponse = "";

        try {
            jsonResponse = makeHTTPRequest(url);

            articlesList = extractFeatureFromJson(jsonResponse);

        } catch (IOException e) {
            e.printStackTrace();
            Log.v(LOG_TAG, "Error calling makeHTTPRequest method: " + e);
        }

        return articlesList;
    }

    // Create a url from the query url string
    private static URL createURL(String urlString) {

        URL url = null;

        try {
            url = new URL(urlString);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.v(LOG_TAG, "Error creating URL: " + e);
        }
        return url;
    }

    /**
     * Perform http request on given URL
     */
    private static String makeHTTPRequest(URL url) throws IOException {

        String jsonResponse = "";

        //IF url is null, return early
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000  /*Milliseconds*/);
            connection.setConnectTimeout(15000 /*Milliseconds*/);
            connection.connect();

            // If response code is 200 (aka, working), then read the input stream and parse the response
            if (connection.getResponseCode() == 200) {

                Log.v(LOG_TAG, "Response code is 200: Aka, everything is working great");
                inputStream = connection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {
                // if response code is not 200, Log error message
                Log.v(LOG_TAG, "Error Response Code: " + connection.getResponseCode());
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.v(LOG_TAG, "Error making http request: " + e);
        } finally {
            // If connection and inputStream are NOT null, close and disconnect them

            if (connection != null) {
                connection.disconnect();
            }

            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies that an IOException
                // could be thrown.
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    // build the json response string
    private static List<Article> extractFeatureFromJson(String jsonString) {

        // If Json string is empty or null, return early
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }

        List<Article> articles = new ArrayList<>();

        try {

            // Get json root string
            JSONObject jsonObjectString = new JSONObject(jsonString);

            // Get the response object in the root json string
            JSONObject responseObj = jsonObjectString.getJSONObject("response");

            // Get th array that contains all the articles inside the response
            JSONArray articlesArray = responseObj.getJSONArray("results");

            // Loop through every article object in the array
            for (int i = 0; i < articlesArray.length(); i++) {

                // get current article at the current index
                JSONObject currentArticle = articlesArray.getJSONObject(i);

                // Get the object that contains all fields of info on the currentArticle
                JSONObject feilds = currentArticle.getJSONObject("fields");

                // Get the articles title
                String title = feilds.getString("headline");

                // Get the articles webUrl
                String url = currentArticle.getString("webUrl");

                // Get the articles picture
                String image = feilds.getString("thumbnail");

                Bitmap picture = readImageUrl(image);

                // Get Authors name
                String author = feilds.optString("byline");

                // Get the date the article was published
                String datePublished = feilds.getString("firstPublicationDate");

                // Get the articles section name
                String section = currentArticle.getString("pillarName");

                // Create a new article and add it to the list
                articles.add(new Article(title, url, author, image, datePublished, section, picture));
            }


        } catch (JSONException e) {
            e.printStackTrace();
            Log.v(LOG_TAG, "Error parsing json:" + e);
        }

        // Return list of articles
        return articles;

    }


    /**
     * Convert inputstream into a string that returns the entire json response
     */
    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        String line = bufferedReader.readLine();

        while (line != null) {
            stringBuilder.append(line);
            line = bufferedReader.readLine();
        }

        return stringBuilder.toString();
    }

    private static Bitmap readImageUrl(String urlString) {

        Bitmap image = null;

        try {
            InputStream in = new java.net.URL(urlString).openStream();
            image = BitmapFactory.decodeStream(in);

        } catch (IOException e) {
            e.printStackTrace();
            Log.v(LOG_TAG, "Error reading image url: " + e);
        }

        return image;
    }

}

