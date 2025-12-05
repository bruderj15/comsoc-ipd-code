package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class Extortion3 implements Strategy {

    @Override
    public Choice initial() {
        return Choice.COOPERATE;
    }

    @Override
    public Choice move(Choice myLastChoice, Choice oppenentLastChoice) {
        final double p = Math.random();
        if (myLastChoice == Choice.COOPERATE && oppenentLastChoice == Choice.COOPERATE) {
            return p < 11d / 13d ? Choice.COOPERATE : Choice.DEFECT;
        } else if (myLastChoice == Choice.COOPERATE && oppenentLastChoice == Choice.DEFECT) {
            return p < 0.5 ? Choice.COOPERATE : Choice.DEFECT;
        } else if (myLastChoice == Choice.DEFECT && oppenentLastChoice == Choice.COOPERATE) {
            return p < 7d / 26d ? Choice.COOPERATE : Choice.DEFECT;
        } else {
            return Choice.DEFECT;
        }
    }
}
