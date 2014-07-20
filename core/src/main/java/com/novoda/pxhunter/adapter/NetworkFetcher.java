package com.novoda.pxhunter.adapter;

import com.novoda.pxhunter.port.Fetcher;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkFetcher implements Fetcher {

    private static final int READ_TIMEOUT_MILLIS = 10 * 1000;
    private static final int CONNECT_TIMEOUT_MILLIS = 10 * 1000;

    @Override
    public InputStream fetch(String url) throws UnableToFetchException {
        try {
            return fetchImageAsStream(url);
        } catch (IOException e) {
            throw new UnableToFetchException(e);
        }
    }

    private InputStream fetchImageAsStream(String target) throws IOException {
        InputStream stream;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
            connection.setReadTimeout(READ_TIMEOUT_MILLIS);
            stream = connection.getInputStream();
        } finally {
            close(connection);
        }

        return stream;
    }

    private void close(HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
        }
    }

}
