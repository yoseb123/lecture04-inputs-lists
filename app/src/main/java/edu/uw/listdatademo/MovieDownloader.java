package edu.uw.listdatademo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * A class for downloading movie data from the internet.
 * Code adapted from Google.
 *
 * @author Joel Ross
 */
public class MovieDownloader {

    private static final String TAG = "MovieDownloader";

    public static String[] downloadMovieData(String movie) {

        //construct the url for the omdbapi API
        String urlString = "";
        try {
            urlString = "http://www.omdbapi.com/?s=" + URLEncoder.encode(movie, "UTF-8") + "&type=movie";
        }catch(UnsupportedEncodingException uee){
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String movies[] = null;

        try {

            URL url = new URL(urlString);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = reader.readLine();
            while (line != null) {
                buffer.append(line + "\n");
                line = reader.readLine();
            }

            if (buffer.length() == 0) {
                return null;
            }
            String results = buffer.toString();
            results = results.replace("{\"Search\":[","");
            results = results.replace("]}","");
            results = results.replace("},", "},\n");

            Log.v(TAG, results); //for debugging purposes

            movies = results.split("\n");
        }
        catch (IOException e) {
            return null;
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                }
            }
        }

        return movies;
    }
}
