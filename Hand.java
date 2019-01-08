package whist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import whist.Card.*;

public class Hand implements Serializable, Iterable {
    
    private static final long serialVersionUID = 300L; 
    
    // holds all the cards in the hand
    private ArrayList<Card> hand;
    
    // used to set orderAdded of cards (default to 0)
    int cardOrder = 0;
    
    // holds the count of all the suits
    private int countClubs = 0;
    private int countDiamonds = 0;
    private int countHearts = 0;
    private int countSpades = 0;
            
    // stores the values of the cards
    private ArrayList<Integer> values;
    
    // default constructor (empty hand)
    public Hand() {
        hand = new ArrayList<>(); // creates empty arraylist
    }
    
    // constructor that takes array of cards
    public Hand(ArrayList<Card> cards) {
        // use arraylist constructor to shallow copy in cards
        hand = new ArrayList<>(cards);         
        
        // create initial suit count and ordering
        hand.forEach((Card i) -> {
            // increases the suit counter
            increaseSuitCount(i);
            
            // sets the cards order added
            setOrderAdded(i);
        });        
    }
    
    // constructor that takes another Hand
    public Hand(Hand otherHand) {
        // shallow copy cards from another hand
        hand = otherHand.hand;
        
        // create initial suit count
        hand.forEach((Card i) -> {
            // increases the suit counter
            increaseSuitCount(i);

            // sets the cards order added
            setOrderAdded(i);
            
        }); 
    }
    
    private void increaseSuitCount (Card card) {
        switch (card.getSuit()) {
                case CLUBS:  
                    countClubs++;
                    break;
                case DIAMONDS:
                    countDiamonds++;
                    break;
                case HEARTS:
                    countHearts++;
                    break;
                case SPADES:
                    countSpades++;
                    break;
                }       
    }
    
    // sets the card's order added, increments cardOrder
    private void setOrderAdded (Card card) {
        card.setOrder(cardOrder);
        cardOrder++;
    }
    
    // generates and stores the value(s) for the hand
    private ArrayList<Integer> generateValues() {
        
        // holds high/low values of aces
        int[] aceValues = {1, 11};
       
        // initalise arraylist to store values, initalise to have one value
        ArrayList<Integer> vals = new ArrayList<>();
        vals.add(0);
        
        // check each card
        for(int i = 0; i < hand.size(); i++) {
            // if an ace, copy the currently found values, then add the 
            // values for high/low aces to each value calculated so far
            if(hand.get(i).getRank() == Rank.ACE) {
                
                // get last element, copy it on to end of arraylist
                int lastElement = vals.get(vals.size() - 1);
                vals.add(lastElement);
                
                // loop through all elements in vals, add ace values
                for(int j = 0; j < vals.size(); j++) {
                    // get element in arraylist
                    int elementValue = vals.get(j);
                    
                    // add on rank value
                    // use modulus to rotate through ace low/high values
                    elementValue += aceValues[j % 2];
                    
                    // re-add element back in correct position
                    vals.set(j, elementValue);
                }                
            }
            else {
                // loop through all elements in vals list, add rank value to it
                for(int j = 0; j < vals.size(); j++) {
                    // get element in arraylist
                    int elementValue = vals.get(j);
                    
                    // add on rank value
                    elementValue += hand.get(i).getRank().getValue();
                    
                    // re-add element back in correct position
                    vals.set(j, elementValue);
                }
            }
        } 
        
        return vals;
    }
    
    // add method that takes a card
    public void add(Card card) {
       hand.add(card);
       increaseSuitCount(card);
    }
    
    // add method that takes a Collection
    public void add(Collection<Card> collection) {
        // create iterator
        Iterator<Card> it = collection.iterator();
        
        // iterate through collection
        while(it.hasNext()) {
            Card card = it.next();
            hand.add(card);
            increaseSuitCount(card);
        }
        
    }
    
    // add method that takes another Hand
    public void add(Hand h) {
        // create iterator
        Iterator handIt = h.iterator();
        
        // iterate through other hand and add it to this one
        while(handIt.hasNext()) {
            hand.add((Card)handIt.next());
        }
    }

    // removes single card (if present), returns bool about success
    public boolean remove(Card card) {
        // loop through hand, if given card is found, remove it and return true
        for(int i = 0; i < hand.size(); i++) {
            if(hand.get(i) == card) {
                hand.remove(card);  // remove card
                return true;        
            }
        }
        
        // if card not found in hand, return false
        return false;        
    }
    
    
    
    
    @Override
    public Iterator iterator() {
        return new HandIterator();
    }
    
