package ru.ulmc.school.storm.logic;

import java.io.Serializable;

public class TrumpTweetLogicService implements Serializable {

    public boolean isKeyWordPresent(String text) {
        String lowerCase = text.toLowerCase();
        boolean china = lowerCase.contains("china");
        boolean wall = lowerCase.contains("wall");
        boolean mexico = lowerCase.contains("mexico");
        boolean obama = lowerCase.contains("barackobama");

        return china || wall || mexico || obama;
    }

}
