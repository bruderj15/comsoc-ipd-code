package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class WinStayLoseShift implements Strategy {

    @Override
    public Choice initial() {
        return Choice.COOPERATE;
    }

    @Override
    public Choice move(Choice myLastChoice, Choice opponentLastChoice) {
        return myLastChoice == opponentLastChoice ? Choice.COOPERATE : Choice.DEFECT;
    }
}
