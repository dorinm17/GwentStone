package game;

import fileio.CardInput;

public class Card {
    private CardInput card;
    private boolean attacked;
    private boolean usedAbility;
    private boolean frozen;
    private int x;
    private int y;

    public Card(CardInput card) {
        this.card = new CardInput();
        this.card.setAttackDamage(card.getAttackDamage());
        this.card.setHealth(card.getHealth());
        this.card.setMana(card.getMana());
        this.card.setName(card.getName());
        this.card.setColors(card.getColors());
        this.card.setDescription(card.getDescription());

        this.attacked = false;
        this.usedAbility = false;
        this.frozen = false;
        this.x = -1;
        this.y = -1;
    }

    public CardInput getCard() {
        return card;
    }

    public void setCard(CardInput card) {
        this.card = card;
    }

    public boolean hasAttacked() {
        return attacked;
    }

    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }

    public boolean hasUsedAbility() {
        return usedAbility;
    }

    public void setUsedAbility(boolean usedAbility) {
        this.usedAbility = usedAbility;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}
