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
                                     final Card[][] table) {
        ObjectNode result = null;
        boolean error1 = false;
        boolean error2 = false;
        boolean error3 = true;

        if (playerOne.getTurn()) {
            Card cardToBePlaced = playerOne.getHand().get(action.getHandIdx());
            CardInput cardToBePlacedInput = cardToBePlaced.getCard();

            for (Environment e : Environment.values()) {
                if (e.getName().equals(cardToBePlacedInput.getName())) {
                    error1 = true;
                    break;
                }
            }

            if (cardToBePlacedInput.getMana() > playerOne.getMana()) {
                error2 = true;
            }

            if (!error1 && !error2) {
                int playerOneFrontRow = Values.PLAYER_ONE_FRONT_ROW.getValue();
                for (MinionFrontRow m : MinionFrontRow.values()) {
                    if (m.getName().equals(cardToBePlacedInput.getName())) {
                        for (int i = 0; i < Values.COLUMNS.getValue(); i++) {
                            if (table[playerOneFrontRow][i] == null) {
                                error3 = false;
                                table[playerOneFrontRow][i] = cardToBePlaced;
                                break;
                            }
                        }
                    }
                }

                int playerOneBackRow = Values.PLAYER_ONE_BACK_ROW.getValue();
                for (MinionBackRow m : MinionBackRow.values()) {
                    if (m.getName().equals(cardToBePlacedInput.getName())) {
                        for (int i = 0; i < Values.COLUMNS.getValue(); i++) {
                            if (table[playerOneBackRow][i] == null) {
                                error3 = false;
                                table[playerOneBackRow][i] = cardToBePlaced;
                                break;
                            }
                        }
                    }
                }

                if (!error3) {
                    playerOne.setMana(playerOne.getMana()
                            - cardToBePlacedInput.getMana());
                    playerOne.getHand().remove(action.getHandIdx());
                }
            }
        } else {
            Card cardToBePlaced = playerTwo.getHand().get(action.getHandIdx());
            CardInput cardToBePlacedInput = cardToBePlaced.getCard();

            for (Environment e : Environment.values()) {
                if (e.getName().equals(cardToBePlacedInput.getName())) {
                    error1 = true;
                    break;
                }
            }

            if (cardToBePlacedInput.getMana() > playerTwo.getMana()) {
                error2 = true;
            }

            if (!error1 && !error2) {
                int playerTwoFrontRow = Values.PLAYER_TWO_FRONT_ROW.getValue();
                for (MinionFrontRow m : MinionFrontRow.values()) {
                    if (m.getName().equals(cardToBePlacedInput.getName())) {
                        for (int i = 0; i < Values.COLUMNS.getValue(); i++) {
                            if (table[playerTwoFrontRow][i] == null) {
                                error3 = false;
                                table[playerTwoFrontRow][i] = cardToBePlaced;
                                break;
                            }
                        }
                    }
                }

                int playerTwoBackRow = Values.PLAYER_TWO_BACK_ROW.getValue();
                for (MinionBackRow m : MinionBackRow.values()) {
                    if (m.getName().equals(cardToBePlacedInput.getName())) {
                        for (int i = 0; i < Values.COLUMNS.getValue(); i++) {
                            if (table[playerTwoBackRow][i] == null) {
                                error3 = false;
                                table[playerTwoBackRow][i] = cardToBePlaced;
                                break;
                            }
                        }
                    }
                }

                if (!error3) {
                    playerTwo.setMana(playerTwo.getMana()
                            - cardToBePlacedInput.getMana());
                    playerTwo.getHand().remove(action.getHandIdx());
                }
            }
        }

        if (error1 || error2 || error3) {
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.createObjectNode();
            result.put(Output.COMMAND.getOutput(), action.getCommand());
            result.put(Output.HAND_IDX.getOutput(), action.getHandIdx());

            if (error1) {
                result.put(Output.ERROR.getOutput(),
                        Error.ENVIRONMENT_NOT_ALLOWED.getMessage());
            } else if (error2) {
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
