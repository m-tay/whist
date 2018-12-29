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
import java.util.Iterator;
import whist.Card.*;

public class Deck implements Serializable, Iterable<Card> {
    
    private static final long serialVersionUID = 200L; 
    
    // ArrayList inited with fixed size but not final - cards can be removed
    private ArrayList<Card> deck = new ArrayList(52);
    
    // default constructor - creates shuffled deck
    public Deck() {
       newDeck();   // call initialiser method
    }
    
    // returns number of cards left in deck
    public int size() {
        return deck.size();
    }
    
    public final void newDeck() {
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

    @Override
    public Iterator<Card> iterator() {
        return new DeckIterator();
    }
    
    public Iterator<Card> spadeIterator() {
        return new SpadeIterator();
    }
       
    // custom iterator that traverses a Deck and returns Cards
    private class DeckIterator<Card> implements Iterator<Card> {

        int index = 0; // index to keep track of position
        
        @Override
        public boolean hasNext() { // checks if there is another card in Deck
            return index < deck.size();
        }

        @Override
        public Card next() { // returns card at position, then increments
            return (Card)deck.get(index++);
        }      
        
        @Override 
        public void remove() { // removes card at position
            deck.remove(index - 1);

        }
    }
    
    // traverses spades in the deck
    private class SpadeIterator<Card> implements Iterator<Card> {
        
        @Override
        public boolean hasNext() {
            DeckIterator<Card> it = new DeckIterator<>();
                
            // iterate through list
            while(it.hasNext()) {
                Card nextCard = it.next();
                               
                // check if any of the cards are spades
                // THIS DOES NOT WORK
                if(nextCard == Suit.SPADES)
                    // return true if any spades are foud
                    return true;
            }
            
            // if no spades found, return false
            return false;
        }

        @Override
        public Card next() {
            DeckIterator it = new DeckIterator();
            Card nextCard;
            
            // iterate through list
            while(it.hasNext()) {
                // get the next card
                nextCard = (Card)it.next();
                
                // check if it's a spade
                // THIS DOES NOT WORK
                if(nextCard == Suit.SPADES)
                    // return true if any spades are foud
                    return nextCard;                
            }            
            
            return null; // TODO: throw exception           
        }
        
    }
        
    public Card deal() {
        // create new iterator
        DeckIterator it = new DeckIterator<>();
        
        // get the top card from the deck, then remove from deck
        Card topCard = (Card)it.next();
        it.remove(); 
        
        // return the dealt card
        return topCard;
    }
    
    public static void main(String args[]) {
        // create deck for testing
        Deck myDeck = new Deck();
        
        // iterator test
        for(Card c : myDeck) {
            System.out.println(c);
        }
        
        // deal() method test
        Card dealtCard = myDeck.deal();
        System.out.println("Dealt card is " + dealtCard);
        
        // print out deck to check deal() works
        for(Card c : myDeck) {
            System.out.println(c);
        }
        
        // test spadeIterator
        System.out.println("SpadeIterator test:");
        Iterator<Card> si = myDeck.spadeIterator();
        while(si.hasNext()) {
            System.out.println("Next spade is " + si.next());
        }
        
    }

}
