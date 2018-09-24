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

        // Create a url form the queryUrl
        URL url = createURL(queryUrl);

        // Make empty json string
        String jsonResponse = "";

        try {
            // TRY to make a http request on url, set the returned json string to jsonResponse
            jsonResponse = makeHTTPRequest(url);

            // Parse through json string to get the article info needed, and create a list of articles
            articlesList = extractFeatureFromJson(jsonResponse);

        } catch (IOException e) {
            e.printStackTrace();
            Log.v(LOG_TAG, "Error calling makeHTTPRequest method: " + e);
        }

        // Return list of articles
        return articlesList;
    }

    /**
     * Create a url from the query url string
     *
     * @param urlString is the url string we want to turn into a proper URL
     * @return a new URL
     */
    private static URL createURL(String urlString) {

        // Create an empty url
        URL url = null;

        try {
            // Try to make a url from urlString param
            url = new URL(urlString);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.v(LOG_TAG, "Error creating URL: " + e);
        }

        //Return the new url
        return url;
    }

    /**
     * Perform http request on given URL
     * @param url is the url obj you want to open a connection on
     * @return Json string from the url
     */
    private static String makeHTTPRequest(URL url) throws IOException {

        // Create an empty json string
        String jsonResponse = "";

        //IF url is null, return early
        if (url == null) {
            return jsonResponse;
        }

        // Create an Http url connection, and an input stream, making both null for now
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {

            // Try to open a connection on the url, request that we GET info from the connection,
            // Set read and connect timeouts, and connect
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000  /*Milliseconds*/);
            connection.setConnectTimeout(15000 /*Milliseconds*/);
            connection.connect();

            // If response code is 200 (aka, working), then get and read from the input stream
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

    /**
     * Go through json string to extract the article information we need
     * @param jsonString is a string of the json response we got from the queryURL
     * @return a list of article objects
     */
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

                // Create bitmap from image url string
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

        // Create a new empty string builder
        StringBuilder stringBuilder = new StringBuilder();

        // Create a bufferedReader that reads from the inputStream param
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,
                Charset.forName("UTF-8")));

        // Create a string that is one line of the buffered reader
        String line = bufferedReader.readLine();

        while (line != null) {
            // Add line to stringBuilder
            stringBuilder.append(line);

            // Set line to the next line in buffered reader
            line = bufferedReader.readLine();
        }

        // Return string builder as a string
        return stringBuilder.toString();
    }


    /**
     * Converts an images url string to a bitmap image so it can be displayed
     * @param urlString is the url of the image
     * @return a bitmap image
     */
    private static Bitmap readImageUrl(String urlString) {

        // Create an empty bitmap image
        Bitmap image = null;

        try {
            // Create and open an inputStream on the urlString
            InputStream in = new java.net.URL(urlString).openStream();

            // Decode the inputStream and set that to the bitmap image
            image = BitmapFactory.decodeStream(in);

        } catch (IOException e) {
            e.printStackTrace();
            Log.v(LOG_TAG, "Error reading image url: " + e);
        }

        // Return the image
        return image;
    }

}

