package actions;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ArrayNode;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Environment;

import constants.Output;

import constants.Values;

import fileio.ActionsInput;

import game.Card;

import game.Player;

public abstract class GetEnvironmentCardsInHand {
    /**
     */
    public static ObjectNode execute(final ActionsInput action,
                                     final Player playerOne,
                                     final Player playerTwo) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode();

        result.put(Output.COMMAND.getOutput(), action.getCommand());
        result.put(Output.PLAYER_IDX.getOutput(), action.getPlayerIdx());

        ArrayNode handEnvironment = objectMapper.createArrayNode();
        if (action.getPlayerIdx() == Values.PLAYER_ONE.getValue()) {
            for (Card card : playerOne.getHand()) {
                for (Environment e : Environment.values()) {
                    if (card.getCard().getName().equals(e.getName())) {
                        handEnvironment.add(GetCard.execute(card.getCard()));
                    }
                }
            }
        } else {
            for (Card card : playerTwo.getHand()) {
                for (Environment e : Environment.values()) {
                    if (card.getCard().getName().equals(e.getName())) {
                        handEnvironment.add(GetCard.execute(card.getCard()));
                    }
                }
            }
        }
        result.set(Output.OUTPUT.getOutput(), handEnvironment);

        return result;
    }
}
