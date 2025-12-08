package com.example.demo;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class DemoApplication implements ApplicationRunner {

    private static final long N = 1_000_000;

    @Autowired
    private List<Strategy> strategies;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(@Nonnull ApplicationArguments args) {
        final HashMap<Class<? extends Strategy>, Long> totalScore = new HashMap<>();
        final HashMap<Class<? extends Strategy>, Long> totalWins = new HashMap<>();

        strategies.forEach(s -> totalScore.put(s.getClass(), 0L));
        strategies.forEach(s -> totalWins.put(s.getClass(), 0L));

        // --- Run all pairings ---
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

                System.out.printf(
                        "'%s' vs '%s': (%d, %d)%n",
                        player1.getClass().getSimpleName(),
                        player2.getClass().getSimpleName(),
                        gameResult.player1Score(),
                        gameResult.player2Score()
                );
            }
        }

        printVisualization(totalScore, totalWins);
    }

    private void printVisualization(
            HashMap<Class<? extends Strategy>, Long> totalScore,
            HashMap<Class<? extends Strategy>, Long> totalWins
    ) {
        System.out.println();
        System.out.println("\u001B[36m╔══════════════════════════════════════════════════╗\u001B[0m");
        System.out.println("\u001B[36m║           Iterated Prisoner's Dilemma            ║\u001B[0m");
        System.out.println("\u001B[36m╚══════════════════════════════════════════════════╝\u001B[0m");

        System.out.println();

        List<? extends Class<? extends Strategy>> strategiesSortedByScore = totalScore.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .map(entry -> (Class<? extends Strategy>) entry.getKey())
                .toList();
        System.out.println("\u001B[33m=== SCORE LEADERBOARD ===\u001B[0m");
        printTable(strategiesSortedByScore, totalScore, "Score");
        printBars(strategiesSortedByScore, totalScore);

        List<? extends Class<? extends Strategy>> strategiesSortedByWins = totalWins.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .map(entry -> (Class<? extends Strategy>) entry.getKey())
                .toList();
        System.out.println("\n\u001B[32m=== WIN COUNT LEADERBOARD ===\u001B[0m");
        printTable(strategiesSortedByWins, totalWins, "Wins");
        printBars(strategiesSortedByWins, totalWins);
    }

    private void printTable(
            List<? extends Class<? extends Strategy>> order,
            HashMap<Class<? extends Strategy>, Long> data,
            String label
    ) {
        String header = String.format(
                "╔════════════════════════╦══════════════╗%n" +
                        "║ %-22s ║ %-12s ║%n" +
                        "╠════════════════════════╬══════════════╣",
                "Strategy", label
        );
        System.out.println(header);

        for (Class<?> cls : order) {
            System.out.printf(
                    "║ %-22s ║ %-12d ║%n",
                    cls.getSimpleName(),
                    data.get(cls)
            );
        }
        System.out.println("╚════════════════════════╩══════════════╝");
    }

    private void printBars(List<? extends Class<? extends Strategy>> order, HashMap<Class<? extends Strategy>, Long> data) {
        long max = data.values().stream().max(Long::compare).orElse(1L);

        for (Class<?> cls : order) {
            long value = data.get(cls);

            System.out.printf("%-22s %12d ", cls.getSimpleName(), value);
            printBar(value, max);
            System.out.println();
        }
    }

    private void printBar(long value, long max) {
        int width = 40;
        int filled = (int) ((double) value / max * width);

        System.out.print(" [");
        for (int i = 0; i < filled; i++) System.out.print("█");
        for (int i = filled; i < width; i++) System.out.print(" ");
        System.out.print("]");
    }
}
