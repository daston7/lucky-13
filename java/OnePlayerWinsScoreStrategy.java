import ch.aplu.jcardgame.Hand;

import java.util.List;

// Euan Marshall, Dustin Susilo, Jeremy Tanasaleh
// Group 17, Thu1300
public class OnePlayerWinsScoreStrategy implements ScoreStrategy {

    // method from score strategy interface, calculates score when one player has achieved a score of 13

    @Override
    public void calculateScore(int[] scores, List<Integer> indexesWithThirteen, Hand[] hands, Hand playingArea) {
        int winnerIndex = indexesWithThirteen.get(0);
        scores[winnerIndex] = 100;
    }
}
