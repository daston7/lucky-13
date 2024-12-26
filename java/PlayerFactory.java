
// Euan Marshall, Dustin Susilo, Jeremy Tanasaleh
// Group 17, Thu1300
/** Singleton factory class to instantiate player strategy classes */
public class PlayerFactory {
    private PlayerStrategy playerAdapter = null;
    private static PlayerFactory instance = null;

    public static synchronized PlayerFactory getInstance() {
        if (instance == null) {
            instance = new PlayerFactory();
        }
        return instance;
    }


    public PlayerStrategy getPlayerAdapter(String playerType) {
        switch (playerType) {
            case "human":
                playerAdapter = (PlayerStrategy) new HumanPlayer();
                break;
            case "basic":
                playerAdapter = (PlayerStrategy) new BasicPlayer();
                break;
            case "clever":
                playerAdapter = (PlayerStrategy) new CleverPlayer();
                break;
            default:
                playerAdapter = (PlayerStrategy) new RandomPlayer();
                break;
        }
        return playerAdapter;

    }
}
