// Module:      CMP-5015Y  Programming 2
// Assignment:  Coursework 1
// 
// Author:      Matthew Taylor
// 
// Description: Stores values needed to describe a deck of cards

package whist;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList; 
import java.util.Collections;
import java.util.Iterator;
import whist.Card.*;

public class Deck implements Serializable, Iterable<Card> {
    
    private static final long serialVersionUID = 49L; 
    
    // custom serialization methods - makes the Deck class serialized using
    // the SpadeIterator     
    private void writeObject(ObjectOutputStream out) throws IOException {
        
    }
    
    private void readObject(ObjectInputStream in) throws IOException, 
                                                         ClassNotFoundException{
        
    }
    
    // ArrayList inited with fixed size but not final - cards can be removed
    private ArrayList<Card> deck = new ArrayList(52);
    
    // default constructor - creates shuffled deck
    public Deck() {
       newDeck();   // call initialiser method
    }
    
    // returns number of cards left in deck
    // TODO: actually use this method???
    public int size() {
        return deck.size();
    }
    
    // TODO: run this with just 2 for loops??
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
    private class DeckIterator implements Iterator<Card> {

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
    private class SpadeIterator implements Iterator<Card> {
        
        int index = 0; // index to keep track of position
        
        @Override
        public boolean hasNext() {        
            
            // loop through remaining cards (start from index)
            for(int i = index; i < deck.size(); i++) {
                
                // get the next card
                Card nextCard = deck.get(i);
                
                // return true if it is a SPADE
                if(nextCard.getSuit() == Suit.SPADES) {
                    index = i + 1;
                    return true;
                }
            }
            
            // otherwise, if no spades remain, return false
            return false;
        }

        @Override
        public Card next() {
            
            // loop through remaining cards
            for(int i = (index - 1); i < deck.size(); i++) {
                Card nextCard = deck.get(i);
                
                // if next card is a spade, return it
                if(nextCard.getSuit() == Suit.SPADES) {
                    return nextCard;
                }    
            }
            
            // should never happen
            return null;
        }
    }
        
    public Card deal() {
        // create new iterator
        DeckIterator it = new DeckIterator();
        
        // get the top card from the deck, then remove from deck
        Card topCard = it.next();
        it.remove(); 
        
        // return the dealt card
        return topCard;
    }
    
    public static void main(String args[]) {
        // create deck for testing
        Deck myDeck = new Deck();
        
        // iterator test
        System.out.println("Iterator test:");
        for(Card c : myDeck) {
            System.out.println(c);
        }
        System.out.println("\n");
        
        // deal() method test
        System.out.println("Testing deal() method:");
        Card dealtCard = myDeck.deal();
        System.out.println("Dealt card is " + dealtCard);
        System.out.println("\n");
        
        // print out deck to check deal() works
        System.out.println("Printing deck to check deal() works:");
        for(Card c : myDeck) {
            System.out.println(c);
        }
        System.out.println("\n");
        
        // test spadeIterator
        System.out.println("SpadeIterator test:");
        Iterator<Card> si = myDeck.spadeIterator();
        while(si.hasNext()) {
            System.out.println("Next spade is " + si.next());
        }
        
    }

}
