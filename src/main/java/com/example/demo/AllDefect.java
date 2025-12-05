package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class AllDefect implements Strategy {

    @Override
    public Choice initial() {
        return Choice.DEFECT;
    }

    @Override
    public Choice move(Choice myLastChoice, Choice oppenentLastChoice) {
        return Choice.DEFECT;
    }
}
