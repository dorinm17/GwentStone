package actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Command;

import constants.Output;

import constants.Values;
import game.Player;

public abstract class GetPlayerTurn {
    public static ObjectNode execute(Player playerOne) {
        ObjectMapper objectMapper = new ObjectMapper();
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
