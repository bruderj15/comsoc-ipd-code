package com.example.demo;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.List;

@SpringBootApplication
public class DemoApplication implements ApplicationRunner {

    private static final long N = 1000000;

    @Autowired
    private List<Strategy> strategies;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(@Nonnull ApplicationArguments args) {
        final HashMap<Class<?>, Long> totalScore = new HashMap<>();
        final HashMap<Class<?>, Long> totalWins = new HashMap<>();
        strategies.forEach(strategy -> totalScore.put(strategy.getClass(), 0L));
        strategies.forEach(strategy -> totalWins.put(strategy.getClass(), 0L));

        for (int i = 0; i < strategies.size(); i++) {
            for (int j = 0; j < i; j++) {
                final Strategy player1 = strategies.get(i);
                final Strategy player2 = strategies.get(j);
                final Game game = new Game(player1, player2, N, Game.GameConfiguration.defaultGameConfiguration());
                final Game.GameResult gameResult = game.play();

                totalScore.computeIfPresent(player1.getClass(), (k, v) -> v + gameResult.player1Score());
                totalScore.computeIfPresent(player2.getClass(), (k, v) -> v + gameResult.player2Score());
                if (gameResult.player1Score() > gameResult.player2Score()) {
                    totalWins.computeIfPresent(player1.getClass(), (k, v) -> v + 1);
                } else if (gameResult.player2Score() > gameResult.player1Score()) {
                    totalWins.computeIfPresent(player2.getClass(), (k, v) -> v + 1);
                }
                System.out.printf("'%s' vs '%s': (%d, %d)%n", player1.getClass().getSimpleName(), player2.getClass().getSimpleName(), gameResult.player1Score(), gameResult.player2Score());
            }
        }

        System.out.println("------------------------------------------");
        totalScore.forEach((strategyClass, score) -> System.out.printf("Score(%s) = %d%n", strategyClass.getSimpleName(), score));
        System.out.println("------------------------------------------");
        totalWins.forEach((strategyClass, score) -> System.out.printf("Wins(%s)  = %d%n", strategyClass.getSimpleName(), score));
        System.out.println("------------------------------------------");
    }
}
