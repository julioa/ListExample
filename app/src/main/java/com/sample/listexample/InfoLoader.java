package com.sample.listexample;

import android.content.AsyncTaskLoader;
//import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by julioa on 6/05/15.
 */
public class InfoLoader  extends AsyncTaskLoader<List<JSONObject>> {

    public InfoLoader(Context context) {
        super(context);
    }

    List<JSONObject> list = null;

    @Override
    public List<JSONObject> loadInBackground() {
        try {
            String result =  loadFromNetwork("http://www.jarrebola.com/kezservice/webresources/source/blog");
            JSONObject obj = new JSONObject(result);
            JSONArray jsonArray =  obj.optJSONArray("items");
            List<JSONObject> list = new ArrayList<JSONObject>();
            for (int i=0; i<jsonArray.length(); i++) {
                list.add( jsonArray.optJSONObject(i) );
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        // AsyncTaskLoader doesn't start unless you forceLoad http://code.google.com/p/android/issues/detail?id=14944
        if(list != null){
            deliverResult(list);
        }
        if(takeContentChanged() || list == null){
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }


    @Override
    protected void onReset() {
        super.onReset();
    }



    /** Initiates the fetch operation. */
    private String loadFromNetwork(String urlString) throws IOException {
        InputStream stream = null;
        String str ="";

        try {
            stream = downloadUrl(urlString);
            str = readIt(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return str;
    }

    /**
     * Given a string representation of a URL, sets up a connection and gets
     * an input stream.
     * @param urlString A string representation of a URL.
     * @return An InputStream retrieved from a successful HttpURLConnection.
     * @throws java.io.IOException
     */
    private InputStream downloadUrl(String urlString) throws IOException {
        // BEGIN_INCLUDE(get_inputstream)
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Start the query
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
        // END_INCLUDE(get_inputstream)
    }

    /** Reads an InputStream and converts it to a String.
     * @param stream InputStream containing HTML from targeted site.
     * @return String concatenated according to len parameter.
     * @throws java.io.IOException
     * @throws java.io.UnsupportedEncodingException
     */
    private String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        StringBuilder result = new StringBuilder();
        char[] buffer = new char[1];
        while (reader.read(buffer,0,buffer.length) >= 0) {
            result.append(buffer);
        }
        return result.toString();
    }
}
