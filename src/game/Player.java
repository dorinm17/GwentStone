package game;

import java.util.ArrayList;

import fileio.CardInput;

public class Player {
    private ArrayList<Card> deck;
    private ArrayList<Card> hand;
    private boolean isTurn;
    private Card hero;
    private int totalWins;
    private int mana;

    public Player(ArrayList<CardInput> deck, CardInput hero) {
        this.deck = new ArrayList<>();
        for(CardInput card : deck) {
            this.deck.add(new Card(card));
        }

        this.hand = new ArrayList<>();
        this.isTurn = false;
        this.hero = new Card(hero);
        this.totalWins = 0;
        this.mana = 0;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public boolean getTurn() {
        return isTurn;
    }

    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public Card getHero() {
        return hero;
    }

    public void setHero(Card hero) {
        this.hero = hero;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }
}
