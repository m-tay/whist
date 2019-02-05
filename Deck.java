// Module:      CMP-5015Y  Programming 2
// Assignment:  Coursework 1
// 
// Author:      Matthew Taylor
// 
// Description: Stores values needed to describe a deck of cards

package whist;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    
    // custom serialization methods - makes the Deck class serializable using
    // the SpadeIterator         
    private void readObject(ObjectInputStream in) throws IOException, 
                                                         ClassNotFoundException{
 
        in.defaultReadObject();  
        
        // create the input collection
        ArrayList<Card> inList = new ArrayList();
        
        // read object in and save in collection
        inList = (ArrayList<Card>) in.readObject();
        
        // set deck to deserialized object
        deck = inList;
    }
    
    private void writeObject(ObjectOutputStream out) throws IOException {
        // default serialization
        out.defaultWriteObject();
        
        // create the output collection
        ArrayList<Card> outList = new ArrayList();
        
        // create the spadeiterator to populate outList
        SpadeIterator it = new SpadeIterator();
        
        // loop through deck, adding cards to 
        it.forEachRemaining(e -> outList.add(e));
        
        // write object out
        out.writeObject(outList);
    }
     
    
    // ArrayList inited with fixed size but not final - cards can be removed
    private ArrayList<Card> deck = new ArrayList(52);
    
    // default constructor - creates shuffled deck
    public Deck() {
       newDeck();   // call initialiser method
    }
    
    // removes a card from the Deck
    public void remove(Card c) {
        // check if element is contained
        if(deck.contains(c))       
            deck.remove(c);
        // todo throw exception?
        
    }
    
    // returns number of cards left in deck
    public int size() {
        return deck.size();
    }
    
    public final void newDeck() {
        // add all 52 possible cards - loop through suits
        for(Suit s : Suit.values()) {
            
            // loop through all values
            for(Rank r : Rank.values()) {
                deck.add(new Card(r, s));
            }
        }
        
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
        
    // gets the top card, removes it from the deck and returns it
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
        
        // test serialization
        try {
            // set up output streams
            FileOutputStream fileOut = new FileOutputStream("deck.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            
            // serialize through Serializable interface
            out.writeObject(myDeck);   
            
            // tidy up
            out.close();
            fileOut.close();
        }   
        catch (IOException i){
            System.out.println("IOException in serialization: " + i);
        }
        
        // test deserialization
        Deck deserDeck = null; // deck object to hold deserialized deck        
        
        try {
            // set up input streams
            FileInputStream fileIn = new FileInputStream("deck.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            
            // deserialize
            deserDeck = (Deck) in.readObject();
            
            // tidy up
            in.close();
            fileIn.close();
            
        }
        catch (IOException i) {
            System.out.println("IOException in serialization: " + i);
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found error ");
        }
        
        // print out deserDeck to test it deserialized properly
        System.out.println("\nDeserialized deck is: ");
        for(Card c : deserDeck) {
            System.out.println(c);
        }
        
    }

}
