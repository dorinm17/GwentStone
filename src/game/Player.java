package game;

import java.util.ArrayList;

import constants.Values;

import fileio.CardInput;

public final class Player {
    private final ArrayList<Card> deck;
    private final ArrayList<Card> hand;
    private boolean isTurn;
    private Card hero;
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

        this.mana = Values.INITIAL_MANA.getValue();
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public ArrayList<Card> getHand() {
        return hand;
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

    public int getMana() {
        return mana;
    }

    public void setMana(final int mana) {
        this.mana = mana;
    }
}
