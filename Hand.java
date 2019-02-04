package whist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
    
    // increases the suit counters, based on given card
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
    
    // decreases the suit counters, based on given card
    private void decreaseSuitCount (Card card) {
        switch (card.getSuit()) {
                case CLUBS:  
                    countClubs--;
                    break;
                case DIAMONDS:
                    countDiamonds--;
                    break;
                case HEARTS:
                    countHearts--;
                    break;
                case SPADES:
                    countSpades--;
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
       hand.add(card);              // add card to hand
       increaseSuitCount(card);     // update suit counters
       setOrderAdded(card);         // set order added of card
    }
    
    // add method that takes a Collection
    public void add(Collection<Card> collection) {
        // create iterator
        Iterator<Card> it = collection.iterator();
        
        // iterate through collection
        while(it.hasNext()) {
            Card card = it.next();  // get next card
            hand.add(card);         // add next card to hand
            increaseSuitCount(card);// update suit counters
            setOrderAdded(card);    // set order added of card
        }
        
    }
    
    // add method that takes another Hand
    public void add(Hand h) {
        // create iterator
        OrderAddedIterator handIt = h.iterator();
        
        // iterate through other hand and add it to this one
        while(handIt.hasNext()) {
            // get next card
            Card nextCard = handIt.next();
            hand.add(nextCard);         // get next card
            increaseSuitCount(nextCard);// update suit counter
            setOrderAdded(nextCard);    // set order added of card
        }
    }

    // removes single card (if present), returns bool about success
    public boolean remove(Card card) {
        boolean result = false;
        
        // ArrayList's remove() method works because equals() is overridden
        // in the Card class
        if(hand.remove(card)) {
            result = true;
            decreaseSuitCount(card);
        }
        
        return result;     
    }
    
    // removes all cards from hand based on given hand, returns true if
    // all cards are removed
    public boolean remove(Hand other) {
        int cardsRemoved = 0;   // stores number of cards removed

        // create iterator
        OrderAddedIterator handIt = other.iterator();        
        
        // iterate through other hand, remove any found cards and count up
        // removed cards for later comparison
        while(handIt.hasNext()) {
            Card nextCard = handIt.next();
            System.out.println("nextCard is: " + nextCard);
            if(hand.remove(nextCard)) {
                cardsRemoved++;
                decreaseSuitCount(nextCard);
            }
            
        }
        
        // if all cards in other were removed, return true
        return cardsRemoved == other.hand.size();
    }
    
    // removes card at a given position, returns card at that position
    public Card remove(int i) throws NoValidCardsFoundException {
        
        // check if a valid card exists at given position
        if(hand.size() < i) 
            throw new NoValidCardsFoundException("No card exists at this position.");
        
        // if card exists...
        Card removedCard = hand.get(i); // get card from hand
        hand.remove(removedCard);       // remove card from hand
        decreaseSuitCount(removedCard); // adjust suit count for hand
        return removedCard;             // return the removed card
    }
    
    @Override
    public OrderAddedIterator iterator() {
        return new OrderAddedIterator();
    }
    
    // iterator that returns cards in order of order added
    private class OrderAddedIterator implements Iterator<Card> {

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
            int lowestOrder = 100;     // holds smallest ordering found
            
            // loop through entire hand
            for(int i = 0; i < hand.size(); i++) {

                // get card order
                int cardOrder = hand.get(i).getOrder();
                
                // check if it the smallest order greater than any ordering
                // value currently found
                if(cardOrder < lowestOrder && cardOrder > lastOrderFound) {
                    lowestOrder = cardOrder; // set smallest order
                    smallestOrderIndex = i;    // set index of smallest order                    
                }
            }
            
            // set lastOrderFound so we know what the next smallest cardOrder
            // has to be greater than
            lastOrderFound = lowestOrder;
            index++;
            return hand.get(smallestOrderIndex);
        }
    }
    
    public Iterator<Card> sortedOrderIterator() {
        return new OrderIterator();
    }
    
    // iterator that returns card in order it is sorted into
    private class OrderIterator implements Iterator<Card> {
        
        int index = 0;       
        
        @Override
        public boolean hasNext() {
            return index < hand.size();
        }

        @Override
        public Card next() {
            return hand.get(index++);
        }
        
    }
    
    // returns the max card in a given hand
    public Card getMax() {
        return Card.max(hand);
    }
    
    // returns the highest card of a given suit
    public Card getMax(Suit suit) throws NoValidCardsFoundException {
        // sort into descending order
        this.sortByDescending();
        
        // create iterator
        Iterator it = this.sortedOrderIterator();
        
        // iterate through cards
        while(it.hasNext()) {
            Card nextCard = (Card) it.next();
            
            // if next card matches given suit, it is highest of that suit in
            // the hand, so return it
            if(nextCard.getSuit() == suit)
                return nextCard;
        }
        
        // if no cards of given suit are found, throw exception
        throw new NoValidCardsFoundException("No cards of " + suit + " found");
        
    }
    
    // gets the minimum card by rank of any suit
    public Card getMin() throws NoValidCardsFoundException {
        // sort into ascending order        
        this.sortByRank();
        
        // check for hand being empty
        if(hand.isEmpty())
            throw new NoValidCardsFoundException("Hand is empty");
                
        // return card by first index - will be lowest by rank because of sort
        return hand.get(0);        
    }
    
    
    // sorts hand into ascending order
    public void sort() {
        Collections.sort(hand);
    }
    
    // sorts hand into rank order
    public void sortByRank() {
        Collections.sort(hand, new CompareRank());
    }
    
    
    // sorts hand into descending order
    public void sortByDescending() {
        Collections.sort(hand, new CompareDescending());
    }
    
    // returns the count from the given suit
    public int countSuit(Suit suit) {
        switch(suit) {
            case CLUBS:
                return countClubs;
            case DIAMONDS:
                return countDiamonds;
            case HEARTS:
                return countHearts;
            case SPADES:
                return countSpades;
        }
        
        // default value
        return 0;
    }
    
    // returns the count from a given rank
    public int countRank(Rank rank) {
        int counter = 0;
        
        // iterate through hand
        for(int i = 0; i < hand.size(); i++) {
            
            // check if each cards rank matches given rank
            if(hand.get(i).getRank() == rank)
                counter++;            
        }
        
        // return the counted value
        return counter;
    }
    
    public boolean hasSuit(Suit suit) {
        boolean result = false;
        
        switch(suit) {
            case CLUBS:
                result = countClubs > 0;
                break;
            case DIAMONDS:
                result = countDiamonds > 0;
                break;
            case HEARTS:
                result = countHearts > 0;
                break;
            case SPADES:
                result = countSpades > 0;
                break;
        }
        
        return result;
    }
    
    // returns how many cards are in the hand
    public int size() {
        return hand.size();
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        
        s.append("Hand contains:\n");
        
        for(int i = 0; i < hand.size(); i++) {
            if(i > 9)
                s.append(" " + i + ": ");
            else
                s.append(" " + i + " : ");
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
        Hand testingHand = new Hand();
        testingHand.add(listHand);
        System.out.println(testingHand);
        System.out.println("\n");
        
        // test iterator method
        System.out.println("Test iterator method:");
        OrderAddedIterator handIt = testingHand.iterator();
        while(handIt.hasNext()) {
            Card nextCard = handIt.next();
            System.out.println("Next card from iterator is: " + nextCard + "with order " + nextCard.getOrder());
            
        }
        System.out.println("\n");
        
        // test remove methods
        System.out.println("Test removing Card with Card:");
        Card removeCard1 = new Card(Rank.FOUR, Suit.CLUBS);
        Card removeCard2 = new Card(Rank.JACK, Suit.SPADES);
        boolean resultOfRemoval;
        
        resultOfRemoval = testingHand.remove(removeCard1);
        System.out.println("Result of removal is " + resultOfRemoval);
        System.out.println(testingHand);
       
        resultOfRemoval = testingHand.remove(removeCard2);
        System.out.println("Result of removal is " + resultOfRemoval);
        System.out.println(testingHand);
        
        System.out.println("Test removing Card with Hand");
        Hand removeHand = new Hand();
        removeHand.add(new Card(Rank.TWO, Suit.CLUBS));
        removeHand.add(new Card(Rank.THREE, Suit.CLUBS));
        resultOfRemoval = testingHand.remove(removeHand);
        System.out.println("Result of removal is " + resultOfRemoval);
        System.out.println(testingHand);
        
        System.out.println("Test removing Card with Position");
        Card removedCard = testingHand.remove(1);
        System.out.println(testingHand);
        System.out.println("Removed card was " + removedCard);
        
        // test sorting methods
        System.out.println("Test sorting a Hand (asc): ");
        testingHand.sort();
        System.out.println(testingHand);
        System.out.println("\n");
        
        System.out.println("Test sorting a Hand (by rank):");
        testingHand.sortByRank();
        System.out.println(testingHand);
        
        System.out.println("test sorting a Hand (by descending):");
        testingHand.sortByDescending();
        System.out.println(testingHand);
        
        // test countSuit() method
        System.out.println("Test countSuit method:");
        System.out.println("testingHand has " + 
                            testingHand.countSuit(Suit.HEARTS) + " hearts");
        
        // test countRank() method
        System.out.println("\nTest countRank method:");
        System.out.println("testingHand has " + 
                            testingHand.countRank(Rank.ACE) + " aces");
        
        // test hasSuit() method
        System.out.println("\nTest hasSuit() methods:");
        System.out.println("testingHand has hearts = " + 
                            testingHand.hasSuit(Suit.HEARTS));        
        System.out.println("testingHand has diamonds = " + 
                            testingHand.hasSuit(Suit.DIAMONDS));
        System.out.println("testingHand has spades = " + 
                            testingHand.hasSuit(Suit.SPADES));
        System.out.println("testingHand has clubs = " + 
                            testingHand.hasSuit(Suit.CLUBS));       
        System.out.println("\n");
        
        // testing getMax() method
        System.out.println(testingHand);
        System.out.println("testingHand getMax() is " + testingHand.getMax());
        
   }


    
}
