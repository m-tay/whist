// Module:      CMP-5015Y  Programming 2
// Assignment:  Coursework 1
// 
// Author:      Matthew Taylor
// 
// Description: Runs a basic simulation, with 4 BasicPlayers

package whist;

import java.util.ArrayList;
import java.util.Random;
import whist.Card.*;

public class BasicWhist {

    // holds the 4 BasicPlayers
    BasicPlayer player1;
    BasicPlayer player2;
    BasicPlayer player3;
    BasicPlayer player4;

    // game state variables
    int roundNumber;
    int currentPlayer;
    Suit trumpSuit;
    Trick currentTrick;
    
    // constructor
    public BasicWhist() {
        // set up the game variables
        this.player1 = new BasicPlayer(1);
        this.player2 = new BasicPlayer(2);
        this.player3 = new BasicPlayer(3);
        this.player4 = new BasicPlayer(4);
        
        // game starts on round 1
        roundNumber = 1;
        
        // randomly determine starting player
        Random rand = new Random();
        currentPlayer = rand.nextInt(4) + 1;  
        
        // determine trump suit for hand
        trumpSuit = Suit.getRandom();
        
        currentTrick = new Trick(trumpSuit);
    }
    
    // gets the next player
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    
    // displays the current game state
    public void printGameState() {
        System.out.println("-------------------------------------------------");
        System.out.println("Round: " + roundNumber + " | Trump suit is: " + 
                            trumpSuit);
        System.out.println("-------------------------------------------------");
        System.out.println("Current number of tricks:");
        System.out.println("[Team 1] Player 1: " + player1.getTricksWon());
        System.out.println("[Team 2] Player 2: " + player2.getTricksWon());
        System.out.println("[Team 1] Player 3: " + player3.getTricksWon());
        System.out.println("[Team 2] Player 4: " + player4.getTricksWon());
        System.out.println("-------------------------------------------------");
        System.out.println("Current trick:");
        printCurrentTrick();
        System.out.println("\n\n");
    }
    
    // logic to print the current trick
    public void printCurrentTrick() {
        if(currentTrick.getNumOfCardsInTrick() == 0) {
            System.out.println("No cards currently played.");
        }
        else {
            // get trick information from currentTrick
            ArrayList<Card> cards = currentTrick.getCards();
            ArrayList<Integer> players = currentTrick.getPlayers();
            ArrayList<Boolean> lead = currentTrick.getLeadCard();
            
            // loop through cards in trick
            for(int i = 0; i < currentTrick.getNumOfCardsInTrick(); i++) {
                // print player and card
                System.out.print("P" + players.get(i) + ": " + 
                                   cards.get(i));
                
                // print if lead card or not
                if(lead.get(i))
                    System.out.print(" (lead)");
                
                // always print new line
                System.out.print("\n");
            }
        }
    }
    
    // test harness
    public static void main(String args[]) {
        // test construcutor / game initialisation        
        BasicWhist whist = new BasicWhist();
        System.out.println("Random starting player is " + whist.getCurrentPlayer());
        
        // test game state printing
        whist.printGameState();
        System.out.println("Currently winning player is " + whist.currentTrick.getWinningPlayer());
        
        // test adding cards to tricks and printing game state
        Card card = new Card(Rank.SEVEN, Suit.HEARTS);        
        whist.currentTrick.addCard(card, 1);        
        whist.printGameState();
        System.out.println("Currently winning player is " + whist.currentTrick.getWinningPlayer());
        
        Card card2 = new Card(Rank.NINE, Suit.DIAMONDS);        
        whist.currentTrick.addCard(card2, 2);     
        whist.printGameState();
        System.out.println("Currently winning player is " + whist.currentTrick.getWinningPlayer());
        
        Card card3 = new Card(Rank.TEN, Suit.DIAMONDS);        
        whist.currentTrick.addCard(card3, 3);     
        whist.printGameState();
        System.out.println("Currently winning player is " + whist.currentTrick.getWinningPlayer());
        
        Card card4 = new Card(Rank.EIGHT, Suit.HEARTS);        
        whist.currentTrick.addCard(card4, 4);     
        whist.printGameState();

        System.out.println("Winning player is " + whist.currentTrick.getWinningPlayer());
        
    }
    
}
