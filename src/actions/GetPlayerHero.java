package actions;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Output;

import constants.Values;

import fileio.ActionsInput;

import game.Player;

public abstract class GetPlayerHero {
    /**
     */
    public static ObjectNode execute(final ActionsInput action,
                                     final Player playerOne,
                                     final Player playerTwo) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode();

        result.put(Output.COMMAND.getOutput(), action.getCommand());
        result.put(Output.PLAYER_IDX.getOutput(), action.getPlayerIdx());

        if (action.getPlayerIdx() == Values.PLAYER_ONE.getValue()) {
            result.set(Output.OUTPUT.getOutput(),
                    GetHero.execute(playerOne.getHero().getCard()));
        } else {
            result.set(Output.OUTPUT.getOutput(),
                    GetHero.execute(playerTwo.getHero().getCard()));
        }

        return result;
    }
}
