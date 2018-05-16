package com.example.user.booklisting;

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
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    /**Tag for the log messages*/
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();


    /**
     *Create a private constructor because object of this class should never be created
     */
    private QueryUtils(){
    }

    /**
     *Returns a new URL object fro the given String URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);

        }catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     *Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        //if the URL is null, then return early.
        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000/*milliseconds*/);
            urlConnection.setConnectTimeout(15000/*milliseconds*/);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //If the request was successful (response code 200),
            //then read the input stream and parse the response
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the book  JSOn results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if(inputStream !=null) {
                //Closing the input stream could throw an IOException, which is why
                //the make HttpRequest(URL url) method signature specifies than an IOException could be thrown.
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Book> extractBooks(String bookJSON) {


        //Create an empty ArrayList that we can add books to
        List<Book> books = new ArrayList<>();

        //Try to parse the JSON response String. If there is a problem in JSON
        //Catch the exception so the app does not crash, and print the error message to the logs.

        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(bookJSON);

            // Extract the JSONArray associated with the key called "items",
            // which represents a list of items (or books).
            JSONArray bookArray = baseJsonResponse.getJSONArray("items");

            // For each book in the items array, create a book object.
            for (int i = 0; i < bookArray.length(); i++) {

                // Get a single book at position i within the array of books.
                JSONObject currentBook = bookArray.getJSONObject(i);

                // For that book, extract the JSONObject with the key called "volumeInfo", which is
                // all the detailed information about the book.
                JSONObject bookInfo = currentBook.getJSONObject("volumeInfo");

                String title = bookInfo.getString("title");

                JSONArray bookAuthors = bookInfo.getJSONArray("authors");

                ArrayList<String> authors = new ArrayList<>();
                //Extract author names.
                for (int a = 0; a < bookAuthors.length(); a++) {
                    String author = bookAuthors.getString(a);
                    authors.add(author);
                }

               // String description = bookInfo.getString("description");

                // For that book, extract the JSONObject with the key called "imageLinks", which
                // contain the low and high resolution images of the book
                JSONObject imageLink = bookInfo.getJSONObject("imageLinks");

                //Extract the value of the key called "smallThumbnail".
                String imageUrl = imageLink.getString("smallThumbnail");

                //JSONObject listPrice = bookInfo.getJSONObject("listPrice");
               // String price = listPrice.getString("currencyCode") + listPrice.getDouble("amount");

                books.add(new Book(title, authors,imageUrl));

            }

        } catch (JSONException e) {

            Log.e(LOG_TAG, "Problem parsing the book JSON results", e);
        }

        //Return the list of the books
        return books;
    }

    /**
     * Query the Google Books and return a list of Book objects.
     */
    public static List<Book> fetchBookData(String requestUrl) {

        //Create URL object
        URL url = createUrl(requestUrl);

        //Perform HTTP request to the URL and recieve a JSON response back
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        //Extract relevant details from the JSON response back and create a list of Books
        List<Book> books = extractBooks(jsonResponse);

        //Return a list of Books
        return books;

    }
}
