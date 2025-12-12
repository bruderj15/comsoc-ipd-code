package com.example.demo;

import org.springframework.stereotype.Component;

/**
 * @author anonymous
 */
@Component
public class Virtuous implements Strategy {
    private static final Choice[] COMFORTABLE_ARRAY = {
            Choice.COOPERATE,
            Choice.DEFECT,
            Choice.COOPERATE,
            Choice.COOPERATE,
            Choice.DEFECT,
            Choice.COOPERATE,
            Choice.DEFECT,
            Choice.DEFECT
    };

    private int turnIndex = 0;
    private boolean veryNiceBoolean = false;

    @Override
    public Choice initial() {
        turnIndex = 0;
        veryNiceBoolean = false;

        Choice move = COMFORTABLE_ARRAY[turnIndex];
        turnIndex++;
        return move;
    }

    @Override
    public Choice move(Choice myLastChoice, Choice opponentLastChoice) {
        if (!veryNiceBoolean && turnIndex <= COMFORTABLE_ARRAY.length) {
            Choice veryNormalMove = COMFORTABLE_ARRAY[turnIndex - 1];
            if (opponentLastChoice != veryNormalMove) {
                veryNiceBoolean = true;
            }
        }

        Choice nextMove;
        if (veryNiceBoolean) {
            nextMove = opponentLastChoice;
        } else if (turnIndex < COMFORTABLE_ARRAY.length) {
            nextMove = COMFORTABLE_ARRAY[turnIndex];
        } else {
            nextMove = Choice.COOPERATE;
        }

        turnIndex++;
        return nextMove;
    }
}