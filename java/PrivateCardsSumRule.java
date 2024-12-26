import ch.aplu.jcardgame.Card;

import java.util.List;

// Euan Marshall, Dustin Susilo, Jeremy Tanasaleh
// Group 17, Thu1300
/** logic to handle determining if sum of 13 can be achieved using only private cards */
public class PrivateCardsSumRule implements SumRule {

    private static final int THIRTEEN_GOAL = 13;


    @Override
    public boolean isThirteen(List<Card> privateCards, List<Card> publicCards) {
        // Assume each player has 2 private cards in hand
        if (privateCards.size() >= 2) {
            return isThirteenCards(privateCards.get(0), privateCards.get(1));
        }
        return false;
    }


    public boolean isThirteenCards(Card card1, Card card2) {
        Rank rank1 = (Rank) card1.getRank();
        Rank rank2 = (Rank) card2.getRank();
        return isThirteenFromPossibleValues(rank1.getPossibleSumValues(), rank2.getPossibleSumValues());
    }

    // iterates through possible values of private cards, determining if a sum of 13 can be reached with them
    private boolean isThirteenFromPossibleValues(int[] possibleValues1, int[] possibleValues2) {
        for (int value1 : possibleValues1) {
            for (int value2 : possibleValues2) {
                if (value1 + value2 == THIRTEEN_GOAL) {
                    return true;
                }
            }
        }
        return false;
    }
}
