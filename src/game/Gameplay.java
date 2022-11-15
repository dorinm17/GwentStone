package game;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ArrayNode;

import fileio.ActionsInput;

import fileio.CardInput;

import fileio.GameInput;

import java.util.ArrayList;

import java.util.Random;

import java.util.Collections;

public class Gameplay {
    private static volatile Gameplay instance = null;

    private final ArrayList<ArrayList<CardInput>> playerOneDecks;
    private final ArrayList<ArrayList<CardInput>> playerTwoDecks;
    private final ArrayList<GameInput> gamesList;

    private Gameplay(final ArrayList<ArrayList<CardInput>> playerOneDecks,
                    final ArrayList<ArrayList<CardInput>> playerTwoDecks,
                    final ArrayList<GameInput> gamesList) {
        this.playerOneDecks = playerOneDecks;
        this.playerTwoDecks = playerTwoDecks;
        this.gamesList = gamesList;
    }

    public static Gameplay getInstance(
            final ArrayList<ArrayList<CardInput>> playerOneDecks,
            final ArrayList<ArrayList<CardInput>> playerTwoDecks,
            final ArrayList<GameInput> gamesList) {
        if (instance == null) {
            synchronized (Gameplay.class) {
                if (instance == null) {
                    instance = new Gameplay(playerOneDecks, playerTwoDecks, gamesList);
                }
            }
        }

        return instance;
    }

    public ArrayNode runGames() {
        Random random;
        ArrayList<CardInput> deck1;
        ArrayList<CardInput> deck2;
        int shuffleSeed;
        int startingPlayer;
        Match match;
        CardInput hero1;
        CardInput hero2;

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode output = objectMapper.createArrayNode();

        for (GameInput game : gamesList) {
            shuffleSeed = game.getStartGame().getShuffleSeed();
            deck1 = new ArrayList<>(playerOneDecks.get(
                    game.getStartGame().getPlayerOneDeckIdx()));
            deck2 = new ArrayList<>(playerTwoDecks.get(
                    game.getStartGame().getPlayerTwoDeckIdx()));
            random = new Random(shuffleSeed);
            Collections.shuffle(deck1, random);
            random = new Random(shuffleSeed);
            Collections.shuffle(deck2, random);

            startingPlayer = game.getStartGame().getStartingPlayer();
            hero1 = game.getStartGame().getPlayerOneHero();
            hero2 = game.getStartGame().getPlayerTwoHero();

            match = new Match(startingPlayer, deck1, deck2, hero1, hero2);

            for (ActionsInput action : game.getActions()) {
                output.add(match.execute(action));
            }
        }

        return output;
    }
}
