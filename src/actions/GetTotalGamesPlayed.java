package actions;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Command;

import constants.Output;

import game.Gameplay;

public abstract class GetTotalGamesPlayed {
    /**
     */
    public static ObjectNode execute() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode();

        result.put(Output.COMMAND.getOutput(),
                Command.GET_TOTAL_GAMES_PLAYED.getCommand());
        result.put(Output.OUTPUT.getOutput(),
                Gameplay.getPlayerOneWins() + Gameplay.getPlayerTwoWins());

        return result;
    }
}
