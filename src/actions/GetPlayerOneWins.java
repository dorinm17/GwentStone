package actions;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Command;

import constants.Output;

import game.Gameplay;

public abstract class GetPlayerOneWins {
    /**
     */
    public static ObjectNode execute() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode();

        result.put(Output.COMMAND.getOutput(),
                Command.GET_PLAYER_ONE_WINS.getCommand());
        result.put(Output.OUTPUT.getOutput(), Gameplay.getPlayerOneWins());

        return result;
    }
}
