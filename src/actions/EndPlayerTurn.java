package actions;

import constants.Values;

import game.Card;

import game.Match;

public abstract class EndPlayerTurn {
    /**
     */
    public static void execute(final Match match) {
        if (match.getPlayerOne().getTurn()) {
            match.getPlayerOne().setTurn(false);
            match.getPlayerTwo().setTurn(true);

            for (int i = Values.PLAYER_ONE_FRONT_ROW.getValue();
                 i <= Values.PLAYER_ONE_BACK_ROW.getValue(); i++) {
                for (Card card : match.getBoard()[i]) {
                    if (card != null) {
                        card.setAttacked(false);
                        card.setFrozen(false);
                    }
                }
            }

            match.getPlayerOne().getHero().setAttacked(false);
        } else {
            match.getPlayerOne().setTurn(true);
            match.getPlayerTwo().setTurn(false);

            for (int i = Values.PLAYER_TWO_BACK_ROW.getValue();
                 i <= Values.PLAYER_TWO_FRONT_ROW.getValue(); i++) {
                for (Card card : match.getBoard()[i]) {
                    if (card != null) {
                        card.setAttacked(false);
                        card.setFrozen(false);
                    }
                }
            }

            match.getPlayerTwo().getHero().setAttacked(false);
        }

        match.setTurn(match.getTurn() + 1);

        if (match.getTurn() % 2 == 1) {
            if (match.getMana() < Values.MAX_MANA_ROUND.getValue()) {
                match.setMana(match.getMana() + 1);
            }

            match.getPlayerOne().setMana(
                    match.getPlayerOne().getMana() + match.getMana());
            match.getPlayerTwo().setMana(
                    match.getPlayerTwo().getMana() + match.getMana());

            if (match.getPlayerOne().getDeck().size() > 0) {
                match.getPlayerOne().getHand().add(
                        match.getPlayerOne().getDeck().get(0));
                match.getPlayerOne().getDeck().remove(0);
            }

            if (match.getPlayerTwo().getDeck().size() > 0) {
                match.getPlayerTwo().getHand().add(
                        match.getPlayerTwo().getDeck().get(0));
                match.getPlayerTwo().getDeck().remove(0);
            }
        }
    }
}
