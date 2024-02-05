import java.lang.reflect.Array;
import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand = new ArrayList<>();
    private final String[] ranks = {"Royal Flush", "Straight Flush", "4-of-a-kind", "Full House", "Flush", "Straight",
            "3-of-a-kind", "2 Pair", "1 Pair", "High Card"};

    public void addCard(Card c){
        if(hand.size() < 5){
            hand.add(c);
        }
    }

    public String getHandRank(){
        if(hand.size() != 5){
            return "Incorrect hand size";
        }
        //sort the hand
        hand.sort(new SortCards());

        // determine if the hand can even get Flush Royale at all based on the lowest card
        boolean royale = false;
        if (hand.get(0).getNumericValue() >= 10) {
            royale = true;
        } else {
            royale = false;
        }

        // counting sort arrays for checking pairings
        int[] vals = new int[15];
        int[] faces = new int[4];

        int straight = 0;
        int prevVal = 0;

        // use counting sort to count pairings
        for (Card c : hand) {
            int val = c.getNumericValue();
            // counts the number of cards in a hand have the same value
            vals[val] += 1;

            // check if the card values have been ascending
            // also checks if the last card is A to account for High-Fives
            if (prevVal != 0 && (prevVal + 1 == val || (straight == 4 && c.getValue().equals("A")))) {
                straight++;
            } else {
                straight = 0;
            }
            switch (c.getSuit()) {
                case "Clubs":
                    faces[0]++;
                    break;
                case "Hearts":
                    faces[1]++;
                    break;
                case "Diamonds":
                    faces[2]++;
                    break;
                case "Spades":
                    faces[3]++;
                    break;
                default:
                    System.out.println("face error, check switch");
                    break;
            }
            prevVal = val;
        }

        // check the faces count array to check if a face has been repeated 5 times
        boolean flush = false;
        for (int f : faces) {
            if (f == 5) {
                flush = true;
                break;
            }
        }

        boolean oneP = false;
        int pairs = 0;
        boolean threeK = false;
        boolean fourK = false;

        for (Card c : hand) {
            int val = c.getNumericValue();
            if (vals[val] == 2) {
                oneP = true;
                pairs++;
            }
            if (vals[val] == 3) {
                threeK = true;
            }
            if (vals[val] == 4) {
                fourK = true;
            }
        }
        //high card (None of the other hands match, the highest value of the card)

        //one pair ( a pair of cards with the same value e.g. 7D, 7H, 4S, 6H, 8H)
        if (oneP & pairs == 1) {
            return ranks[8];
        }

        //two pair (2 pairs of matched values e.g. 7D, 7H, 4S, 4C, 2D)
        if (pairs >= 2) {
            return ranks[7];
        }

        //3 of a kind (3 cards with the same value and two others e.g. 7D, 7H, 7C, 2H, KS)
        if (threeK) {
            return ranks[6];
        }

        //straight (A run of values in different suits e.g. 3H, 4D, 5H, 6C, 7S)
        if (straight == 4 && !flush && !royale) {
            return ranks[5];
        }

        //flush (All cards are in the same suit e.g. 3H, 7H, 9H, JH, KH)
        if (flush && straight < 4) {
            return ranks[4];
        }

        //full house (3 of a kind and a pair e.g. 7S, 7H, 7D, 4C, 4H)
        if (oneP & threeK) {
            return ranks[3];
        }

        //4 of a kind (4 cards with the same value e.g. 9S, 9C, 9H, 9D, 7D)
        if (fourK) {
            return ranks[2];
        }

        //straight flush (5 cards in a row all of the same suit e.g. 3S, 4S, 5S, 6S, 7S)
        if (straight == 4 & flush && !royale) {
            return ranks[1];
        }

        //royal flush (J,Q,K,A,10 all of the same suit)
        if (royale && straight == 4) {
            return ranks[0];
        }

        return ranks[9];
    }

    public String toString(){
        String output = "";
        for(Card c: hand){
            if(c.getSuit().equals("Hearts") || c.getSuit().equals("Diamonds")) {
                output += "\u001B[31m[ " + c.getValue() + " , " + c.getSuit() + " ] \u001B[0m";
            }
            else{
                output += "[ " + c.getValue() + " , " + c.getSuit() + " ] ";
            }
        }

        return output;
    }
}
