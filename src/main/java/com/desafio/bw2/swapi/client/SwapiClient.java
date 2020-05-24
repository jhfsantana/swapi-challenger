package com.desafio.bw2.swapi.client;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class SwapiClient {

    public String request(String url) throws IOException {

        URL obj = new URL(url);

        HttpURLConnection con  = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        // Send post request
        con.setDoOutput(true);

        int responseCode = con.getResponseCode();


        while(responseCode == HttpURLConnection.HTTP_MOVED_TEMP ||responseCode == HttpURLConnection.HTTP_MOVED_PERM) {
            responseCode = con.getResponseCode();
            String location = con.getHeaderField("Location");

            if(location != null) {
                URL newUrl = new URL(location);
                con = (HttpURLConnection) newUrl.openConnection();
            }
        }


        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer responseStr = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            responseStr.append(inputLine);
        }
        in.close();

        return responseStr.toString();
    }
}
