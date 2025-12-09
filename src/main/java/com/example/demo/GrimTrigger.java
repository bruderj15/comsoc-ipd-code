package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class GrimTrigger implements Strategy {

    private boolean opponentEverDefected = false;

    @Override
    public Choice initial() {
        return Choice.COOPERATE;
    }

    @Override
    public Choice move(Choice myLastChoice, Choice opponentLastChoice) {
        if (opponentLastChoice == Choice.DEFECT) {
            opponentEverDefected = true;
        }

        return opponentEverDefected ? Choice.DEFECT : Choice.COOPERATE;
    }
}
