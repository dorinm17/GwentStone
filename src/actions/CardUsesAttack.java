package actions;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.MinionFrontRow;

import constants.Output;

import constants.Values;

import constants.Error;

import fileio.ActionsInput;

import game.Card;

public abstract class CardUsesAttack {
    /**
     */
    public static ObjectNode execute(final ActionsInput action,
                                     final Card[][] board) {
        ObjectNode result = null;
        boolean errorNotEnemy = false;
        boolean errorAttacked = false;
        boolean errorFrozen = false;
        boolean errorNotTank = false;
        boolean tankExists = false;
        int xAttacker = action.getCardAttacker().getX();
        int yAttacker = action.getCardAttacker().getY();
        int xAttacked = action.getCardAttacked().getX();
        int yAttacked = action.getCardAttacked().getY();
        int enemyFrontRow;

        if (xAttacker == Values.PLAYER_ONE_FRONT_ROW.getValue()
                || xAttacker == Values.PLAYER_ONE_BACK_ROW.getValue()) {
            if (xAttacked == Values.PLAYER_ONE_FRONT_ROW.getValue()
                    || xAttacked == Values.PLAYER_ONE_BACK_ROW.getValue()) {
                errorNotEnemy = true;
            }

            enemyFrontRow = Values.PLAYER_TWO_FRONT_ROW.getValue();
        } else {
            if (xAttacked == Values.PLAYER_TWO_FRONT_ROW.getValue()
                    || xAttacked == Values.PLAYER_TWO_BACK_ROW.getValue()) {
                errorNotEnemy = true;
            }

            enemyFrontRow = Values.PLAYER_ONE_FRONT_ROW.getValue();
        }

        if (board[xAttacker][yAttacker].getAttacked()) {
            errorAttacked = true;
        }

        if (board[xAttacker][yAttacker].isFrozen()) {
            errorFrozen = true;
        }

        for (int i = 0; i < Values.COLUMNS.getValue(); i++) {
            Card card = board[enemyFrontRow][i];
            if (card != null) {
                if (card.getCard().getName().equals(
                        MinionFrontRow.GOLIATH.getName())
                        || board[enemyFrontRow][i].getCard().getName().equals(
                        MinionFrontRow.WARDEN.getName())) {
                    tankExists = true;
                    break;
                }
            }
        }

        if (tankExists) {
            if (!board[xAttacked][yAttacked].getCard().getName().equals(
                    MinionFrontRow.GOLIATH.getName())
                    && !board[xAttacked][yAttacked].getCard().getName().equals(
                    MinionFrontRow.WARDEN.getName())) {
                errorNotTank = true;
            }
        }

        if (!errorNotEnemy && !errorAttacked && !errorFrozen
                && !errorNotTank) {
            board[xAttacker][yAttacker].setAttacked(true);
            board[xAttacked][yAttacked].getCard().setHealth(
                    board[xAttacked][yAttacked].getCard().getHealth()
                    - board[xAttacker][yAttacker].getCard().getAttackDamage());

            if (board[xAttacked][yAttacked].getCard().getHealth() <= 0) {
                board[xAttacked][yAttacked] = null;
            }
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.createObjectNode();
            result.put(Output.COMMAND.getOutput(), action.getCommand());

            ObjectNode coordinatesAttacker = objectMapper.createObjectNode();
            coordinatesAttacker.put(Output.X.getOutput(), xAttacker);
            coordinatesAttacker.put(Output.Y.getOutput(), yAttacker);

            ObjectNode coordinatesAttacked = objectMapper.createObjectNode();
            coordinatesAttacked.put(Output.X.getOutput(), xAttacked);
            coordinatesAttacked.put(Output.Y.getOutput(), yAttacked);

            result.set(Output.CARD_ATTACKER.getOutput(), coordinatesAttacker);
            result.set(Output.CARD_ATTACKED.getOutput(), coordinatesAttacked);

            if (errorNotEnemy) {
                result.put(Output.ERROR.getOutput(),
                        Error.NOT_ENEMY.getMessage());
            } else if (errorAttacked) {
                result.put(Output.ERROR.getOutput(),
                        Error.ATTACKER_ALREADY_ATTACKED.getMessage());
            } else if (errorFrozen) {
                result.put(Output.ERROR.getOutput(),
                        Error.FROZEN.getMessage());
            } else {
                result.put(Output.ERROR.getOutput(),
                        Error.NOT_TANK.getMessage());
            }
        }

        return result;
    }
}
