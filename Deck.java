// Module:      CMP-5015Y  Programming 2
// Assignment:  Coursework 1
// 
// Author:      Matthew Taylor
// 
// Description: Stores values needed to describe a deck of cards

package whist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Deck implements Serializable {
    
    private static final long serialVersionUID = 200L; 
    
    // ArrayList inited with fixed size but not final - cards can be removed
    private ArrayList<Card> deck = new ArrayList(52);
    
    // default constructor - creates shuffled deck
    public Deck() {
        // add all 52 possible cards
        deck.add(new Card(Card.Rank.TWO,    Card.Suit.CLUBS));
        deck.add(new Card(Card.Rank.THREE,  Card.Suit.CLUBS));
        deck.add(new Card(Card.Rank.FOUR,   Card.Suit.CLUBS));
        deck.add(new Card(Card.Rank.FIVE,   Card.Suit.CLUBS));
        deck.add(new Card(Card.Rank.SIX,    Card.Suit.CLUBS));
        deck.add(new Card(Card.Rank.SEVEN,  Card.Suit.CLUBS));
        deck.add(new Card(Card.Rank.EIGHT,  Card.Suit.CLUBS));
        deck.add(new Card(Card.Rank.NINE,   Card.Suit.CLUBS));
        deck.add(new Card(Card.Rank.TEN,    Card.Suit.CLUBS));
        deck.add(new Card(Card.Rank.JACK,   Card.Suit.CLUBS));
        deck.add(new Card(Card.Rank.QUEEN,  Card.Suit.CLUBS));
        deck.add(new Card(Card.Rank.KING,   Card.Suit.CLUBS));
        deck.add(new Card(Card.Rank.ACE,    Card.Suit.CLUBS));
        deck.add(new Card(Card.Rank.TWO,    Card.Suit.DIAMONDS));
        deck.add(new Card(Card.Rank.THREE,  Card.Suit.DIAMONDS));
        deck.add(new Card(Card.Rank.FOUR,   Card.Suit.DIAMONDS));
        deck.add(new Card(Card.Rank.FIVE,   Card.Suit.DIAMONDS));
        deck.add(new Card(Card.Rank.SIX,    Card.Suit.DIAMONDS));
        deck.add(new Card(Card.Rank.SEVEN,  Card.Suit.DIAMONDS));
        deck.add(new Card(Card.Rank.EIGHT,  Card.Suit.DIAMONDS));
        deck.add(new Card(Card.Rank.NINE,   Card.Suit.DIAMONDS));
        deck.add(new Card(Card.Rank.TEN,    Card.Suit.DIAMONDS));
        deck.add(new Card(Card.Rank.JACK,   Card.Suit.DIAMONDS));
        deck.add(new Card(Card.Rank.QUEEN,  Card.Suit.DIAMONDS));
        deck.add(new Card(Card.Rank.KING,   Card.Suit.DIAMONDS));
        deck.add(new Card(Card.Rank.ACE,    Card.Suit.DIAMONDS));
        deck.add(new Card(Card.Rank.TWO,    Card.Suit.HEARTS));
        deck.add(new Card(Card.Rank.THREE,  Card.Suit.HEARTS));
        deck.add(new Card(Card.Rank.FOUR,   Card.Suit.HEARTS));
        deck.add(new Card(Card.Rank.FIVE,   Card.Suit.HEARTS));
        deck.add(new Card(Card.Rank.SIX,    Card.Suit.HEARTS));
        deck.add(new Card(Card.Rank.SEVEN,  Card.Suit.HEARTS));
        deck.add(new Card(Card.Rank.EIGHT,  Card.Suit.HEARTS));
        deck.add(new Card(Card.Rank.NINE,   Card.Suit.HEARTS));
        deck.add(new Card(Card.Rank.TEN,    Card.Suit.HEARTS));
        deck.add(new Card(Card.Rank.JACK,   Card.Suit.HEARTS));
        deck.add(new Card(Card.Rank.QUEEN,  Card.Suit.HEARTS));
        deck.add(new Card(Card.Rank.KING,   Card.Suit.HEARTS));
        deck.add(new Card(Card.Rank.ACE,    Card.Suit.HEARTS));
        deck.add(new Card(Card.Rank.TWO,    Card.Suit.SPADES));
        deck.add(new Card(Card.Rank.THREE,  Card.Suit.SPADES));
        deck.add(new Card(Card.Rank.FOUR,   Card.Suit.SPADES));
        deck.add(new Card(Card.Rank.FIVE,   Card.Suit.SPADES));
        deck.add(new Card(Card.Rank.SIX,    Card.Suit.SPADES));
        deck.add(new Card(Card.Rank.SEVEN,  Card.Suit.SPADES));
        deck.add(new Card(Card.Rank.EIGHT,  Card.Suit.SPADES));
        deck.add(new Card(Card.Rank.NINE,   Card.Suit.SPADES));
        deck.add(new Card(Card.Rank.TEN,    Card.Suit.SPADES));
        deck.add(new Card(Card.Rank.JACK,   Card.Suit.SPADES));
        deck.add(new Card(Card.Rank.QUEEN,  Card.Suit.SPADES));
        deck.add(new Card(Card.Rank.KING,   Card.Suit.SPADES));
        deck.add(new Card(Card.Rank.ACE,    Card.Suit.SPADES));
        
        // shuffle the deck
        Collections.shuffle(deck);        
    }
    
    
}
