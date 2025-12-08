package com.example.demo;

public class Game {

    private final Strategy player1;
    private final Strategy player2;
    private final long n;
    private final GameConfiguration gameConfiguration;

    public Game(Strategy player1, Strategy player2, long n, GameConfiguration gameConfiguration) {
        this.player1 = player1;
        this.player2 = player2;
        this.n = n;
        this.gameConfiguration = gameConfiguration;
    }

    public GameResult play() {
        long player1Score = 0;
        long player2Score = 0;

        final RoundResult initial = new RoundResult(player1.initial(), player2.initial());
        player1Score += initial.player1Score(gameConfiguration);
        player2Score += initial.player2Score(gameConfiguration);

        Choice lastChoicePlayer1 = initial.player1Choice;
        Choice lastChoicePlayer2 = initial.player2Choice;
        for (int i = 1; i < n; i++) {
            final Choice choicePlayer1 = player1.move(lastChoicePlayer1, lastChoicePlayer2);
            final Choice choicePlayer2 = player2.move(lastChoicePlayer2, lastChoicePlayer1);
            final RoundResult roundResult = new RoundResult(choicePlayer1, choicePlayer2);
            player1Score += roundResult.player1Score(gameConfiguration);
            player2Score += roundResult.player2Score(gameConfiguration);
            lastChoicePlayer1 = choicePlayer1;
            lastChoicePlayer2 = choicePlayer2;
        }

        return new GameResult(player1Score, player2Score);
    }

    @FunctionalInterface
    public interface GameConfiguration {

        static GameConfiguration defaultGameConfiguration() {
            return (player1Choice, player2Choice) -> {
                if (player1Choice == Choice.COOPERATE && player2Choice == Choice.COOPERATE) {
                    return new GameResult(3, 3);
                } else if (player1Choice == Choice.COOPERATE && player2Choice == Choice.DEFECT) {
                    return new GameResult(1, 5);
                } else if (player1Choice == Choice.DEFECT && player2Choice == Choice.COOPERATE) {
                    return new GameResult(5, 1);
                } else {
                    return new GameResult(0, 0);
                }
            };
        }

        GameResult evalScore(Choice player1Choice, Choice player2Choice);
    }

    public record RoundResult(Choice player1Choice, Choice player2Choice) {
        public long player1Score(GameConfiguration configuration) {
            return configuration.evalScore(player1Choice, player2Choice).player1Score;
        }

        public long player2Score(GameConfiguration configuration) {
            return configuration.evalScore(player1Choice, player2Choice).player2Score;
        }
    }

    public record GameResult(long player1Score, long player2Score) {
    }
}
