// Module:      CMP-5015Y  Programming 2
// Assignment:  Coursework 1
// 
// Author:      Matthew Taylor
// 
// Description: TODO

package whist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Card implements Serializable, Comparable<Card> {
    
    private static final long serialVersionUID = 100L; 

    @Override
    public int compareTo(Card other) {
        int thisSuit  = this.suit.getValue();
        int thisRank  = this.rank.getValue();
        int otherSuit = other.suit.getValue();
        int otherRank = other.rank.getValue();
        
        // if have the same rank
        if(thisRank == otherRank) {
            if(thisSuit < otherSuit)
                return -1;
            else if(thisSuit > otherSuit)
                return 1;            
        }
        else if(thisRank < otherRank)
            return -1;
        else if(thisRank > otherRank)
            return 1;
        
        // if none of the above return, must be equal
        return 0;                    
    }
    
    private enum Suit {
        CLUBS    (1),
        DIAMONDS (2),
        HEARTS   (3),
        SPADES   (4);
        
        private final int value;
        
        private Suit(int value) {
            this.value = value;
        }
        
        public static Suit getRandom(){
            // generate the random value within Suit's range
            int randomValue = (int) (Math.random() * values().length);
            
            // return Suit type at random value position
            return values()[randomValue];
        }      
        
        public int getValue() {
            return this.value;
        }
    }
    
    private enum Rank {
        TWO   (2),
        THREE (3),
        FOUR  (4),
        FIVE  (5), 
        SIX   (6),
        SEVEN (7),
        EIGHT (8),
        NINE  (9),
        TEN   (10),
        JACK  (10),
        QUEEN (10),
        KING  (10),
        ACE   (11);
        
        private final int value;
        
        private Rank(int value) {
            this.value = value;
        }
        
        // method to get next rank of card (looping from ACE to TWO)
        // requires an input of current Rank r
        public Rank getNext() {
            int curr = this.ordinal();       // get value of current index of Rank
            int next = curr + 1;          // calculate next value of Rank
            
            Rank[] ranks = Rank.values(); // fill array with all values of Rank
            next %= ranks.length;         // modulus gives us next value or 
                                          // loops from ACE to TWO

            return ranks[next];           // return next rank
        }
        
        public int getValue() {
            return this.value;
        }
    }
    
    // Card class attributes
    private final Suit suit;
    private final Rank rank;
    
    // default constructor
    public Card() {
        this.rank = Rank.FIVE;
        this.suit = Suit.SPADES;        
    } 
    
    // constructor specifying rank/suit
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }
    
    // accessor methods
    public Rank getRank() {
        return this.rank;
    }
    
    public Suit getSuit() {
        return this.suit;
    }
    
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
    
    // returns the max element from the given List of cards
    public static Card max(List<Card> cardList) {
        ListIterator<Card> iterator = cardList.listIterator(); // create iterator object
        
 
        Card max = iterator.next(); // set max to be first value of cardList
        Card next;                  // create next to be used in iterator
        
        // traverse list with iterator
        while(iterator.hasNext()) {
            next = iterator.next();         // set next value
            
            if(max.compareTo(next) < 1) {   // if max < next
                max = next;
            }           
        }
        
        return max;
    }
    
    // main contains all test code
    public static void main(String[] args) {
        
        // initialise test Card with default attributes
        Card testCardDefault = new Card();
        
        // test initial attributes
        System.out.println("testCard rank = " + testCardDefault.rank);
        System.out.println("testCard suit = " + testCardDefault.suit);
        
        // test Rank.getNext()
        Rank nextRank = testCardDefault.rank.getNext();
        System.out.println("testCard Rank.getNext() = " + nextRank);
        
        // test Rank.getValue()
        int value = testCardDefault.rank.getValue();
        System.out.println("testCard Rank.getValue() = " + value);
        
        // test Suit.getRandom method
        System.out.println("Random suit = " + Suit.getRandom());
        
        // initialise test Card with defined attributes
        Card testCardDefined = new Card(Rank.QUEEN, Suit.HEARTS);
        
        // test accessor methods
        System.out.println("testCardDefault suit = " + testCardDefault.getSuit());
        System.out.println("testCardDefined rank = " + testCardDefined.getRank());
        
        // test toString() method
        System.out.println("testCardDefined toString() = " + testCardDefault);
        System.out.println("testCardDefault toString() = " + testCardDefined);
        
        // test compareTo() implementation (should be -1)
        System.out.println(testCardDefault.compareTo(testCardDefined));
        
        // test compareTo() implementation (should be 1)
        System.out.println(testCardDefined.compareTo(testCardDefault));
        
        // test compareTo() implementation (should be 0)
        System.out.println(testCardDefined.compareTo(testCardDefined));
        
        // create List of cards
        ArrayList<Card> cardList = new ArrayList<>();
        cardList.add(new Card(Rank.TWO, Suit.CLUBS));
        cardList.add(new Card(Rank.FOUR, Suit.CLUBS));
        cardList.add(new Card(Rank.THREE, Suit.CLUBS));
        
        // run max(), print result
        Card maxCard = Card.max(cardList);
        System.out.println("maxCard = " + maxCard);

        
        
    }
}

