package in.isunny.newsportal.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

import in.isunny.newsportal.interfaces.FeedInterface;
import in.isunny.newsportal.models.Feed;

/**
 * Created by Sunny on 4/17/2018.
 */

public abstract class MyAsyncTask extends AsyncTask<String, String, Void> implements FeedInterface {

    private String uri;
    private StringBuilder result = new StringBuilder();

    protected MyAsyncTask(String u) {
        uri = u;
    }

    protected void onPreExecute() {
    }

    @Override
    protected Void doInBackground(String... params) {

        String url_select = uri;

        try {
            URL url = new URL(url_select);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(20 * 1000);
            urlConnection.setReadTimeout(20 * 1000);
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } else {

                InputStream in = new BufferedInputStream(urlConnection.getErrorStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            }

        } catch (UnsupportedEncodingException e1) {
            Log.e("UnsupportedException", e1.toString());
            e1.printStackTrace();
        } catch (IllegalStateException e3) {
            Log.e("IllegalStateException", e3.toString());
            e3.printStackTrace();
        } catch (IOException e4) {
            Log.e("IOException", e4.toString());
            e4.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Void v) {

        Gson gson = new Gson();
        Type type = new TypeToken<Feed>() {
        }.getType();
        Feed feed = gson.fromJson(result.toString(), type);
        onResponseReceived(feed);
    }

    abstract public void onResponseReceived(Feed result);
}
