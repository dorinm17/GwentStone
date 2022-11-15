package actions;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ArrayNode;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Output;

import fileio.ActionsInput;

import game.Card;

public abstract class GetCardsOnTable {
    /**
     */
    public static ObjectNode execute(final ActionsInput action,
                                     final Card[][] table) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode();

        result.put(Output.COMMAND.getOutput(), action.getCommand());

        ArrayNode tableObject = objectMapper.createArrayNode();
        for (Card[] row : table) {
            ArrayNode rowObject = objectMapper.createArrayNode();

            for (Card card : row) {
                if (card != null) {
                    rowObject.add(GetCard.execute(card.getCard()));
                }
            }

            tableObject.add(rowObject);
        }
        result.set(Output.OUTPUT.getOutput(), tableObject);

        return result;
    }
}
