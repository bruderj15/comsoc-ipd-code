package com.example.demo;

import org.springframework.stereotype.Component;

/**
 * @author anonymous
 */
@Component
public class Evil implements Strategy {
    private static final Choice[] SUSPICIOUS_ARRAY = {
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
    private boolean suspiciousEvilBoolean = false;

    @Override
    public Choice initial() {
        turnIndex = 0;
        suspiciousEvilBoolean = false;

        Choice move = SUSPICIOUS_ARRAY[turnIndex];
        turnIndex++;
        return move;
    }

    @Override
    public Choice move(Choice myLastChoice, Choice opponentLastChoice) {
        if (!suspiciousEvilBoolean && turnIndex <= SUSPICIOUS_ARRAY.length) {
            Choice suspiciousMove = SUSPICIOUS_ARRAY[turnIndex - 1];
            if (opponentLastChoice != suspiciousMove) {
                suspiciousEvilBoolean = true;
            }
        }

        Choice nextMove;
        if (suspiciousEvilBoolean) {
            nextMove = opponentLastChoice;
        } else if (turnIndex < SUSPICIOUS_ARRAY.length) {
            nextMove = SUSPICIOUS_ARRAY[turnIndex];
        } else {
            nextMove = Choice.DEFECT;
        }

        turnIndex++;
        return nextMove;
    }
}
