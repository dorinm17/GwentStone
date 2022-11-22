package actions;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Output;

import constants.Error;

import fileio.ActionsInput;

import game.Card;

public abstract class GetCardAtPosition {
    /**
     */
    public static ObjectNode execute(final ActionsInput action,
                                     final Card[][] board) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode();

        result.put(Output.COMMAND.getOutput(), action.getCommand());
        result.put(Output.X.getOutput(), action.getX());
        result.put(Output.Y.getOutput(), action.getY());

        int x = action.getX();
        int y = action.getY();

        if (board[x][y] != null) {
            ObjectNode cardObject = GetCard.execute(board[x][y].getCard());
            result.set(Output.OUTPUT.getOutput(), cardObject);
        } else {
            result.put(Output.OUTPUT.getOutput(), Error.NO_CARD.getMessage());
        }

        return result;
    }
}
