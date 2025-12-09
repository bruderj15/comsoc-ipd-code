package com.example.demo;

/**
 * Choice to be taken in a round of the prisoners' dilemma.
 */
public enum Choice {
    COOPERATE,
    DEFECT;

    public Choice negate() {
        return switch (this) {
            case COOPERATE -> Choice.DEFECT;
            case DEFECT -> Choice.COOPERATE;
        };
    }
}
