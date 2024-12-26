import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

// Euan Marshall, Dustin Susilo, Jeremy Tanasaleh
// Group 17, Thu1300
/** Handles logic for random player strategy */
public class RandomPlayer implements PlayerStrategy {


    public void move(LuckyThirdteen game, GameLogic gameLogic) {

        this.discardCardRandomStrategy(game, gameLogic);
    }

    // discards card randomly according to strategy for random player
    public void discardCardRandomStrategy(LuckyThirdteen game, GameLogic gameLogic) {

        Hand[] hands = gameLogic.getHands();
        int nextPlayer = gameLogic.getNextPlayer();
        Hand pack = gameLogic.getPack();
        int thinkingTime = game.getThinkingTime();
        CardManager cardManager = gameLogic.getCardManager();

        game.setStatusText("Player " + nextPlayer + " thinking...");
        gameLogic.setSelected(cardManager.getRandomCard(hands[nextPlayer], pack, thinkingTime));
        gameLogic.getSelected().removeFromHand(true);

    }


}
