package game;

import fileio.CardInput;

public final class Card {
    private CardInput card;
    private boolean attacked;
    private boolean usedAbility;
    private boolean frozen;
    private int x;
    private int y;

    public Card(final CardInput card) {
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

    public void setCard(final CardInput card) {
        this.card = card;
    }

    public boolean getAttacked() {
        return attacked;
    }

    public void setAttacked(final boolean attacked) {
        this.attacked = attacked;
    }

    public boolean getUsedAbility() {
        return usedAbility;
    }

    public void setUsedAbility(final boolean usedAbility) {
        this.usedAbility = usedAbility;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(final boolean frozen) {
        this.frozen = frozen;
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }
}
