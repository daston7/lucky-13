import ch.aplu.jcardgame.Hand;

import java.util.List;

// Euan Marshall, Dustin Susilo, Jeremy Tanasaleh
// Group 17, Thu1300
/** Score strategy interface, implemented by different scoring strategies */
public interface ScoreStrategy {
    void calculateScore(int[] scores, List<Integer> indexesWithThirteen, Hand[] hands, Hand playingArea);
}
