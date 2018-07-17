package com.erikpoerksen.erikciv.GameLogic.Helpers;

import java.util.ArrayList;

public class GameMessages {
    private ArrayList<String> messages;

    public GameMessages(){
        this.messages = new ArrayList<>();
    }

    public void addMessage(String message){
        messages.add(message);
    }

    public ArrayList<String> getAndClearMessages(){
        ArrayList<String> curMessages = messages;
        messages.clear();
        return curMessages;
    }





}
