package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class AllCooperate implements Strategy {

    @Override
    public Choice initial() {
        return Choice.COOPERATE;
    }

    @Override
    public Choice move(Choice myLastChoice, Choice oppenentLastChoice) {
        return Choice.COOPERATE;
    }
}
