package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class Random implements Strategy {
    @Override
    public Choice initial() {
        return Math.random() < 0.5 ? Choice.COOPERATE : Choice.DEFECT;
    }

    @Override
    public Choice move(Choice myLastChoice, Choice opponentLastChoice) {
        return Math.random() < 0.5 ? Choice.COOPERATE : Choice.DEFECT;
    }
}
