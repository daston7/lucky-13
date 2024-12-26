import ch.aplu.jcardgame.Hand;

import java.util.List;

// Euan Marshall, Dustin Susilo, Jeremy Tanasaleh
// Group 17, Thu1300
public class MultiplePlayersWinScoreStrategy implements ScoreStrategy {
    private final Score score;

    public MultiplePlayersWinScoreStrategy(Score score) {
        this.score = score;
    }

    // method from score strategy interface, calculates score when multiple players have achieved a score of 13
    @Override
    public void calculateScore(int[] scores, List<Integer> indexesWithThirteen, Hand[] hands, Hand playingArea) {
        for (Integer thirteenIndex : indexesWithThirteen) {
            scores[thirteenIndex] = score.calculateMaxScoreForThirteenPlayer(thirteenIndex, hands, playingArea);
        }
    }
}
