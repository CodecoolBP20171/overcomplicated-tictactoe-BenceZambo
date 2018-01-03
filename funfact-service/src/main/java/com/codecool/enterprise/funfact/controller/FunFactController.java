package com.codecool.enterprise.funfact.controller;

import com.codecool.enterprise.funfact.model.FunFact;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Controller
public class FunFactController {

    public String sendingGetRequest() throws Exception {

        String urlString = "https://api.chucknorris.io/jokes/random";

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

    @GetMapping(value = "/generate-fun-fact")
    public ResponseEntity<FunFact> generateComic(){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(sendingGetRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String joke = jsonObject.getString("value");
        FunFact funFact = new FunFact();
        funFact.setJoke(joke);
        return new ResponseEntity(funFact, HttpStatus.OK);
    }
}
