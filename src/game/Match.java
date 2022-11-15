package game;

import actions.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ArrayNode;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.*;

import fileio.ActionsInput;

import fileio.CardInput;

import java.util.ArrayList;

public class Match {
    private Player playerOne;
    private Player playerTwo;
    private Card[][] table = new Card[4][5];
    private int round;
    private int mana;

    public Match(int startingPlayer, ArrayList<CardInput> deck1,
                 ArrayList<CardInput> deck2, CardInput hero1,
                 CardInput hero2) {
        playerOne = new Player(deck1, hero1);
        playerTwo = new Player(deck2, hero2);

        if (startingPlayer == Values.PLAYER_ONE.getValue()) {
            playerOne.setTurn(true);
        } else {
            playerTwo.setTurn(true);
        }

        round = 1;
        mana = 1;

        playerOne.getHand().add(playerOne.getDeck().get(0));
        playerOne.getDeck().remove(0);
        playerTwo.getHand().add(playerTwo.getDeck().get(0));
        playerTwo.getDeck().remove(0);
    }

    public ObjectNode execute(ActionsInput action) {
        ObjectNode result = null;

        for (Command c : Command.values()) {
            if (c.getCommand().equals(action.getCommand())) {
                switch (c) {
                    case END_PLAYER_TURN:
                        // result = endPlayerTurn();
                        break;
                    case PLACE_CARD:
                        // placeCard();
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
                        // getCardsInHand();
                        break;
                    case GET_PLAYER_DECK:
                        result = GetPlayerDeck.execute(action,
                                playerOne, playerTwo);
                        break;
                    case GET_CARDS_ON_TABLE:
                        // getCardsOnTable();
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
                }
            }
        }

        return result;
    }
}
