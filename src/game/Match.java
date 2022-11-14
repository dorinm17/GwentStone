package game;

import fileio.ActionsInput;

import fileio.CardInput;

import constants.Command;

import java.util.ArrayList;

public class Gameplay {
    private Player playerOne;
    private Player playerTwo;
    private Card[][] table = new Card[4][5];
    private int round;
    private int mana;

    public Gameplay(int startingPlayer, ArrayList<CardInput> deck1,
                    ArrayList<CardInput> deck2, CardInput hero1,
                    CardInput hero2) {
        playerOne = new Player(deck1, hero1);
        playerTwo = new Player(deck2, hero2);
        if (startingPlayer == 1) {
            playerOne.setTurn(true);
        } else {
            playerTwo.setTurn(true);
        }
        round = 1;
        mana = 1;
    }

    public void execute(ActionsInput action) {
        for (Command c : Command.values()) {
            if (c.getCommand().equals(action.getCommand())) {
                switch (c) {
                    case END_PLAYER_TURN:
                        // endPlayerTurn();
                        break;
                    case PLACE_CARD:
                        // placeCard();
                        break;
                    case CARD_USES_ATTACK:
                        // cardUsesAttack();
                        break;
                    case CARD_USES_ABILITY:
                        // cardUsesAbility();
                        break;
                    case USE_ATTACK_HERO:
                        // useAttackHero();
                        break;
                    case USE_HERO_ABILITY:
                        // useHeroAbility();
                        break;
                    case USE_ENVIRONMENT_CARD:
                        // useEnvironmentCard();
                        break;
                    case GET_CARDS_IN_HAND:
                        // getCardsInHand();
                        break;
                    case GET_PLAYER_DECK:
                        // getPlayerDeck(action.getPlayerIdx());
                        break;
                    case GET_CARDS_ON_TABLE:
                        // getCardsOnTable();
                        break;
                    case GET_PLAYER_TURN:
                        // getPlayerTurn();
                        break;
                    case GET_CARD_AT_POSITION:
                        // getCardAtPosition();
                        break;
                }
            }
        }
    }
}
