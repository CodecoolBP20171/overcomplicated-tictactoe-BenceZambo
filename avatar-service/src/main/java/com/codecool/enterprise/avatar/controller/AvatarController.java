package com.codecool.enterprise.avatar.controller;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Controller
public class AvatarController {



    @GetMapping(value = "/generate-avatar")
    @ResponseBody
    public String generateComic(){
        String avatarMine = "https://api.adorable.io/avatars/285/" + generateRandomString() + ".png";
        return avatarMine;
    }

    private String generateRandomString() {
        Random random = new Random();
        RandomStringGenerator randomStringGenerator =
                new RandomStringGenerator.Builder()
                        .withinRange('0', 'z')
                        .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                        .build();
        return randomStringGenerator.generate(random.nextInt(50) + 1);
    }
}
