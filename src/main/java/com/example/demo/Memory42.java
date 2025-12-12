package com.example.demo;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.Arrays;


/**
 * @author anonymous
 */
@Component
public class Memory42 implements Strategy {

    static final int mem = 42;
    static final Choice[] past = new Choice[mem];
    static final Random gen = new Random();

    static int some() {
        return gen.nextInt(mem);
    }

    static Choice other(Choice x) {
        return Choice.values()[1 - x.ordinal()];
    }

    static {
        Arrays.fill(past, Choice.COOPERATE);
    }

    @Override
    public Choice initial() {
        return Choice.COOPERATE;
    }

    @Override
    public Choice move(Choice myLastChoice, Choice opponentLastChoice) {
        past[some()] = opponentLastChoice;
        return other(past[some()]);
    }
}