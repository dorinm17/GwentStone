package actions;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Environment;

import constants.Error;

import constants.MinionFrontRow;

import constants.MinionBackRow;

import constants.Values;

import constants.Output;

import fileio.ActionsInput;

import fileio.CardInput;

import game.Card;

import game.Player;

public abstract class PlaceCard {
    /**
     */
    public static ObjectNode execute(final ActionsInput action,
                                     final Player playerOne,
                                     final Player playerTwo,
                                     final Card[][] board) {
        ObjectNode result = null;
        boolean errorEnvironment = false;
        boolean errorMana = false;
        boolean errorFullRow = true;
        Player currentPlayer;
        int currentFrontRow;
        int currentBackRow;

        if (playerOne.getTurn()) {
            currentPlayer = playerOne;
            currentFrontRow = Values.PLAYER_ONE_FRONT_ROW.getValue();
            currentBackRow = Values.PLAYER_ONE_BACK_ROW.getValue();
        } else {
            currentPlayer = playerTwo;
            currentFrontRow = Values.PLAYER_TWO_FRONT_ROW.getValue();
            currentBackRow = Values.PLAYER_TWO_BACK_ROW.getValue();
        }

        Card cardToBePlaced = currentPlayer.getHand().get(action.getHandIdx());
        CardInput cardToBePlacedInput = cardToBePlaced.getCard();

        for (Environment e : Environment.values()) {
            if (e.getName().equals(cardToBePlacedInput.getName())) {
                errorEnvironment = true;
                break;
            }
        }

        if (cardToBePlacedInput.getMana() > currentPlayer.getMana()) {
            errorMana = true;
        }

        if (!errorEnvironment && !errorMana) {
            for (MinionFrontRow m : MinionFrontRow.values()) {
                if (m.getName().equals(cardToBePlacedInput.getName())) {
                    for (int i = 0; i < Values.COLUMNS.getValue(); i++) {
                        if (board[currentFrontRow][i] == null) {
                            errorFullRow = false;
                            board[currentFrontRow][i] = cardToBePlaced;
                            break;
                        }
                    }
                }
            }

            for (MinionBackRow m : MinionBackRow.values()) {
                if (m.getName().equals(cardToBePlacedInput.getName())) {
                    for (int i = 0; i < Values.COLUMNS.getValue(); i++) {
                        if (board[currentBackRow][i] == null) {
                            errorFullRow = false;
                            board[currentBackRow][i] = cardToBePlaced;
                            break;
                        }
                    }
                }
            }

            if (!errorFullRow) {
                currentPlayer.setMana(currentPlayer.getMana()
                        - cardToBePlacedInput.getMana());
                currentPlayer.getHand().remove(action.getHandIdx());
            }
        }

        if (errorEnvironment || errorMana || errorFullRow) {
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.createObjectNode();
            result.put(Output.COMMAND.getOutput(), action.getCommand());
            result.put(Output.HAND_IDX.getOutput(), action.getHandIdx());

            if (errorEnvironment) {
                result.put(Output.ERROR.getOutput(),
                        Error.ENVIRONMENT_NOT_ALLOWED.getMessage());
            } else if (errorMana) {
                result.put(Output.ERROR.getOutput(),
                        Error.NOT_ENOUGH_MANA_MINION.getMessage());
            } else {
                result.put(Output.ERROR.getOutput(),
                        Error.NO_PLACING_FULL_ROW.getMessage());
            }
        }

        return result;
    }
}
