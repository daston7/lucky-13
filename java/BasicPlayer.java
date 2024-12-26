import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

// Euan Marshall, Dustin Susilo, Jeremy Tanasaleh
// Group 17, Thu1300
/** Handles logic for basic player strategy */
public class BasicPlayer implements PlayerStrategy{
    public void move(LuckyThirdteen game, GameLogic gameLogic){
        this.getRandomCard(game, gameLogic);
        this.discardingCardBasicStrategy(game, gameLogic);
    }


    public void getRandomCard(LuckyThirdteen game, GameLogic gameLogic) {

        Hand pack = gameLogic.getPack();
        int thinkingTime = game.getThinkingTime();
        CardManager cardManager = gameLogic.getCardManager();
        Hand[] hands = gameLogic.getHands();
        int nextPlayer = gameLogic.getNextPlayer();

//        cardManager.dealACardToHand(hands[nextPlayer], pack);
        cardManager.getRandomCard(hands[nextPlayer], pack, thinkingTime);
        game.setStatusText("Player " + nextPlayer + " thinking...");

    }
    // method to handle logic for discarding a card accoridng to basic player logic
    public void discardingCardBasicStrategy(LuckyThirdteen game, GameLogic gameLogic) {

        Hand[] hands = gameLogic.getHands();
        int nextPlayer = gameLogic.getNextPlayer();
        CardManager cardManager = gameLogic.getCardManager();

        gameLogic.setSelected(basicDiscardCard(hands[nextPlayer], cardManager));
        gameLogic.getSelected().removeFromHand(true);

    }

    // finds lowest card to discard as per basic player strategy
    public Card basicDiscardCard(Hand hand, CardManager cardManager) {
        Card lowestValueCard = null;
        int lowestValue = Integer.MAX_VALUE;

        for (Card card : hand.getCardList()) {
            int cardValue = cardManager.getCardValue(card);
            if (cardValue < lowestValue) {
                lowestValue = cardValue;
                lowestValueCard = card;
            }
        }

        return lowestValueCard;
    }

}
