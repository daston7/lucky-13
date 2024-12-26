
// Euan Marshall, Dustin Susilo, Jeremy Tanasaleh
// Group 17, Thu1300
public enum Suit {
    SPADES ("S", 4), HEARTS ("H", 3),
    DIAMONDS ("D", 2), CLUBS ("C", 1);
    private String suitShortHand = "";
    private int multiplicationFactor = 1;
    public static final int PUBLIC_CARD_MULTIPLICATION_FACTOR = 2;
    Suit(String shortHand, int multiplicationFactor) {
        this.suitShortHand = shortHand;
        this.multiplicationFactor = multiplicationFactor;
    }

    public String getSuitShortHand() {
        return suitShortHand;
    }

    public int getMultiplicationFactor() {
        return multiplicationFactor;
    }
}