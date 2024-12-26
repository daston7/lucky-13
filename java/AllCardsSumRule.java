import ch.aplu.jcardgame.Card;

import java.util.List;

// Euan Marshall, Dustin Susilo, Jeremy Tanasaleh
// Group 17, Thu1300

/** Handles the summing rule in which all cards are considered  */
public class AllCardsSumRule implements SumRule {

    private static final int THIRTEEN_GOAL = 13;


    // determines whether a sum of 13 is reached by all 4 cards, returns boolean
    @Override
    public boolean isThirteen(List<Card> privateCards, List<Card> publicCards) {

        if (privateCards.size() < 2 || publicCards.size() < 2) {
            return false;
        }

        Card privateCard1 = privateCards.get(0);
        Card privateCard2 = privateCards.get(1);
        Rank privateRank1 = (Rank) privateCard1.getRank();
        Rank privateRank2 = (Rank) privateCard2.getRank();

        Card publicCard1 = publicCards.get(0);
        Card publicCard2 = publicCards.get(1);
        Rank publicRank1 = (Rank) publicCard1.getRank();
        Rank publicRank2 = (Rank) publicCard2.getRank();

        return isThirteenFromFourPossibleValues(privateRank1.getPossibleSumValues(), privateRank2.getPossibleSumValues(), publicRank1.getPossibleSumValues(), publicRank2.getPossibleSumValues());
    }

    // Cycles through all possibles values of all cards to determine whether a sum of 13 can be reached
    private boolean isThirteenFromFourPossibleValues(int[] possibleValues1, int[] possibleValues2, int[] possibleValues3, int[] possibleValues4) {
        for (int value1 : possibleValues1) {
            for (int value2 : possibleValues2) {
                for (int value3 : possibleValues3) {
                    for (int value4 : possibleValues4) {
                        if (value1 + value2 + value3 + value4 == THIRTEEN_GOAL) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    }

