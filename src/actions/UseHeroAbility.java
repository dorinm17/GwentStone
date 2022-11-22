package actions;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Hero;

import constants.Output;

import constants.Values;

import constants.Error;

import fileio.ActionsInput;

import game.Card;

import game.Player;

public abstract class UseHeroAbility {
    /**
     */
    public static ObjectNode execute(final ActionsInput action,
                                     final Player playerOne,
                                     final Player playerTwo,
                                     final Card[][] board) {
        ObjectNode result = null;
        boolean errorMana = false;
        boolean errorAttacked = false;
        boolean errorNotEnemy = false;
        boolean errorNotCurrentPlayer = false;
        Player currentPlayer;
        Card currentHero;
        int affectedRow = action.getAffectedRow();

        if (playerOne.getTurn()) {
            currentPlayer = playerOne;
            currentHero = playerOne.getHero();

            if (currentHero.getCard().getName().equals(
                    Hero.LORD_ROYCE.getName())
                    || currentHero.getCard().getName().equals(
                    Hero.EMPRESS_THORINA.getName())) {
                if (affectedRow
                        == Values.PLAYER_ONE_FRONT_ROW.getValue()
                        || affectedRow
                        == Values.PLAYER_ONE_BACK_ROW.getValue()) {
                    errorNotEnemy = true;
                }
            } else if (affectedRow
                    == Values.PLAYER_TWO_FRONT_ROW.getValue()
                    || affectedRow
                    == Values.PLAYER_TWO_BACK_ROW.getValue()) {
                errorNotCurrentPlayer = true;
            }
        } else {
            currentPlayer = playerTwo;
            currentHero = playerTwo.getHero();

            if (currentHero.getCard().getName().equals(
                    Hero.LORD_ROYCE.getName())
                    || currentHero.getCard().getName().equals(
                    Hero.EMPRESS_THORINA.getName())) {
                if (affectedRow
                        == Values.PLAYER_TWO_FRONT_ROW.getValue()
                        || affectedRow
                        == Values.PLAYER_TWO_BACK_ROW.getValue()) {
                    errorNotEnemy = true;
                }
            } else if (affectedRow
                    == Values.PLAYER_ONE_FRONT_ROW.getValue()
                    || affectedRow
                    == Values.PLAYER_ONE_BACK_ROW.getValue()) {
                errorNotCurrentPlayer = true;
            }
        }

        if (currentPlayer.getMana() < currentHero.getCard().getMana()) {
            errorMana = true;
        }

        if (currentHero.getAttacked()) {
            errorAttacked = true;
        }

        if (!errorMana && !errorAttacked && !errorNotEnemy
                && !errorNotCurrentPlayer) {
            currentHero.setAttacked(true);
            currentPlayer.setMana(currentPlayer.getMana()
                    - currentHero.getCard().getMana());

            int maxAttackDamage = -1;
            int maxHealth = 0;
            int yMaxAttackDamage = 0;
            int yMaxHealth = 0;

            for (int i = 0; i < Values.COLUMNS.getValue(); i++) {
                Card card = board[affectedRow][i];
                if (card != null) {
                    if (card.getCard().getAttackDamage()
                            > maxAttackDamage) {
                        maxAttackDamage = card.getCard().getAttackDamage();
                        yMaxAttackDamage = i;
                    }

                    if (card.getCard().getHealth() > maxHealth) {
                        maxHealth = card.getCard().getHealth();
                        yMaxHealth = i;
                    }
                }
            }

            if (currentHero.getCard().getName().equals(
                    Hero.LORD_ROYCE.getName())) {
                board[affectedRow][yMaxAttackDamage].setFrozen(true);
            } else if (currentHero.getCard().getName().equals(
                    Hero.EMPRESS_THORINA.getName())) {
                board[affectedRow][yMaxHealth] = null;
            } else if (currentHero.getCard().getName().equals(
                    Hero.KING_MUDFACE.getName())) {
                for (int i = 0; i < Values.COLUMNS.getValue(); i++) {
                    Card card = board[affectedRow][i];
                    if (card != null) {
                        card.getCard().setHealth(card.getCard().getHealth()
                                + Values.EARTH_BORN.getValue());
                    }
                }
            } else {
                for (int i = 0; i < Values.COLUMNS.getValue(); i++) {
                    Card card = board[affectedRow][i];
                    if (card != null) {
                        card.getCard().setAttackDamage(
                                card.getCard().getAttackDamage()
                                + Values.BLOOD_THIRST.getValue());
                    }
                }
            }
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.createObjectNode();
            result.put(Output.COMMAND.getOutput(), action.getCommand());
            result.put(Output.AFFECTED_ROW.getOutput(), affectedRow);

            if (errorMana) {
                result.put(Output.ERROR.getOutput(),
                        Error.NOT_ENOUGH_MANA_HERO.getMessage());
            } else if (errorAttacked) {
                result.put(Output.ERROR.getOutput(),
                        Error.HERO_ALREADY_ATTACKED.getMessage());
            } else if (errorNotEnemy) {
                result.put(Output.ERROR.getOutput(),
                        Error.NOT_ENEMY_ROW_HERO.getMessage());
            } else {
                result.put(Output.ERROR.getOutput(),
                        Error.NOT_CURRENT_PLAYER_ROW.getMessage());
            }
        }

        return result;
    }
}
