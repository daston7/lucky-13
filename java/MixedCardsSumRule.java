import ch.aplu.jcardgame.Card;

import java.util.List;


// Euan Marshall, Dustin Susilo, Jeremy Tanasaleh
// Group 17, Thu1300
public class MixedCardsSumRule implements SumRule {
    private static final int THIRTEEN_GOAL = 13;

    // method from SumRule interface implemented for mixed cards summing strategy
    @Override
    public boolean isThirteen(List<Card> privateCards, List<Card> publicCards) {
        for (Card privateCard : privateCards) {
            for (Card publicCard : publicCards) {
                if (isThirteenCards(privateCard, publicCard)) {
                    return true;
                }
            }
        }

        return false;
    }


    public boolean isThirteenCards(Card card1, Card card2) {
        Rank rank1 = (Rank) card1.getRank();
        Rank rank2 = (Rank) card2.getRank();
        return isThirteenFromPossibleValues(rank1.getPossibleSumValues(), rank2.getPossibleSumValues());
    }

    // determines whether a score of 13 can be achived by 1 card from hand and 1 card from public cards, returns boolean
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
