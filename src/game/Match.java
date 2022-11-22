package game;

import actions.EndPlayerTurn;

import actions.GetCardsInHand;

import actions.GetCardsOnTable;

import actions.GetPlayerDeck;

import actions.GetPlayerHero;

import actions.GetPlayerMana;

import actions.GetPlayerTurn;

import actions.PlaceCard;

import actions.UseEnvironmentCard;

import actions.GetEnvironmentCardsInHand;

import actions.GetCardAtPosition;

import actions.GetFrozenCardsOnTable;

import actions.CardUsesAttack;

import actions.CardUsesAbility;

import actions.UseAttackHero;

import actions.UseHeroAbility;

import actions.GetTotalGamesPlayed;

import actions.GetPlayerOneWins;

import actions.GetPlayerTwoWins;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Values;

import constants.Command;

import fileio.ActionsInput;

import fileio.CardInput;

import java.util.ArrayList;

public final class Match {
    private final Player playerOne;
    private final Player playerTwo;
    private final Card[][] board = new Card[Values.ROWS.getValue()]
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
                board[i][j] = null;
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
                                playerTwo, board);
                        break;
                    case CARD_USES_ATTACK:
                        result = CardUsesAttack.execute(action, board);
                        break;
                    case CARD_USES_ABILITY:
                        result = CardUsesAbility.execute(action, board);
                        break;
                    case USE_ATTACK_HERO:
                        result = UseAttackHero.execute(action, playerOne,
                                playerTwo, board);
                        break;
                    case USE_HERO_ABILITY:
                        result = UseHeroAbility.execute(action, playerOne,
                                playerTwo, board);
                        break;
                    case USE_ENVIRONMENT_CARD:
                        result = UseEnvironmentCard.execute(action, playerOne,
                                playerTwo, board);
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
                        result = GetCardsOnTable.execute(action, board);
                        break;
                    case GET_PLAYER_TURN:
                        result = GetPlayerTurn.execute(playerOne);
                        break;
                    case GET_CARD_AT_POSITION:
                        result = GetCardAtPosition.execute(action, board);
                        break;
                    case GET_PLAYER_HERO:
                        result = GetPlayerHero.execute(action,
                                playerOne, playerTwo);
                        break;
                    case GET_PLAYER_MANA:
                        result = GetPlayerMana.execute(action,
                                playerOne, playerTwo);
                        break;
                    case GET_ENVIRONMENT_CARDS_IN_HAND:
                        result = GetEnvironmentCardsInHand.execute(action,
                                playerOne, playerTwo);
                        break;
                    case GET_FROZEN_CARDS_ON_TABLE:
                        result = GetFrozenCardsOnTable.execute(action, board);
                        break;
                    case GET_TOTAL_GAMES_PLAYED:
                        result = GetTotalGamesPlayed.execute();
                        break;
                    case GET_PLAYER_ONE_WINS:
                        result = GetPlayerOneWins.execute();
                        break;
                    case GET_PLAYER_TWO_WINS:
                        result = GetPlayerTwoWins.execute();
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

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public Card[][] getBoard() {
        return board;
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
