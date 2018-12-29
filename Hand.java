package whist;

import java.util.ArrayList;

public class Hand {
    
    // holds all the cards in the hand
    private ArrayList<Card> hand;
    
    // holds the count of all the suits
    int countClubs;
    int countDiamonds;
    int countHearts;
    int countSpades;
            
    
    // default constructor (empty hand)
    public Hand() {
        hand = new ArrayList<>(); // creates empty arraylist
    }
    
    // constructor that takes array of cards
    public Hand(ArrayList<Card> cards) {
        // use arraylist constructor to shallow copy in cards
        hand = new ArrayList<>(cards);                                       
    }
    
    // constructor that takes another Hand
    public Hand(Hand otherHand) {
        // shallow copy cards from another hand
        hand = otherHand.hand;
    }
    
}
