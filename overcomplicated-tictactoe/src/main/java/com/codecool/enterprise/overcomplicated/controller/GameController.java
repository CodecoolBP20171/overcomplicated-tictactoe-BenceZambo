package com.codecool.enterprise.overcomplicated.controller;

import com.codecool.enterprise.overcomplicated.model.Player;
import com.codecool.enterprise.overcomplicated.model.TictactoeGame;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Controller
@SessionAttributes({"player", "game", "avatar_uri"})
public class GameController {

    @ModelAttribute("player")
    public Player getPlayer() {
        return new Player();
    }

    @ModelAttribute("game")
    public TictactoeGame getGame() {
        return new TictactoeGame();
    }

    @ModelAttribute("avatar_uri")
    public String getAvatarUri() {
        String avatar = null;
        try {
            avatar = sendingGetRequest("http://localhost:9000/generate-avatar");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return avatar;
    }

    @GetMapping(value = "/")
    public String welcomeView(@ModelAttribute Player player) {
        return "welcome";
    }

    @PostMapping(value="/changeplayerusername")
    public String changPlayerUserName(@ModelAttribute Player player) {
        return "redirect:/game";
    }

    @GetMapping(value = "/game")
    public String gameView(@ModelAttribute("player") Player player, @ModelAttribute("game") TictactoeGame tictactoeGame, Model model) {
        JSONObject comicJSONObject = null;
        JSONObject funFactJSONObject = null;
        try {
            comicJSONObject = new JSONObject(sendingGetRequest("http://localhost:8090/generate-comic"));
            funFactJSONObject = new JSONObject(sendingGetRequest("http://localhost:8099/generate-fun-fact"));
        } catch (Exception e) {
            e.printStackTrace();
            return "game";
        }
        model.addAttribute("funfact", funFactJSONObject.get("joke"));
        model.addAttribute("comic_uri", comicJSONObject.get("url"));
        return "game";
    }

    @GetMapping(value = "/game-move")
    public String gameMove(@ModelAttribute("player") Player player, @ModelAttribute("game") TictactoeGame tictactoeGame, @ModelAttribute("move") int move) {
        switch (move){
            case 0:
                if (tictactoeGame.getNullField() == 0) {
                    tictactoeGame.setNullField(1);
                    break;
                }else{return "redirect:/game";}
            case 1:
                if (tictactoeGame.getFirstField() == 0) {
                    tictactoeGame.setFirstField(1);
                    break;
                }else{return "redirect:/game";}
            case 2:
                if (tictactoeGame.getSecondField() == 0) {
                    tictactoeGame.setSecondField(1);
                    break;
                }else{return "redirect:/game";}
            case 3:
                if (tictactoeGame.getThirdField() == 0) {
                    tictactoeGame.setThirdField(1);
                    break;
                }else{return "redirect:/game";}
            case 4:
                if (tictactoeGame.getFourthField() == 0) {
                    tictactoeGame.setFourthField(1);
                    break;
                }else{return "redirect:/game";}
            case 5:
                if (tictactoeGame.getFifthField() == 0) {
                tictactoeGame.setFifthField(1);
                break;
            }else{return "redirect:/game";}
            case 6:
                if (tictactoeGame.getSixthField() == 0) {
                    tictactoeGame.setSixthField(1);
                    break;
                }else{return "redirect:/game";}
            case 7:
                if (tictactoeGame.getSeventhField() == 0) {
                    tictactoeGame.setSeventhField(1);
                    break;
                }else{return "redirect:/game";}
            case 8:
                if (tictactoeGame.getEightField() == 0) {
                    tictactoeGame.setEightField(1);
                    break;
                }else{return "redirect:/game";}
        }
        if (isWinning(tictactoeGame, 1)){
            tictactoeGame.clearTable();
            System.out.println("Player Won");
        }else {
            if (!isDraw(tictactoeGame)) {
                int AIMove = AIMove(tictactoeGame.getTable());

                switch (AIMove) {
                    case 0:
                        tictactoeGame.setNullField(2);
                        break;
                    case 1:
                        tictactoeGame.setFirstField(2);
                        break;
                    case 2:
                        tictactoeGame.setSecondField(2);
                        break;
                    case 3:
                        tictactoeGame.setThirdField(2);
                        break;
                    case 4:
                        tictactoeGame.setFourthField(2);
                        break;
                    case 5:
                        tictactoeGame.setFifthField(2);
                        break;
                    case 6:
                        tictactoeGame.setSixthField(2);
                        break;
                    case 7:
                        tictactoeGame.setSeventhField(2);
                        break;
                    case 8:
                        tictactoeGame.setEightField(2);
                        break;
                }
                if (isWinning(tictactoeGame, 2)) {
                    tictactoeGame.clearTable();
                    System.out.println("AI Won");
                }
            }else{
                System.out.println("It's a draw");
                tictactoeGame.clearTable();
            }
        }

        return "redirect:/game";
    }

    protected boolean isWinning(TictactoeGame tictactoeGame, int whomTurn){
        // row
        if (tictactoeGame.getNullField() == whomTurn && tictactoeGame.getFirstField() == whomTurn && tictactoeGame.getSecondField() == whomTurn){
            return true;
        }
        if (tictactoeGame.getThirdField() == whomTurn && tictactoeGame.getFourthField() == whomTurn && tictactoeGame.getFifthField() == whomTurn){
            return true;
        }
        if (tictactoeGame.getSixthField() == whomTurn && tictactoeGame.getSeventhField() == whomTurn && tictactoeGame.getEightField() == whomTurn){
            return true;
        }

        //column
        if (tictactoeGame.getNullField() == whomTurn && tictactoeGame.getThirdField() == whomTurn && tictactoeGame.getSixthField() == whomTurn){
            return true;
        }
        if (tictactoeGame.getFirstField() == whomTurn && tictactoeGame.getFourthField() == whomTurn && tictactoeGame.getSeventhField() == whomTurn){
            return true;
        }
        if (tictactoeGame.getSecondField() == whomTurn && tictactoeGame.getFifthField() == whomTurn && tictactoeGame.getEightField() == whomTurn){
            return true;
        }

        //diagonal
        if (tictactoeGame.getNullField() == whomTurn && tictactoeGame.getFourthField() == whomTurn && tictactoeGame.getEightField() == whomTurn){
            return true;
        }if (tictactoeGame.getSecondField() == whomTurn && tictactoeGame.getFourthField() == whomTurn && tictactoeGame.getSixthField() == whomTurn){
            return true;
        }

        return false;
    }

    protected boolean isDraw(TictactoeGame tictactoeGame){
        for (int field:tictactoeGame.getFields()){
            if (field != 0){
                continue;
            }
            return false;
        }
        return true;
    }

    protected String sendingGetRequest(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // By default it is GET request
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        // Reading response from input Stream
        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
        }catch (ConnectException e){
            return null;
        }
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        return response.toString();

    }

    protected int AIMove (String table) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(sendingGetRequest("http://tttapi.herokuapp.com/api/v1/" + table + "/O"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.getInt("recommendation");
    }
}
