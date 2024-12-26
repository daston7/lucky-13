import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import ch.aplu.jcardgame.Deck;
import java.util.List;

import java.util.ArrayList;

// Euan Marshall, Dustin Susilo, Jeremy Tanasaleh
// Group 17, Thu1300

/** Class to deal with logic for clever player strategy */
public class CleverPlayer implements PlayerStrategy{

    public void move(LuckyThirdteen game, GameLogic gameLogic) {
        this.getRandomCard(game, gameLogic);
        this.discardingCardCleverStrategy(game, gameLogic);

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

    // handle discard by clever player
    public void discardingCardCleverStrategy(LuckyThirdteen game, GameLogic gameLogic) {

        Hand[] hands = gameLogic.getHands();
        int nextPlayer = gameLogic.getNextPlayer();
        CardManager cardManager = gameLogic.getCardManager();
        Hand playingArea = gameLogic.getPlayingArea();
        Deck deck = gameLogic.getDeck();
        Score score = gameLogic.getScore();
        List<Card> cardsPlayed= gameLogic.getCardsPlayed();

        gameLogic.setSelected(cleverDiscardCard(hands[nextPlayer], cardManager, playingArea, deck, score, cardsPlayed));

        gameLogic.getSelected().removeFromHand(true);


    }

    // strategy for discarding of card by clever player, determines if a sum of 13 can be reached with any of the 3 cards
    // in hand, keeps highest scoring 13 if so, otherwise determines probability of low or high card drawn next,
    // and discards accordingly
    public Card cleverDiscardCard(Hand hand, CardManager cardManager, Hand playingArea, Deck deck, Score score, List<Card> cardsPlayed) {
         Card discardCard;
         int [] scores = new int[3];
         Hand [] hands = new Hand[3];
         int iterator = 0;

         for (int i = 0; i < 3; i++){
             Card card1 = hand.getCardList().get(i);


             //card1
             Hand currentHand = new Hand(deck);
             for (int j = 0; j < 3; j++){
                 Card card2 = hand.getCardList().get(j);
                 if (i != j){
                     currentHand.insert(card2, false);
                 }
             }


             hands[iterator] = currentHand;
             iterator++;
         }



         if (score.calculateScoreClever(scores, hands, playingArea)) {
             int highestScore = 0;
             int highestScoreHand = 0;
             for (int i = 0; i < 3; i++) {
                 if (scores[i] > highestScore) {
                     highestScore = scores[i];
                     highestScoreHand = i;
                 }
             }

             discardCard = hand.getCardList().get(highestScoreHand);

             hand.getCardList().remove(discardCard);

         } else {
             int cardsSmall = 24;
             int cardsBig = 28;

             for (int i =0; i < cardsPlayed.size(); i++) {
                 if (cardsPlayed.get(i).getCardNumber() > 6) {
                     cardsBig--;
                 } else {
                     cardsSmall--;
                 }
             }
             for (int i = 0; i < 3; i++) {
                 if (hand.getCardList().get(i).getCardNumber() > 6) {
                     cardsBig--;
                 } else {
                     cardsSmall--;
                 }
             }

             for (int i = 0; i < 2; i++) {
                 if (playingArea.getCardList().get(i).getCardNumber() > 6) {
                     cardsBig--;
                 } else {
                     cardsSmall--;
                 }
             }

             int probSmall = (cardsSmall*100)/24;
             int probBig = (cardsBig*100)/28;

             discardCard = hand.getCardList().get(0);
             if (probSmall > probBig) {
                 for (int i = 1; i < 3; i++) {
                     if (hand.getCardList().get(i).getCardNumber() > discardCard.getCardNumber()) {
                         discardCard = hand.getCardList().get(i);
                     }
                 }
             } else {
                 for (int i = 1; i < 3; i++) {
                     if (hand.getCardList().get(i).getCardNumber() < discardCard.getCardNumber()) {
                         discardCard = hand.getCardList().get(i);
                     }
                 }
             }
             hand.getCardList().remove(discardCard);


         }

         return discardCard;


    }

}
