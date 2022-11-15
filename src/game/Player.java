package game;

import java.util.ArrayList;

import constants.Values;

import fileio.CardInput;

public final class Player {
    private ArrayList<Card> deck;
    private ArrayList<Card> hand;
    private boolean isTurn;
    private Card hero;
    private static int playerOneWins;
    private static int playerTwoWins;
    private int mana;

    public Player(final ArrayList<CardInput> deck,
                  final CardInput hero) {
        this.deck = new ArrayList<>();
        for (CardInput card : deck) {
            this.deck.add(new Card(card));
        }

        this.hand = new ArrayList<>();

        this.isTurn = false;

        this.hero = new Card(hero);
        this.hero.getCard().setHealth(Values.HERO_HEALTH.getValue());

        Player.playerOneWins = 0;
        Player.playerTwoWins = 0;

        this.mana = Values.INITIAL_MANA.getValue();
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(final ArrayList<Card> deck) {
        this.deck = deck;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(final ArrayList<Card> hand) {
        this.hand = hand;
    }

    public boolean getTurn() {
        return isTurn;
    }

    public void setTurn(final boolean turn) {
        isTurn = turn;
    }

    public Card getHero() {
        return hero;
    }

    public void setHero(final Card hero) {
        this.hero = hero;
    }

    public static int getPlayerOneWins() {
        return playerOneWins;
    }

    public static void setPlayerOneWins(final int playerOneWins) {
        Player.playerOneWins = playerOneWins;
    }

    public static int getPlayerTwoWins() {
        return playerTwoWins;
    }

    public static void setPlayerTwoWins(final int playerTwoWins) {
        Player.playerTwoWins = playerTwoWins;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(final int mana) {
        this.mana = mana;
    }
}
