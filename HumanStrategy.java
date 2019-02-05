// Module:      CMP-5015Y  Programming 2
// Assignment:  Coursework 1
// 
// Author:      Matthew Taylor
// 
// Description: Implements a human player deciding the strategy

package whist;

import java.util.InputMismatchException;
import java.util.Scanner;
import whist.Card.Suit;

public class HumanStrategy implements Strategy {

    // holds player number
    private final int playerNumber;
    
    // constructor
    public HumanStrategy(int playerNum) {
        playerNumber = playerNum;
    }
    
    @Override
    public Card chooseCard(Hand h, Trick t) {
        // variables to hold details of card to return
        Card chosenCard;
        int index;
        
        // sort cards to make them easier to read
        h.sortByRank();
                
        System.out.println("-------------------------------------------------");
        System.out.println("Human player " + playerNumber + " - it's your turn");
        System.out.println("-------------------------------------------------");
        System.out.println("Trump suit is " + t.getTrumpSuit());
        System.out.println("Current trick is:");
        System.out.println(t);
        System.out.println("-------------------------------------------------");
        System.out.println(h);
        index = getInput(h);
        chosenCard = h.remove(index);
        
        return chosenCard;
    }

    @Override
    public void updateData(Trick completedTrick) {
        // no state to update in humanStrategy
    }
    
    // asks for a checks user input is valid for given hand
    private int getInput(Hand h) {
        
        int input = -1;
        // get and check input is valid
        while(input < 0 || input > (h.size() - 1)) {
           
            System.out.println("Enter the card number you wish to play:");
            Scanner reader = new Scanner(System.in);
            
            // check for errors in input
            try {
                input = reader.nextInt();                   
            }
            catch(InputMismatchException ex) {
                System.out.println("Error: enter a number only!");
            }
            
        }
        
        // if while loop has finished, card must be valid
        return input;        
    }
        
    
    @Override    
    public void resetState() {
        // no state is stored to reset
    }
        
    
    // test harness
    public static void main(String[] args) {
        
        // set up testing hand
        Hand testHand = new Hand();
        testHand.add(new Card(Card.Rank.TWO, Card.Suit.CLUBS));
        testHand.add(new Card(Card.Rank.ACE, Card.Suit.CLUBS));
        testHand.add(new Card(Card.Rank.THREE, Card.Suit.CLUBS));
        testHand.add(new Card(Card.Rank.ACE, Card.Suit.SPADES));
        testHand.add(new Card(Card.Rank.NINE, Card.Suit.SPADES));
        
        // set up testing trick with trump suit as diamonds
        Trick testTrick = new Trick(Card.Suit.DIAMONDS);
        
        // set up basic strategy object for testing
        HumanStrategy strat = new HumanStrategy(1);
        
        // test outputting card from chooseCard()
        Card testOutputCard;
        testOutputCard = strat.chooseCard(testHand, testTrick);
        System.out.println("Returned card was " + testOutputCard);
        
        
    }
    
}
