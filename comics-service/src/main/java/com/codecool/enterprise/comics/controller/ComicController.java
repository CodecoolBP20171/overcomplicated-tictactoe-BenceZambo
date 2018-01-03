package com.codecool.enterprise.comics.controller;

import com.codecool.enterprise.comics.model.Comic;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Controller
public class ComicController {
    Random random = new Random();

    protected int generateComicNumber(){
        return random.nextInt(1929) + 1;
    }

    public String sendingGetRequest() throws Exception {

        int number = generateComicNumber();
        String urlString = "https://xkcd.com/" + number + "/info.0.json";

        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // By default it is GET request
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("Sending get request : "+ url);
        System.out.println("Response code : "+ responseCode);

        // Reading response from input Stream
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        //printing result from response
        System.out.println(response.toString());

        return response.toString();

    }

    @GetMapping(value = "/generate-comic")
    public ResponseEntity<Comic> generateComic(){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(sendingGetRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String imgURL = jsonObject.getString("img");
        Comic comic = new Comic();
        comic.setUrl(imgURL);
        return new ResponseEntity(comic, HttpStatus.OK);
    }
}
