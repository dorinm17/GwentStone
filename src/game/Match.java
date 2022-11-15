package game;

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
                        result = getPlayerDeck(action);
                        break;
                    case GET_CARDS_ON_TABLE:
                        // getCardsOnTable();
                        break;
                    case GET_PLAYER_TURN:
                        result = getPlayerTurn();
                        break;
                    case GET_CARD_AT_POSITION:
                        // getCardAtPosition();
                        break;
                    case GET_PLAYER_HERO:
                        result = getPlayerHero(action);
                        break;
                }
            }
        }

        return result;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    private ObjectNode getCard(CardInput card) {
        ObjectNode cardObject = objectMapper.createObjectNode();
        cardObject.put(CardOutput.MANA.getOutput(), card.getMana());

        boolean environment = false;
        for (Environment e : Environment.values()) {
            if (e.getName().equals(card.getName())) {
                environment = true;
                break;
            }
        }

        if (!environment) {
            cardObject.put(CardOutput.ATTACK_DAMAGE.getOutput(),
                    card.getAttackDamage());
            cardObject.put(CardOutput.HEALTH.getOutput(), card.getHealth());
        }

        cardObject.put(CardOutput.DESCRIPTION.getOutput(),
                card.getDescription());

        ArrayNode colors = objectMapper.createArrayNode();
        for (String color : card.getColors()) {
            colors.add(color);
        }
        cardObject.set(CardOutput.COLORS.getOutput(), colors);

        cardObject.put(CardOutput.NAME.getOutput(), card.getName());

        return cardObject;
    }

    private ObjectNode getHero(CardInput hero) {
        ObjectNode heroObject = objectMapper.createObjectNode();
        heroObject.put(CardOutput.MANA.getOutput(), hero.getMana());
        heroObject.put(CardOutput.DESCRIPTION.getOutput(),
                hero.getDescription());

        ArrayNode colors = objectMapper.createArrayNode();
        for (String color : hero.getColors()) {
            colors.add(color);
        }
        heroObject.set(CardOutput.COLORS.getOutput(), colors);

        heroObject.put(CardOutput.NAME.getOutput(), hero.getName());
        heroObject.put(CardOutput.HEALTH.getOutput(), hero.getHealth());

        return heroObject;
    }

    private ObjectNode getPlayerDeck(ActionsInput action) {
        ObjectNode result = objectMapper.createObjectNode();
        result.put(Output.COMMAND.getOutput(), action.getCommand());
        result.put(Output.PLAYER_IDX.getOutput(), action.getPlayerIdx());

        ArrayNode deck = objectMapper.createArrayNode();
        if (action.getPlayerIdx() == Values.PLAYER_ONE.getValue()) {
            for (Card card : playerOne.getDeck()) {
                deck.add(getCard(card.getCard()));
            }
            result.set(Output.OUTPUT.getOutput(), deck);
        } else {
            for (Card card : playerTwo.getDeck()) {
                deck.add(getCard(card.getCard()));
            }
            result.set(Output.OUTPUT.getOutput(), deck);
        }

        return result;
    }

    private ObjectNode getPlayerHero(ActionsInput action) {
        ObjectNode result = objectMapper.createObjectNode();
        result.put(Output.COMMAND.getOutput(), action.getCommand());
        result.put(Output.PLAYER_IDX.getOutput(), action.getPlayerIdx());

        if (action.getPlayerIdx() == Values.PLAYER_ONE.getValue()) {
            result.set(Output.OUTPUT.getOutput(),
                    getHero(playerOne.getHero().getCard()));
        } else {
            result.set(Output.OUTPUT.getOutput(),
                    getHero(playerTwo.getHero().getCard()));
        }

        return result;
    }

    private ObjectNode getPlayerTurn() {
        ObjectNode result = objectMapper.createObjectNode();
        result.put(Output.COMMAND.getOutput(), Command.GET_PLAYER_TURN.getCommand());

        if (playerOne.getTurn()) {
            result.put(Output.OUTPUT.getOutput(), Values.PLAYER_ONE.getValue());
        } else {
            result.put(Output.OUTPUT.getOutput(), Values.PLAYER_TWO.getValue());
        }

        return result;
    }
}
