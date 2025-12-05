package com.example.demo;

/**
 * Interface specifying a strategy in the iterated prisoners' dilemma.
 */
public interface Strategy {

    /**
     * The choice of this strategy for the first move in the iterated prisoners' dilemma.
     *
     * @return the initial choice
     */
    Choice initial();

    /**
     * The choice of this strategy for any non-first move in the iterated prisoners' dilemma.
     * <p>
     * Only depends on the previous move.
     *
     * @param myLastChoice       the choice this strategy made in the last move
     * @param opponentLastChoice the choice the opponents' strategy made in the last move
     * @return the choice for this move
     */
    Choice move(Choice myLastChoice, Choice opponentLastChoice);
}
