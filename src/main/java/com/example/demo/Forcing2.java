package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class Forcing2 implements Strategy {

    @Override
    public Choice initial() {
        return Choice.COOPERATE;
    }

    @Override
    public Choice move(Choice myLastChoice, Choice opponentLastChoice) {
        final double p = Math.random();
        if (myLastChoice == Choice.COOPERATE && opponentLastChoice == Choice.COOPERATE) {
            return p < 2d / 3d ? Choice.COOPERATE : Choice.DEFECT;
        } else if (myLastChoice == Choice.COOPERATE && opponentLastChoice == Choice.DEFECT) {
            return Choice.DEFECT;
        } else if (myLastChoice == Choice.DEFECT && opponentLastChoice == Choice.COOPERATE) {
            return p < 2d / 3d ? Choice.COOPERATE : Choice.DEFECT;
        } else {
            return p < 1d / 3d ? Choice.COOPERATE : Choice.DEFECT;
        }
    }
}
