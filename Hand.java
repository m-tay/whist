package whist;

import java.io.Serializable;
import java.util.ArrayList;
import whist.Card.*;

public class Hand implements Serializable {
    
    private static final long serialVersionUID = 300L; 
    
    // holds all the cards in the hand
    private ArrayList<Card> hand;
    
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
        
        // create initial suit count
        hand.forEach((Card i) -> {
            switch (i.getSuit()) {
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
        });        
    }
    
    // constructor that takes another Hand
    public Hand(Hand otherHand) {
        // shallow copy cards from another hand
        hand = otherHand.hand;
        
        // create initial suit count
        hand.forEach((Card i) -> {
            switch (i.getSuit()) {
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
        }); 
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
                    System.out.println("aceValues is " + (aceValues[j % 2]));
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
        
        
   }
    
}
