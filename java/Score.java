import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


// Euan Marshall, Dustin Susilo, Jeremy Tanasaleh
// Group 17, Thu1300
/** Class to handle general scoring logic */
public class Score {

    // gets score of private card
    public int getScorePrivateCard(Card card) {
        Rank rank = (Rank) card.getRank();
        Suit suit = (Suit) card.getSuit();

        return rank.getScoreCardValue() * suit.getMultiplicationFactor();
    }

    // gets score of public card
    private int getScorePublicCard(Card card) {
        Rank rank = (Rank) card.getRank();
        return rank.getScoreCardValue() * Suit.PUBLIC_CARD_MULTIPLICATION_FACTOR;
    }

    // gets score of all cards
    private int getScoreAllCards(List<Card> privateCards, List<Card> publicCards) {
        int totalScore = 0;
        for (Card card : privateCards) {
            totalScore += getScorePrivateCard(card);
        }
        for (Card card : publicCards) {
            totalScore += getScorePublicCard(card);
        }
        return totalScore;
    }


    // finds max score for the player in question
    public int calculateMaxScoreForThirteenPlayer(int playerIndex, Hand[] hands, Hand playingArea) {
        List<Card> privateCards = hands[playerIndex].getCardList();
        List<Card> publicCards = playingArea.getCardList();
        int maxScore = 0;

        List<SumRule> sumRules = Arrays.asList(
                new PrivateCardsSumRule(),
                new MixedCardsSumRule(),
                new AllCardsSumRule()
        );

        // Iterate over each sum rule and assign score based on private card sum rule or mixed cards sum rule
        for (SumRule rule : sumRules) {
            if (rule instanceof PrivateCardsSumRule && rule.isThirteen(privateCards, null)) {
                int score = getScorePrivateCard(privateCards.get(0)) + getScorePrivateCard(privateCards.get(1));
                maxScore = Math.max(maxScore, score);
            } else {
                for (Card privateCard : privateCards) {
                    for (Card publicCard : publicCards) {
                        if (rule.isThirteen(List.of(privateCard), List.of(publicCard))) {
                            int score = getScorePrivateCard(privateCard) + getScorePublicCard(publicCard);
                            maxScore = Math.max(maxScore, score);
                        }
                    }
                }
            }
        }

        // Assign score for all cards sum rule
        if (sumRules.get(2).isThirteen(privateCards, publicCards)) {
            int score = getScoreAllCards(privateCards, publicCards);
            maxScore = Math.max(maxScore, score);
        }

        return maxScore;
    }


    // calculates scores for players at the end of the round and puts them into the scores array
    public void calculateScoreEndOfRound(int[] scores, Hand[] hands, Hand playingArea) {
        List<Boolean> isThirteenChecks = new ArrayList<>(Collections.nCopies(hands.length, Boolean.FALSE));
        List<SumRule> sumRules = Arrays.asList(
                new PrivateCardsSumRule(),
                new MixedCardsSumRule(),
                new AllCardsSumRule()
        );

        for (int i = 0; i < hands.length; i++) {
            boolean isThirteen = false;
            List<Card> privateCards = hands[i].getCardList();
            List<Card> publicCards = playingArea.getCardList();

            for (SumRule rule : sumRules) {
                if (rule.isThirteen(privateCards, publicCards)) {
                    isThirteen = true;
                    break;
                }
            }

            isThirteenChecks.set(i, isThirteen);
        }

        List<Integer> indexesWithThirteen = new ArrayList<>();

        for (int i = 0; i < isThirteenChecks.size(); i++) {
            if (isThirteenChecks.get(i)) {
                indexesWithThirteen.add(i);
            }
        }



        ScoreStrategy scoreStrategy = ScoreStrategyFactory.getInstance().createScoreStrategy(indexesWithThirteen.size(), this);
        scoreStrategy.calculateScore(scores, indexesWithThirteen, hands, playingArea);

    }

    // calculates possible scores for the clever player in attempt to find optimal hand to continue with
    public boolean calculateScoreClever(int[] scores, Hand[] hands, Hand playingArea) {
        List<Boolean> isThirteenChecks = new ArrayList<>(Collections.nCopies(hands.length, Boolean.FALSE));
        List<SumRule> sumRules = Arrays.asList(
                new PrivateCardsSumRule(),
                new MixedCardsSumRule(),
                new AllCardsSumRule()
        );

        for (int i = 0; i < hands.length; i++) {
            boolean isThirteen = false;
            List<Card> privateCards = hands[i].getCardList();
            List<Card> publicCards = playingArea.getCardList();

            for (SumRule rule : sumRules) {
                if (rule.isThirteen(privateCards, publicCards)) {
                    isThirteen = true;
                    break;
                }
            }

            isThirteenChecks.set(i, isThirteen);
        }

        List<Integer> indexesWithThirteen = new ArrayList<>();

        for (int i = 0; i < isThirteenChecks.size(); i++) {
            if (isThirteenChecks.get(i)) {
                indexesWithThirteen.add(i);
            }
        }

        if (indexesWithThirteen.isEmpty()){
            return false;
        }

        ScoreStrategy scoreStrategy = ScoreStrategyFactory.getInstance().createScoreStrategy(indexesWithThirteen.size(), this);
        scoreStrategy.calculateScore(scores, indexesWithThirteen, hands, playingArea);
        return true;
    }

}