// Module:      CMP-5015Y  Programming 2
// Assignment:  Coursework 1
// 
// Author:      Matthew Taylor
// 
// Description: TODO

package whist;

import java.io.Serializable;

public class Card implements Serializable {
    
    private static final long serialVersionUID = 100L; 
    
    private enum Suit {
        CLUBS,
        DIAMONDS,
        HEARTS,
        SPADES;
        
        // TODO: add method to randomly return suit
        //       (why on earth am I doing this????)
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
    
    private final Suit suit;
    private final Rank rank;
    
    // default constructor
    public Card() {
        this.rank = Rank.FIVE;
        this.suit = Suit.SPADES;
        
    } 
    
    // main contains all test code
    public static void main(String[] args) {
        
        // initialise test Card with default attributes
        Card testCard = new Card();
        
        // test initial attributes
        System.out.println("testCard rank = " + testCard.rank);
        System.out.println("testCard suit = " + testCard.suit);
        
        // test Rank.getNext()
        Rank nextRank = testCard.rank.getNext();
        System.out.println("testCard Rank.getNext() = " + nextRank);
        
        // test Rank.getValue()
        int value = testCard.rank.getValue();
        System.out.println("testCard Rank.getValue() = " + value);
        
        
        
        
    }
}

