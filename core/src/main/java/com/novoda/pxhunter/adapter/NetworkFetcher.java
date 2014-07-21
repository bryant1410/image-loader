package com.novoda.pxhunter.adapter;

import com.novoda.pxhunter.port.Fetcher;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkFetcher implements Fetcher {

    private static final int READ_TIMEOUT_MILLIS = 10 * 1000;
    private static final int CONNECT_TIMEOUT_MILLIS = 10 * 1000;
    private static final int STREAM_COPY_BUFFER_SIZE_BYTES = 4096;

    @Override
    public byte[] fetch(String url) throws UnableToFetchException {
        try {
            return fetchImageAsStream(url);
        } catch (IOException e) {
            throw new UnableToFetchException(e);
        }
    }

    private byte[] fetchImageAsStream(String target) throws IOException {
        byte[] data;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
            connection.setReadTimeout(READ_TIMEOUT_MILLIS);
            InputStream stream = connection.getInputStream();
            data = convertToByteArray(stream);
        } finally {
            close(connection);
        }

        return data;
    }

    private void close(HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
        }
    }

    private byte[] convertToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int bytesRead;
        byte[] data = new byte[STREAM_COPY_BUFFER_SIZE_BYTES];

        while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        buffer.flush();

        return buffer.toByteArray();
    }

}
