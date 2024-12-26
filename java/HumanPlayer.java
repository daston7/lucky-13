import ch.aplu.jcardgame.*;


// Euan Marshall, Dustin Susilo, Jeremy Tanasaleh
// Group 17, Thu1300
public class HumanPlayer implements PlayerStrategy {
    public void move(LuckyThirdteen game, GameLogic gameLogic) {
        this.settingTouchEnabled(game, gameLogic);
        this.getRandomCard(gameLogic, game);
        this.discardingCardHumanStrategy(game, gameLogic);
    }

    // method to set touch enabled allowing double click from human player
    public void settingTouchEnabled(LuckyThirdteen game, GameLogic gameLogic) {
        Hand[] hands = gameLogic.getHands();
        int nextPlayer = gameLogic.getNextPlayer();

        hands[nextPlayer].setTouchEnabled(true);
        game.setStatusText("Player "+nextPlayer+" is playing. Please double click on a card to discard");
        gameLogic.setSelected(null);
    }

    public void getRandomCard(GameLogic gameLogic, LuckyThirdteen game) {

        Hand pack = gameLogic.getPack();
        CardManager cardManager = gameLogic.getCardManager();
        Hand[] hands = gameLogic.getHands();
        int nextPlayer = gameLogic.getNextPlayer();
        int thinkingTime = game.getThinkingTime();

        cardManager.getRandomCard(hands[nextPlayer], pack, thinkingTime);
//        cardManager.dealACardToHand(hands[nextPlayer], pack);
    }

    // handles logic for discarding card as a human player according to clicking on a card
    public void discardingCardHumanStrategy(LuckyThirdteen game, GameLogic gameLogic) {
        int delayTime = game.getDelayTime();

        while (null == gameLogic.getSelected()) LuckyThirdteen.delay(delayTime);
        gameLogic.getSelected().removeFromHand(true);
    }

}
