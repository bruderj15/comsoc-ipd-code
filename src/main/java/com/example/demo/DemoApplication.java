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

    private static final long N = 10_000_000;

    @Autowired
    private List<Strategy> strategies;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(@Nonnull ApplicationArguments args) {
        final Game.GameConfiguration gameConfiguration = Game.GameConfiguration.defaultGameConfiguration();
        printGameConfiguration();

        final HashMap<Class<? extends Strategy>, Long> totalScore = new HashMap<>();
        final HashMap<Class<? extends Strategy>, Long> totalWins = new HashMap<>();

        strategies.forEach(s -> totalScore.put(s.getClass(), 0L));
        strategies.forEach(s -> totalWins.put(s.getClass(), 0L));

        System.out.println();
        System.out.println("\u001B[36m╔═════════════════════════════════════════════════════════════════╗\u001B[0m");
        System.out.println("\u001B[36m║           Iterated Prisoner's Dilemma (Round Results)           ║\u001B[0m");
        System.out.println("\u001B[36m╚═════════════════════════════════════════════════════════════════╝\u001B[0m");

        for (int i = 0; i < strategies.size(); i++) {
            for (int j = 0; j < i; j++) {
                final Strategy player1 = strategies.get(i);
                final Strategy player2 = strategies.get(j);

                final Game game = new Game(player1, player2, N, gameConfiguration);
                final Game.GameResult gameResult = game.play();

                totalScore.computeIfPresent(player1.getClass(), (k, v) -> v + gameResult.player1Score());
                totalScore.computeIfPresent(player2.getClass(), (k, v) -> v + gameResult.player2Score());

                if (gameResult.player1Score() > gameResult.player2Score()) {
                    totalWins.computeIfPresent(player1.getClass(), (k, v) -> v + 1);
                } else if (gameResult.player2Score() > gameResult.player1Score()) {
                    totalWins.computeIfPresent(player2.getClass(), (k, v) -> v + 1);
                }

                printRoundResult(player1, player2, gameResult.player1Score(), gameResult.player2Score());
            }
        }

        printGameResult(totalScore, totalWins);
    }

    private void printGameConfiguration() {
        System.out.println("\n\u001B[36m╔══════════════════════════════════════╗");
        System.out.println("║       Iterated PD Configuration      ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf("║   T (Temptation)      ║       5      ║%n");
        System.out.printf("║   R (Reward)          ║       3      ║%n");
        System.out.printf("║   P (Punishment)      ║       1      ║%n");
        System.out.printf("║   S (Sucker’s Payoff) ║       0      ║%n");
        System.out.println("╚══════════════════════════════════════╝\u001B[0m\n");
    }

    private void printRoundResult(Strategy p1, Strategy p2, long s1, long s2) {
        String name1 = p1.getClass().getSimpleName();
        String name2 = p2.getClass().getSimpleName();

        // Winner/loser/tie colors
        String win = "\u001B[32m";  // green
        String lose = "\u001B[31m";  // red
        String tie = "\u001B[33m";  // yellow
        String reset = "\u001B[0m";

        String color1 =
                (s1 > s2) ? win :
                        (s2 > s1) ? lose : tie;

        String color2 =
                (s2 > s1) ? win :
                        (s1 > s2) ? lose : tie;

        // Alignment BEFORE coloring
        String n1 = String.format("%-18s", name1);
        String n2 = String.format("%-18s", name2);

        double avg1 = (double) s1 / N;
        double avg2 = (double) s2 / N;

        System.out.printf(
                "%s%s%s vs %s%s%s → (%7f , %7f)%n",
                color1, n1, reset,
                color2, n2, reset,
                avg1, avg2
        );
    }


    private void printGameResult(
            HashMap<Class<? extends Strategy>, Long> totalScore,
            HashMap<Class<? extends Strategy>, Long> totalWins
    ) {
        System.out.println();
        System.out.println("\u001B[36m╔═════════════════════════════════════════════════════════════════╗\u001B[0m");
        System.out.println("\u001B[36m║           Iterated Prisoner's Dilemma (Total Results)           ║\u001B[0m");
        System.out.println("\u001B[36m╚═════════════════════════════════════════════════════════════════╝\u001B[0m");

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
