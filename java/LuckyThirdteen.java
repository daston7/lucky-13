// LuckyThirteen.java

import ch.aplu.jcardgame.CardGame;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class LuckyThirdteen extends CardGame {
    final String trumpImage[] = {"bigspade.gif", "bigheart.gif", "bigdiamond.gif", "bigclub.gif"};

    private Properties properties;

    public final int nbPlayers = 4;

    private final Location textLocation = new Location(350, 450);
    private int thinkingTime = 2000;
    private int delayTime = 600;

    private List<String> playerTypes;

    private CompositePlayer compositePlayer;

    private int[] scores = new int[nbPlayers];

    private LogManager logManager = new LogManager();
    private GameLogic gameLogic;

    private boolean isAuto = false;


    // main method called by driver to run game
    public String runApp() {
        gameLogic = new GameLogic(this, isAuto);
        gameLogic.runGameLogic();


        int maxScore = 0;
        for (int i = 0; i < nbPlayers; i++) if (scores[i] > maxScore) maxScore = scores[i];
        List<Integer> winners = new ArrayList<Integer>();
        for (int i = 0; i < nbPlayers; i++) if (scores[i] == maxScore) winners.add(i);
        String winText;
        if (winners.size() == 1) {
            winText = "Game over. Winner is player: " +
                    winners.iterator().next();
        } else {
            winText = "Game Over. Drawn winners are players: " +
                    String.join(", ", winners.stream().map(String::valueOf).collect(Collectors.toList()));
        }
        addActor(new Actor("sprites/gameover.gif"), textLocation);
        setStatusText(winText);
        refresh();
        logManager.addEndOfGameToLog(winners, scores);

        return logManager.getLog();
    }

    public LuckyThirdteen(Properties properties) {
        super(700, 700, 30);
        this.properties = properties;
        isAuto = Boolean.parseBoolean(properties.getProperty("isAuto"));
        thinkingTime = Integer.parseInt(properties.getProperty("thinkingTime", "200"));
        delayTime = Integer.parseInt(properties.getProperty("delayTime", "50"));

        playerTypes = List.of(properties.getProperty("players.0"), properties.getProperty("players.1"), properties.getProperty("players.2"), properties.getProperty("players.3") );
        parseCompositePlayer(properties);

    }

    private void parseCompositePlayer(Properties properties) {
        compositePlayer = new CompositePlayer();

        for (int i = 0; i < 4; i++) {
            String playerType = properties.getProperty("players." + i);
            compositePlayer.addPlayerAdapter(PlayerFactory.getInstance().getPlayerAdapter(playerType));
        }
    }



    public int[] getScores() {
        return scores;
    }

    public int getNbPlayers() {
        return nbPlayers;
    }

    public Properties getProperties() {
        return properties;
    }

    public LogManager getLogManager() {
        return logManager;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public int getThinkingTime() {
        return thinkingTime;
    }

    public List<String> getPlayerTypes(){ return playerTypes; }

    public CompositePlayer getCompositePlayer() {
        return compositePlayer;
    }
}
