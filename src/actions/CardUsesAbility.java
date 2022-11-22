package actions;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Output;

import constants.Error;

import constants.Values;

import constants.MinionBackRow;

import constants.MinionFrontRow;

import fileio.ActionsInput;

import game.Card;


public abstract class CardUsesAbility {
    /**
     */
    public static ObjectNode execute(final ActionsInput action,
                                     final Card[][] board) {
        ObjectNode result = null;
        boolean errorFrozen = false;
        boolean errorAttacked = false;
        boolean errorNotCurrentPlayer = false;
        boolean errorNotEnemy = false;
        boolean errorNotTank = false;
        int xAttacker = action.getCardAttacker().getX();
        int yAttacker = action.getCardAttacker().getY();
        int xAttacked = action.getCardAttacked().getX();
        int yAttacked = action.getCardAttacked().getY();
        Card attacker = board[xAttacker][yAttacker];
        Card attacked = board[xAttacked][yAttacked];
        int enemyFrontRow;
        int enemyBackRow;

        if (attacker.isFrozen()) {
            errorFrozen = true;
        }

        if (attacker.getAttacked()) {
            errorAttacked = true;
        }

        if (xAttacker == Values.PLAYER_ONE_FRONT_ROW.getValue()
                || xAttacker == Values.PLAYER_ONE_BACK_ROW.getValue()) {
            enemyFrontRow = Values.PLAYER_TWO_FRONT_ROW.getValue();
            enemyBackRow = Values.PLAYER_TWO_BACK_ROW.getValue();
        } else {
            enemyFrontRow = Values.PLAYER_ONE_FRONT_ROW.getValue();
            enemyBackRow = Values.PLAYER_ONE_BACK_ROW.getValue();
        }

        if (attacker.getCard().getName().equals(
                MinionBackRow.DISCIPLE.getName())) {
            if (xAttacked == enemyFrontRow || xAttacked == enemyBackRow) {
                errorNotCurrentPlayer = true;
            } else if (!errorFrozen && !errorAttacked) {
                attacked.getCard().setHealth(attacked.getCard().getHealth()
                        + Values.GODS_PLAN.getValue());
            }
        } else if (!errorFrozen && !errorAttacked) {
            if (xAttacked != enemyFrontRow && xAttacked != enemyBackRow) {
                errorNotEnemy = true;
            } else {
                boolean tankExists = false;

                for (int i = 0; i < Values.COLUMNS.getValue(); i++) {
                    Card card = board[enemyFrontRow][i];

                    if (card != null) {
                        if (card.getCard().getName().equals(
                                MinionFrontRow.GOLIATH.getName())
                                || card.getCard().getName().equals(
                                MinionFrontRow.WARDEN.getName())) {
                            tankExists = true;
                            break;
                        }
                    }
                }

                if (tankExists) {
                    if (!(attacked.getCard().getName().equals(
                            MinionFrontRow.GOLIATH.getName()))
                            && !(attacked.getCard().getName().equals(
                            MinionFrontRow.WARDEN.getName()))) {
                        errorNotTank = true;
                    }
                }

                if (!errorNotTank) {
                    if (attacker.getCard().getName().equals(
                            MinionFrontRow.THE_RIPPER.getName())) {
                        attacked.getCard().setAttackDamage(
                                attacked.getCard().getAttackDamage()
                                - Values.WEAK_KNEES.getValue());
                    } else if (attacker.getCard().getName().equals(
                            MinionFrontRow.MIRAJ.getName())) {
                        int aux = attacked.getCard().getHealth();
                        attacked.getCard().setHealth(
                                attacker.getCard().getHealth());
                        attacker.getCard().setHealth(aux);
                    } else {
                        int aux = attacked.getCard().getHealth();
                        attacked.getCard().setHealth(
                                attacked.getCard().getAttackDamage());
                        attacked.getCard().setAttackDamage(aux);
                    }
                }
            }
        }

        if (errorFrozen || errorAttacked || errorNotCurrentPlayer
                || errorNotEnemy || errorNotTank) {
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

            if (errorFrozen) {
                result.put(Output.ERROR.getOutput(),
                        Error.FROZEN.getMessage());
            } else if (errorAttacked) {
                result.put(Output.ERROR.getOutput(),
                        Error.ATTACKER_ALREADY_ATTACKED.getMessage());
            } else if (errorNotCurrentPlayer) {
                result.put(Output.ERROR.getOutput(),
                        Error.NOT_CURRENT_PLAYER.getMessage());
            } else if (errorNotEnemy) {
                result.put(Output.ERROR.getOutput(),
                        Error.NOT_ENEMY.getMessage());
            } else {
                result.put(Output.ERROR.getOutput(),
                        Error.NOT_TANK.getMessage());
            }
        } else {
            attacker.setAttacked(true);

            if (attacked.getCard().getHealth() <= 0) {
                board[xAttacked][yAttacked] = null;
            }

            if (attacked.getCard().getAttackDamage() < 0) {
                attacked.getCard().setAttackDamage(0);
            }
        }

        return result;
    }
}
