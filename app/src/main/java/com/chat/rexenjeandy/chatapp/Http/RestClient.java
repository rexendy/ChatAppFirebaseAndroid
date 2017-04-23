package com.chat.rexenjeandy.chatapp.Http;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by rexenjeandy on 4/22/17.
 */
public class RestClient {
    /**
     * Performs a GET request with validation.
     *
     * @param api_url
     * @return JSONObject json
     * @throws JSONException
     */
    public JSONObject get(String api_url) throws JSONException {

        URL url;
        JSONObject json = null;
        HttpURLConnection conn = null;

        try {
            url = new URL(api_url);

            // create a connection to the url object
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // retrieve the response from the input stream
            InputStream in = new BufferedInputStream(conn.getInputStream());

            // create a json object from the parsed response
            json = new JSONObject(getResponseText(in));
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }  catch (NoSuchElementException e) {
            JSONObject object = new JSONObject();
            object.put("status", "500");
            return object;
        } finally {
            conn.disconnect();
        }

        return json;
    }

    /**
     * Converts the InputStream into a String.
     *
     * @param inStream
     * @return String
     */
    private String getResponseText(InputStream inStream) {
        String result = "";
        Scanner scanner = new Scanner(inStream, "UTF-8");
        result = scanner.useDelimiter("\\A").next();

        scanner.close();

        return result;
    }
}