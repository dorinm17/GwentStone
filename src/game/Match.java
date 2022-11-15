package game;

import actions.EndPlayerTurn;

import actions.GetCardsInHand;

import actions.GetCardsOnTable;

import actions.GetPlayerDeck;

import actions.GetPlayerHero;

import actions.GetPlayerMana;

import actions.GetPlayerTurn;

import actions.PlaceCard;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Values;

import constants.Command;

import fileio.ActionsInput;

import fileio.CardInput;

import java.util.ArrayList;

public final class Match {
    private Player playerOne;
    private Player playerTwo;
    private Card[][] table = new Card[Values.ROWS.getValue()]
            [Values.COLUMNS.getValue()];
    private int turn;
    private int mana;

    public Match(final int startingPlayer, final ArrayList<CardInput> deck1,
                 final ArrayList<CardInput> deck2, final CardInput hero1,
                 final CardInput hero2) {
        playerOne = new Player(deck1, hero1);
        playerTwo = new Player(deck2, hero2);

        if (startingPlayer == Values.PLAYER_ONE.getValue()) {
            playerOne.setTurn(true);
        } else {
            playerTwo.setTurn(true);
        }

        turn = 1;
        mana = Values.INITIAL_MANA.getValue();

        playerOne.getHand().add(playerOne.getDeck().get(0));
        playerOne.getDeck().remove(0);
        playerTwo.getHand().add(playerTwo.getDeck().get(0));
        playerTwo.getDeck().remove(0);

        for (int i = 0; i < Values.ROWS.getValue(); i++) {
            for (int j = 0; j < Values.COLUMNS.getValue(); j++) {
                table[i][j] = null;
            }
        }
    }

    /**
     */
    public ObjectNode execute(final ActionsInput action) {
        ObjectNode result = null;

        for (Command c : Command.values()) {
            if (c.getCommand().equals(action.getCommand())) {
                switch (c) {
                    case END_PLAYER_TURN:
                        EndPlayerTurn.execute(this);
                        break;
                    case PLACE_CARD:
                        result = PlaceCard.execute(action, playerOne,
                                playerTwo, table);
                        break;
                    case CARD_USES_ATTACK:
                        // cardUsesAttack();
                        break;
                    case CARD_USES_ABILITY:
                        // cardUsesAbility();
                        break;
                    case USE_ATTACK_HERO:
                        // useAttackHero();
                        break;
                    case USE_HERO_ABILITY:
                        // useHeroAbility();
                        break;
                    case USE_ENVIRONMENT_CARD:
                        // useEnvironmentCard();
                        break;
                    case GET_CARDS_IN_HAND:
                        result = GetCardsInHand.execute(action,
                                playerOne, playerTwo);
                        break;
                    case GET_PLAYER_DECK:
                        result = GetPlayerDeck.execute(action,
                                playerOne, playerTwo);
                        break;
                    case GET_CARDS_ON_TABLE:
                        result = GetCardsOnTable.execute(action, table);
                        break;
                    case GET_PLAYER_TURN:
                        result = GetPlayerTurn.execute(playerOne);
                        break;
                    case GET_CARD_AT_POSITION:
                        // getCardAtPosition();
                        break;
                    case GET_PLAYER_HERO:
                        result = GetPlayerHero.execute(action,
                                playerOne, playerTwo);
                        break;
                    case GET_PLAYER_MANA:
                        result = GetPlayerMana.execute(action,
                                playerOne, playerTwo);
                        break;
                    default:
                        break;
                }
            }
        }

        return result;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(final Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(final Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public Card[][] getTable() {
        return table;
    }

    public void setTable(final Card[][] table) {
        this.table = table;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(final int turn) {
        this.turn = turn;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(final int mana) {
        this.mana = mana;
    }
}