    private class HandIterator implements Iterator<Card> {

        int index = 0;
        int lastOrderFound = -1;
        int smallestOrderIndex = -1;// holds index of smallest in order
        int smallestOrder;     // holds smallest ordering found
        
        @Override
        public boolean hasNext() {
            return index < hand.size();
        }

        // finds smallest orderAdded in list, returns it
        @Override
        public Card next() {
            int smallestOrder = 100;     // holds smallest ordering found
            
            // loop through entire hand
            for(int i = 0; i < hand.size(); i++) {

//                System.out.println("for loop i = " + i);
//                System.out.println("hand.size() = " + hand.size());

                // get card order
                int cardOrder = hand.get(i).getOrder();
                
                // check if it the smallest order greater than any ordering
                // value currently found
                if(cardOrder < smallestOrder && cardOrder > lastOrderFound) {
                    smallestOrder = cardOrder; // set smallest order
                    smallestOrderIndex = i;    // set index of smallest order                    
                }
            }
            
            // set lastOrderFound so we know what the next smallest cardOrder
            // has to be greater than
            lastOrderFound = smallestOrder;
            index++;
            return hand.get(smallestOrderIndex);
        }
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        
        s.append("Hand contains:\n");
        
        for(int i = 0; i < hand.size(); i++) {
            s.append("> ");
            s.append(hand.get(i)); // Card already has a toString()
            s.append("\n");
        }
        
        return s.toString();
    }
    
    
    public static void main(String args[]) {
        // test each constructor type
        Hand emptyHand = new Hand();
        System.out.println("Empty constructor test:");
        System.out.println("emptyHand clubs count = " + emptyHand.countClubs);
        System.out.println("emptyHand diamonds count = " + emptyHand.countDiamonds);
        System.out.println("emptyHand hearts count = " + emptyHand.countHearts);
        System.out.println("emptyHand spades count = " + emptyHand.countSpades + "\n");

        // create list of cards to add
        ArrayList<Card> cardList = new ArrayList<>();     
        cardList.add(new Card(Rank.TWO, Suit.CLUBS));
        cardList.add(new Card(Rank.FOUR, Suit.CLUBS));
        cardList.add(new Card(Rank.THREE, Suit.CLUBS));
        cardList.add(new Card(Rank.ACE, Suit.SPADES));
        cardList.add(new Card(Rank.NINE, Suit.SPADES));
        cardList.add(new Card(Rank.THREE, Suit.HEARTS));
        cardList.add(new Card(Rank.ACE, Suit.HEARTS));
        cardList.add(new Card(Rank.NINE, Suit.DIAMONDS));

        // test Hand constructor with cardList
        Hand listHand = new Hand(cardList);
        System.out.println("List constructor test:");
        System.out.println("listHand clubs count = " + listHand.countClubs);
        System.out.println("listHand diamonds count = " + listHand.countDiamonds);
        System.out.println("listHand hearts count = " + listHand.countHearts);
        System.out.println("listHand spades count = " + listHand.countSpades + "\n");        
       
        // test Hand constructor with Hand
        Hand handHand = new Hand(listHand);
        System.out.println("Hand constructor test:");
        System.out.println("handHand clubs count = " + handHand.countClubs);
        System.out.println("handHand diamonds count = " + handHand.countDiamonds);
        System.out.println("handHand hearts count = " + handHand.countHearts);
        System.out.println("handHand spades count = " + handHand.countSpades + "\n");  
        
        // test generateValues() function
        System.out.println("generateValues() test:");
        ArrayList<Integer> listHandValues = listHand.generateValues();
        for(int i = 0; i < listHandValues.size(); i++) {
            System.out.println("listHandValues["+i+"] = " + listHandValues.get(i));
        }
        System.out.println("\n");
        
        // test add() methods
        // test adding single card
        System.out.println("add() methods testing:");
        Card newCard = new Card(Rank.FIVE, Suit.HEARTS);
        emptyHand.add(newCard);
        System.out.println(emptyHand);
        
        // test adding Collection
        ArrayList<Card> cardList2 = new ArrayList<>();
        cardList2.add(new Card(Rank.FIVE, Suit.CLUBS));
        cardList2.add(new Card(Rank.QUEEN, Suit.HEARTS));
        System.out.println("Test adding Collection to Hand:");
        emptyHand.add(cardList2);
        System.out.println(emptyHand);
        
        
        // test adding Hand
        System.out.println("Test adding Hand to hand:");
        emptyHand.add(listHand);
        System.out.println(emptyHand);
        System.out.println("\n");
        
        
        
        
        
        
   }


    
}
