package com.example.benha.corrector;

import android.content.Context;
import android.os.AsyncTask;
import com.loopj.android.http.*;

import org.xml.sax.helpers.DefaultHandler;
import cz.msebera.android.httpclient.Header;

public class DictionaryAPI extends AsyncTask {

    private static AsyncHttpClient client = new AsyncHttpClient();

    public final static String apiURL = "http://www.dictionaryapi.com/api/v1/references/collegiate/xml/";
    public final static String key = "?key=386de8a3-2589-4e26-9824-74a538c56aa8";

    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }

    public void dictionaryQuery(Context context, String word, VocabEntry entry) {
        client.get(context, apiURL, new SaxAsyncHttpResponseHandler<DefaultHandler>(new DefaultHandler()) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, DefaultHandler defaultHandler) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, DefaultHandler defaultHandler) {

            }
        });
    }
}


