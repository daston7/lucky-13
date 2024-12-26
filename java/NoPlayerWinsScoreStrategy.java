import ch.aplu.jcardgame.Hand;

import java.util.List;

// Euan Marshall, Dustin Susilo, Jeremy Tanasaleh
// Group 17, Thu1300
public class NoPlayerWinsScoreStrategy implements ScoreStrategy{

    private final Score score;

    public NoPlayerWinsScoreStrategy(Score score) {
        this.score = score;
    }

    // method from score strategy interface, calculates score when no players have achieved a score of 13
    @Override
    public void calculateScore(int[] scores, List<Integer> indexesWithThirteen, Hand[] hands, Hand playingArea) {
        for (int i = 0; i < scores.length; i++) {
            scores[i] = score.getScorePrivateCard(hands[i].getCardList().get(0)) +
                    score.getScorePrivateCard(hands[i].getCardList().get(1));
        }
    }
}
