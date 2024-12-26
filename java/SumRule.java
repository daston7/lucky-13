import ch.aplu.jcardgame.Card;

import java.util.List;

// Euan Marshall, Dustin Susilo, Jeremy Tanasaleh
// Group 17, Thu1300

/** interface implemented by different summing rule classes  */
public interface SumRule {

    // common isThirteen method for all summing classes, allows one method to be called for all different summing rules
    boolean isThirteen(List<Card> privateCards, List<Card> publicCards);
}
