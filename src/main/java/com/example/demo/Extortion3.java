package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class Extortion3 implements Strategy {

    @Override
    public Choice initial() {
        return Choice.COOPERATE;
    }

    @Override
    public Choice move(Choice myLastChoice, Choice opponentLastChoice) {
        final double p = Math.random();
        if (myLastChoice == Choice.COOPERATE && opponentLastChoice == Choice.COOPERATE) {
            return p < 11d / 13d ? Choice.COOPERATE : Choice.DEFECT;
        } else if (myLastChoice == Choice.COOPERATE && opponentLastChoice == Choice.DEFECT) {
            return p < 0.5 ? Choice.COOPERATE : Choice.DEFECT;
        } else if (myLastChoice == Choice.DEFECT && opponentLastChoice == Choice.COOPERATE) {
            return p < 7d / 26d ? Choice.COOPERATE : Choice.DEFECT;
        } else {
            return Choice.DEFECT;
        }
    }
}
