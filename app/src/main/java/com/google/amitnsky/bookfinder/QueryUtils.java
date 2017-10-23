package com.google.amitnsky.bookfinder;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by amitnsky on 21-10-2017.
 */

public class QueryUtils {
    private static final String LOG_TAG = "LOG_TAG";
    private static final String BASE_URL_STRING = "https://www.googleapis.com/books/v1/volumes?q=";

    /**
     * performs http query on google book
     *
     * @param searchKey
     * @return jsonString
     */
    public String makeHttpRequest(String searchKey) {
        String jsonResponse = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        URL url = createUrl(searchKey);
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(20000);
            httpURLConnection.setReadTimeout(20000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in reading Stream : ", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return jsonResponse;
    }

    /**
     * information from a jsonSring returned by makehttpreq is paresed
     * new books are added in arraylist
     *
     * @param searchKey
     * @return book arraylist
     */
    public ArrayList<Book> extractInformation(String searchKey) {
        String jsonString = makeHttpRequest(searchKey);
        ArrayList<Book> books = new ArrayList<>();
        String title, infolink, author="";
        int ratingCount;
        float rating;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray items = jsonObject.getJSONArray("items");
            JSONObject volume = null;
            JSONObject item = null;
            int i=0;
            while (i<items.length()-1) {
                item = items.getJSONObject(i);
                volume = item.getJSONObject("volumeInfo");
                title = volume.getString("title");
                if (volume.has("authors"))
                    author = volume.getJSONArray("authors").getString(0);
                infolink = volume.getString("infoLink");
                if (volume.has("ratingsCount")) {
                    ratingCount = volume.getInt("ratingsCount");
                    rating = (float) volume.getLong("averageRating");
                } else {
                    //provide default value as for some books these fields are not avail.
                    //you may also use method setVisibility gone for views
                    //which does nt hv item.
                    ratingCount = 25;
                    rating = 3.5f;
                }
                books.add(new Book(title, author, rating, ratingCount, infolink));
                i++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return books;
    }

    /**
     * @param inputStream
     * @return String read from InputStream
     * @throws IOException
     */
    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder result = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                result.append(line);
                line = reader.readLine();
            }
        }
        return result.toString();
    }

    /**
     * @param String keyString
     * @return url built using keyString and base_url
     */
    private URL createUrl(String keyString) {
        //use proper encode as keyString may contain some special characters.
        try {
            keyString = URLEncoder.encode(keyString, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //final string of url
        String urlString = BASE_URL_STRING + keyString;
        urlString += "&maxResults=25";
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error in Creating URL : ", e);
        }
        return url;
    }
}
