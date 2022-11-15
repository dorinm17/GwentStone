package actions;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ArrayNode;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Output;

import constants.Values;

import fileio.ActionsInput;

import game.Card;

import game.Player;

public abstract class GetPlayerDeck {
    public static ObjectNode execute(ActionsInput action,
                               Player playerOne, Player playerTwo) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode();
        result.put(Output.COMMAND.getOutput(), action.getCommand());
        result.put(Output.PLAYER_IDX.getOutput(), action.getPlayerIdx());

        ArrayNode deck = objectMapper.createArrayNode();
        if (action.getPlayerIdx() == Values.PLAYER_ONE.getValue()) {
            for (Card card : playerOne.getDeck()) {
                deck.add(GetCard.execute(card.getCard()));
            }
            result.set(Output.OUTPUT.getOutput(), deck);
        } else {
            for (Card card : playerTwo.getDeck()) {
                deck.add(GetCard.execute(card.getCard()));
            }
            result.set(Output.OUTPUT.getOutput(), deck);
        }

        return result;
    }
}
