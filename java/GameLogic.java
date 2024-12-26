import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import ch.aplu.jgamegrid.TextActor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// Euan Marshall, Dustin Susilo, Jeremy Tanasaleh
// Group 17, Thu1300
public class GameLogic {
    private boolean isAuto;
    public final int nbStartCards = 2;
    public final int nbFaceUpCards = 2;
    private final int handWidth = 400;
    private final int trickWidth = 40;
    private Hand[] hands;
    private CardManager cardManager = new CardManager();
    private int[] autoIndexHands;

    private Score score = new Score();
    //    private static final int THIRTEEN_GOAL = 13;
    private Card selected;
    private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
    private Hand playingArea;
    private Hand pack;
    int nextPlayer = 0;
    List<Card>cardsPlayed = new ArrayList<>();
    private final Location[] handLocations = {
            new Location(350, 625),
            new Location(75, 350),
            new Location(350, 75),
            new Location(625, 350)
    };
    private final Location trickLocation = new Location(350, 350);




    private final Location[] scoreLocations = {
            new Location(575, 675),
            new Location(25, 575),
            new Location(575, 25),
            // new Location(650, 575)
            new Location(575, 575)
    };
    LuckyThirdteen game;
    private final String version = "1.0";
    private List<List<String>> playerAutoMovements = new ArrayList<>();

    Font bigFont = new Font("Arial", Font.BOLD, 36);

    private Actor[] scoreActors = {null, null, null, null};

    public GameLogic(LuckyThirdteen game, boolean isAuto) {
        this.game = game;
        this.isAuto = isAuto;
        this.autoIndexHands = new int [game.getNbPlayers()];
    }

