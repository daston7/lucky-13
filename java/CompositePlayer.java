import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;
import java.util.List;


// Euan Marshall, Dustin Susilo, Jeremy Tanasaleh
// Group 17, Thu1300
public class CompositePlayer implements PlayerStrategy {
    private List<PlayerStrategy> playerStrategies = new ArrayList<>();


    public void addPlayerAdapter(PlayerStrategy playerAdapter) {
        playerStrategies.add(playerAdapter);
    }

    // moves all players in the game
    @Override
    public void move(LuckyThirdteen game, GameLogic gameLogic) {
        playerStrategies.get(gameLogic.getNextPlayer()).move(game, gameLogic);
    }

    public List<PlayerStrategy> getPlayerAdapters() {
        return playerStrategies;
    }
}
