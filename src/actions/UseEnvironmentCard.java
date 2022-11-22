package actions;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Environment;

import constants.Output;

import constants.Values;

import constants.Error;

import fileio.ActionsInput;

import fileio.CardInput;

import game.Card;

import game.Player;

public abstract class UseEnvironmentCard {
    /**
     */
    public static ObjectNode execute(final ActionsInput action,
                                     final Player playerOne,
                                     final Player playerTwo,
                                     final Card[][] board) {
        ObjectNode result = null;
        boolean errorEnvironment = true;
        boolean errorMana = false;
        boolean errorWrongRow = false;
        boolean errorHeartHound = false;
        Card cardToBePlaced;
        CardInput cardToBePlacedInput;
        int column = 0;
        Card cardWithMaxHealth = null;
        int affectedRow = action.getAffectedRow();
        Player currentPlayer;
        int enemyFrontRow;
        int enemyBackRow;
        int mirrorRow = 0;

        if (playerOne.getTurn()) {
            currentPlayer = playerOne;
            enemyFrontRow = Values.PLAYER_TWO_FRONT_ROW.getValue();
            enemyBackRow = Values.PLAYER_TWO_BACK_ROW.getValue();

            if (affectedRow == enemyFrontRow) {
                mirrorRow = Values.PLAYER_ONE_BACK_ROW.getValue();
            } else if (affectedRow == enemyBackRow) {
                mirrorRow = Values.PLAYER_ONE_FRONT_ROW.getValue();
            }
        } else {
            currentPlayer = playerTwo;
            enemyFrontRow = Values.PLAYER_ONE_FRONT_ROW.getValue();
            enemyBackRow = Values.PLAYER_ONE_BACK_ROW.getValue();

            if (affectedRow == enemyFrontRow) {
                mirrorRow = Values.PLAYER_TWO_BACK_ROW.getValue();
            } else if (affectedRow == enemyBackRow) {
                mirrorRow = Values.PLAYER_TWO_FRONT_ROW.getValue();
            }
        }

        if (affectedRow != enemyFrontRow && affectedRow != enemyBackRow) {
            errorWrongRow = true;
        }

        cardToBePlaced = currentPlayer.getHand().get(action.getHandIdx());
        cardToBePlacedInput = cardToBePlaced.getCard();

        if (cardToBePlacedInput.getMana() > currentPlayer.getMana()) {
            errorMana = true;
        }

        if (cardToBePlacedInput.getName().equals(
                Environment.HEART_HOUND.getName())) {
            int maxHealth = 0;
            errorHeartHound = true;

            for (int i = 0; i < Values.COLUMNS.getValue(); i++) {
                Card card = board[affectedRow][i];
                if (card != null) {
                    if (maxHealth > card.getCard().getHealth()) {
                        maxHealth = card.getCard().getHealth();
                        cardWithMaxHealth = card;
                        column = i;
                    }
                }
            }
        }

        for (Environment e : Environment.values()) {
            if (e.getName().equals(cardToBePlacedInput.getName())) {
                errorEnvironment = false;
                break;
            }
        }

        if (!errorEnvironment && !errorMana && !errorWrongRow) {
            if (cardToBePlacedInput.getName().equals(
                    Environment.FIRESTORM.getName())) {
                for (int i = 0; i < Values.COLUMNS.getValue(); i++) {
                    Card card = board[affectedRow][i];
                    if (card != null) {
                        CardInput cardInput = card.getCard();
                        cardInput.setHealth(cardInput.getHealth()
                                - Values.FIRESTORM.getValue());

                        if (cardInput.getHealth() == 0) {
                            board[affectedRow][i] = null;
                        }
                    }
                }
            } else if (cardToBePlacedInput.getName().equals(
                    Environment.WINTERFELL.getName())) {
                for (int i = 0; i < Values.COLUMNS.getValue(); i++) {
                    Card card = board[affectedRow][i];
                    if (card != null) {
                        card.setFrozen(true);
                    }
                }
            } else {
                for (int i = 0; i < Values.COLUMNS.getValue(); i++) {
                    Card card = board[mirrorRow][i];
                    if (card != null && cardWithMaxHealth != null) {
                        board[mirrorRow][i] = cardWithMaxHealth;
                        errorHeartHound = false;
                        board[affectedRow][column] = null;
                        break;
                    }
                }
            }

            if (!errorHeartHound) {
                if (playerOne.getTurn()) {
                    playerOne.setMana(playerOne.getMana()
                            - cardToBePlacedInput.getMana());
                    playerOne.getHand().remove(action.getHandIdx());

                } else {
                    playerTwo.setMana(playerTwo.getMana()
                            - cardToBePlacedInput.getMana());
                    playerTwo.getHand().remove(action.getHandIdx());
                }
            }
        }

        if (errorEnvironment || errorMana || errorWrongRow
                || errorHeartHound) {
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.createObjectNode();
            result.put(Output.COMMAND.getOutput(), action.getCommand());
            result.put(Output.HAND_IDX.getOutput(), action.getHandIdx());
            result.put(Output.AFFECTED_ROW.getOutput(),
                    affectedRow);

            if (errorEnvironment) {
                result.put(Output.ERROR.getOutput(),
                        Error.NOT_ENVIRONMENT.getMessage());
            } else if (errorMana) {
                result.put(Output.ERROR.getOutput(),
                        Error.NOT_ENOUGH_MANA_ENVIRONMENT.getMessage());
            } else if (errorWrongRow) {
                result.put(Output.ERROR.getOutput(),
                        Error.NOT_ENEMY_ROW_ENVIRONMENT.getMessage());
            } else {
                result.put(Output.ERROR.getOutput(),
                        Error.NO_STEALING_FULL_ROW.getMessage());
            }
        }

        return result;
    }
}
