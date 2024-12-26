
/** Factory to create score strategies */

// Euan Marshall, Dustin Susilo, Jeremy Tanasaleh
// Group 17, Thu1300
public class ScoreStrategyFactory {

    private static ScoreStrategyFactory instance = null;

    public static synchronized ScoreStrategyFactory getInstance() {
        if (instance == null) {
            instance = new ScoreStrategyFactory();
        }
        return instance;
    }

    public ScoreStrategy createScoreStrategy(int countTrue, Score score) {
        if (countTrue == 1) {
            return new OnePlayerWinsScoreStrategy();
        } else if (countTrue > 1) {
            return new MultiplePlayersWinScoreStrategy(score);
        } else {
            return new NoPlayerWinsScoreStrategy(score);
        }
    }
}
