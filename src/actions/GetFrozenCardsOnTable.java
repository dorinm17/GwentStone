package actions;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ArrayNode;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Output;

import fileio.ActionsInput;

import game.Card;

public abstract class GetFrozenCardsOnTable {
    /**
     */
    public static ObjectNode execute(final ActionsInput action,
                                     final Card[][] board) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode();

        result.put(Output.COMMAND.getOutput(), action.getCommand());

        ArrayNode tableObject = objectMapper.createArrayNode();
        ArrayNode rowObject = objectMapper.createArrayNode();
        for (Card[] row : board) {
            for (Card card : row) {
                if (card != null) {
                    if (card.isFrozen()) {
                        rowObject.add(GetCard.execute(card.getCard()));
                    }
                }
            }
        }
        result.set(Output.OUTPUT.getOutput(), tableObject);

        return result;
    }
}
