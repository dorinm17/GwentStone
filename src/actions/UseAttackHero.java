package actions;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Error;

import constants.GameEnded;

import constants.MinionFrontRow;

import constants.Output;

import constants.Values;

import fileio.ActionsInput;

import fileio.CardInput;

import game.Card;

import game.Gameplay;

import game.Player;

public abstract class UseAttackHero {
    /**
     */
    public static ObjectNode execute(final ActionsInput action,
                                     final Player playerOne,
                                     final Player playerTwo,
                                     final Card[][] board) {
        ObjectNode result = null;
        boolean errorAttacked = false;
        boolean errorFrozen = false;
        boolean errorNotTank = false;
        int xAttacker = action.getCardAttacker().getX();
        int yAttacker = action.getCardAttacker().getY();
        int enemyFrontRow;
        CardInput enemyHero;

        if (playerOne.getTurn()) {
            enemyFrontRow = Values.PLAYER_TWO_FRONT_ROW.getValue();
            enemyHero = playerTwo.getHero().getCard();
        } else {
            enemyFrontRow = Values.PLAYER_ONE_FRONT_ROW.getValue();
            enemyHero = playerOne.getHero().getCard();
        }

        if (board[xAttacker][yAttacker].isFrozen()) {
            errorFrozen = true;
        }

        if (board[xAttacker][yAttacker].getAttacked()) {
            errorAttacked = true;
        }

        for (int i = 0; i < Values.COLUMNS.getValue(); i++) {
            Card card = board[enemyFrontRow][i];
            if (card != null) {
                if (card.getCard().getName().equals(
                        MinionFrontRow.GOLIATH.getName())
                        || board[enemyFrontRow][i].getCard().getName().equals(
                        MinionFrontRow.WARDEN.getName())) {
                    errorNotTank = true;
                    break;
                }
            }
        }

        if (!errorFrozen && !errorAttacked && !errorNotTank) {
            board[xAttacker][yAttacker].setAttacked(true);
            enemyHero.setHealth(enemyHero.getHealth()
                    - board[xAttacker][yAttacker].getCard().getAttackDamage());

            if (enemyHero.getHealth() <= 0) {
                ObjectMapper objectMapper = new ObjectMapper();
                result = objectMapper.createObjectNode();

                if (playerOne.getTurn()) {
                    Gameplay.setPlayerOneWins(Gameplay.getPlayerOneWins() + 1);
                    result.put(Output.GAME_ENDED.getOutput(),
                            GameEnded.PLAYER_ONE_WON.getMessage());
                } else {
                    Gameplay.setPlayerTwoWins(Gameplay.getPlayerTwoWins() + 1);
                    result.put(Output.GAME_ENDED.getOutput(),
                            GameEnded.PLAYER_TWO_WON.getMessage());
                }
            }
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.createObjectNode();
            result.put(Output.COMMAND.getOutput(), action.getCommand());

            ObjectNode coordinatesAttacker = objectMapper.createObjectNode();
            coordinatesAttacker.put(Output.X.getOutput(), xAttacker);
            coordinatesAttacker.put(Output.Y.getOutput(), yAttacker);
            result.set(Output.CARD_ATTACKER.getOutput(), coordinatesAttacker);

            if (errorFrozen) {
                result.put(Output.ERROR.getOutput(),
                        Error.FROZEN.getMessage());
            } else if (errorAttacked) {
                result.put(Output.ERROR.getOutput(),
                        Error.ATTACKER_ALREADY_ATTACKED.getMessage());
            } else {
                result.put(Output.ERROR.getOutput(),
                        Error.NOT_TANK.getMessage());
            }
        }

        return result;
    }
}
