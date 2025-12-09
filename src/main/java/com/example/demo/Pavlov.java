package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class Pavlov implements Strategy {

    @Override
    public Choice initial() {
        return Choice.COOPERATE;
    }

    @Override
    public Choice move(Choice myLastChoice, Choice opponentLastChoice) {
        if (myLastChoice == Choice.COOPERATE && opponentLastChoice == Choice.COOPERATE) {
            return myLastChoice;
        } else if (myLastChoice == Choice.DEFECT && opponentLastChoice == Choice.COOPERATE) {
            return myLastChoice;
        } else {
            return myLastChoice.negate();
        }
    }
}
