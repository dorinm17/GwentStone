package actions;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Output;

import constants.Values;
import fileio.ActionsInput;

import game.Card;
import game.Player;

public abstract class GetCardsInHand {
    /**
     */
    public static ObjectNode execute(final ActionsInput action,
                                     final Player playerOne,
                                     final Player playerTwo) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode();

        result.put(Output.COMMAND.getOutput(), action.getCommand());
        result.put(Output.PLAYER_IDX.getOutput(), action.getPlayerIdx());

        ArrayNode hand = objectMapper.createArrayNode();
        if (action.getPlayerIdx() == Values.PLAYER_ONE.getValue()) {
            for (Card card : playerOne.getHand()) {
                hand.add(GetCard.execute(card.getCard()));
            }
            result.set(Output.OUTPUT.getOutput(), hand);
        } else {
            for (Card card : playerTwo.getHand()) {
                hand.add(GetCard.execute(card.getCard()));
            }
            result.set(Output.OUTPUT.getOutput(), hand);
        }

        return result;
    }
}