    public void runGameLogic() {
        game.setTitle("LuckyThirteen (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
        game.setStatusText("Initializing...");
        initScores();
        initScore();
        setupPlayerAutoMovements();
        initGame();
        playGame();
        for (int i = 0; i < game.getNbPlayers(); i++) updateScore(i);
    }

    private void initGame() {
        hands = new Hand[game.getNbPlayers()];
        for (int i = 0; i < game.getNbPlayers(); i++) {
            hands[i] = new Hand(deck);
        }
        playingArea = new Hand(deck);
        pack = cardManager.dealingOut(hands, game.getNbPlayers(), nbStartCards, nbFaceUpCards, deck, game.getProperties(), playingArea);
        if (pack.isEmpty()) return;
        playingArea.setView(game, new RowLayout(trickLocation, (playingArea.getNumberOfCards() + 2) * trickWidth));
        playingArea.draw();

        for (int i = 0; i < game.getNbPlayers(); i++) {
            hands[i].sort(Hand.SortType.SUITPRIORITY, false);
        }
        // Set up human player for interaction
        CardListener cardListener = new CardAdapter()  // Human Player plays card
        {
            public void leftDoubleClicked(Card card) {
                selected = card;
                for (int i = 0; i < game.getNbPlayers(); i++) {
                    if (game.getPlayerTypes().get(i).equals("human")){
                        hands[i].setTouchEnabled(false);
                    }
                }
            }
        };

        for (int i = 0; i < game.getNbPlayers(); i++) {
            if (game.getPlayerTypes().get(i).equals("human")){
                hands[i].addCardListener(cardListener);
            }
        }
        //hands[0].addCardListener(cardListener);
        // graphics
        RowLayout[] layouts = new RowLayout[game.getNbPlayers()];
        for (int i = 0; i < game.getNbPlayers(); i++) {
            layouts[i] = new RowLayout(handLocations[i], handWidth);
            layouts[i].setRotationAngle(90 * i);
            // layouts[i].setStepDelay(10);
            hands[i].setView(game, layouts[i]);
            hands[i].setTargetArea(new TargetArea(trickLocation));
            hands[i].draw();
        }
    }

    private void initScores() {
        Arrays.fill(game.getScores(), 0);
    }

    private void initScore() {
        for (int i = 0; i < game.getNbPlayers(); i++) {
            // scores[i] = 0;
            String text = "[" + String.valueOf(game.getScores()[i]) + "]";
            scoreActors[i] = new TextActor(text, Color.WHITE, game.bgColor, bigFont);
            game.addActor(scoreActors[i], scoreLocations[i]);
        }
    }

    // main method for initiating game
    private void playGame() {
        // End trump suit
        int winner = 0;
        int roundNumber = 1;
        for (int i = 0; i < game.getNbPlayers(); i++) updateScore(i);


        game.getLogManager().addRoundInfoToLog(roundNumber);


        while(roundNumber <= 4) {
            selected = null;
            boolean finishedAuto = false;

            if (isAuto) {
                int nextPlayerAutoIndex = autoIndexHands[nextPlayer];
                List<String> nextPlayerMovement = playerAutoMovements.get(nextPlayer);
                String nextMovement = "";

                if (nextPlayerMovement.size() > nextPlayerAutoIndex) {
                    nextMovement = nextPlayerMovement.get(nextPlayerAutoIndex);
                    nextPlayerAutoIndex++;

                    autoIndexHands[nextPlayer] = nextPlayerAutoIndex;
                    Hand nextHand = hands[nextPlayer];

                    // Apply movement for player
                    selected = applyAutoMovement(nextHand, nextMovement);
                    LuckyThirdteen.delay(game.getDelayTime());
                    if (selected != null) {
                        selected.removeFromHand(true);
                    } else {
                        selected = cardManager.getRandomCard(hands[nextPlayer], pack, game.getThinkingTime());
                        selected.removeFromHand(true);
                    }
                } else {
                    finishedAuto = true;
                }
            }



            // maybe have different classes for each player and have them have a move method with a common interface
            // i.e. use the strategy pattern

            if (!isAuto || finishedAuto) {
                //apply the moves to the players
                game.getCompositePlayer().move(game, this);
            }

            game.getLogManager().addCardPlayedToLog(nextPlayer, hands[nextPlayer].getCardList());
            if (selected != null) {
                cardsPlayed.add(selected);
                selected.setVerso(false);  // In case it is upside down
                LuckyThirdteen.delay(game.getDelayTime());
                // End Follow
            }

            nextPlayer = (nextPlayer + 1) % game.getNbPlayers();

            if (nextPlayer == 0) {
                roundNumber ++;
                game.getLogManager().addEndOfRoundToLog(game.getScores());

                if (roundNumber <= 4) {
                    game.getLogManager().addRoundInfoToLog(roundNumber);
                }
            }

            if (roundNumber > 4) {
                score.calculateScoreEndOfRound(game.getScores(), hands, playingArea);
            }
            LuckyThirdteen.delay(game.getDelayTime());
        }
    }


    private void setupPlayerAutoMovements() {
        String player0AutoMovement = game.getProperties().getProperty("players.0.cardsPlayed");
        String player1AutoMovement = game.getProperties().getProperty("players.1.cardsPlayed");
        String player2AutoMovement = game.getProperties().getProperty("players.2.cardsPlayed");
        String player3AutoMovement = game.getProperties().getProperty("players.3.cardsPlayed");

        String[] playerMovements = new String[] {"", "", "", ""};
        if (player0AutoMovement != null) {
            playerMovements[0] = player0AutoMovement;
        }

        if (player1AutoMovement != null) {
            playerMovements[1] = player1AutoMovement;
        }

        if (player2AutoMovement != null) {
            playerMovements[2] = player2AutoMovement;
        }

        if (player3AutoMovement != null) {
            playerMovements[3] = player3AutoMovement;
        }

        for (int i = 0; i < playerMovements.length; i++) {
            String movementString = playerMovements[i];
            if (movementString.equals("")) {
                playerAutoMovements.add(new ArrayList<>());
                continue;
            }
            List<String> movements = Arrays.asList(movementString.split(","));
            playerAutoMovements.add(movements);
        }
    }

    private Card applyAutoMovement(Hand hand, String nextMovement) {
        if (pack.isEmpty()) return null;
        String[] cardStrings = nextMovement.split("-");
        String cardDealtString = cardStrings[0];
        Card dealt = cardManager.getCardFromList(pack.getCardList(), cardDealtString);
        if (dealt != null) {
            dealt.removeFromHand(false);
            hand.insert(dealt, true);
        } else {
            System.out.println("cannot draw card: " + cardDealtString + " - hand: " + hand);
        }

        if (cardStrings.length > 1) {
            String cardDiscardString = cardStrings[1];
            return cardManager.getCardFromList(hand.getCardList(), cardDiscardString);
        } else {
            return null;
        }
    }

    private void updateScore(int player) {
        game.removeActor(scoreActors[player]);
        int displayScore = Math.max(game.getScores()[player], 0);
        String text = "P" + player + "[" + String.valueOf(displayScore) + "]";
        scoreActors[player] = new TextActor(text, Color.WHITE, game.bgColor, bigFont);
        game.addActor(scoreActors[player], scoreLocations[player]);
    }


    public Card getSelected() {
        return selected;
    }

    public void setSelected(Card selected) {
        this.selected = selected;
    }

    public Hand[] getHands() {
        return hands;
    }

    public int getNextPlayer() {
        return nextPlayer;
    }

    public Hand getPack() {
        return pack;
    }

    public CardManager getCardManager() {
        return cardManager;
    }

    public Hand getPlayingArea() {
        return playingArea;
    }

    public Deck getDeck() {
        return deck;
    }

    public Score getScore() {
        return score;
    }

    public List<Card> getCardsPlayed() {
        return cardsPlayed;
    }
}