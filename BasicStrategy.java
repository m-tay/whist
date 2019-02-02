// Module:      CMP-5015Y  Programming 2
// Assignment:  Coursework 1
// 
// Author:      Matthew Taylor
// 
// Description: Implements the basic strategy for AI players

package whist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import whist.Card.Suit;
import whist.Hand.*;

public class BasicStrategy implements Strategy {
    
    // holds the current player number
    private final int playerNumber;
    private final int partnerNumber;
    
    // constructor
    public BasicStrategy(int playerNum) {
        playerNumber = playerNum; // get the player number
        
        // set the partner number
        switch(playerNumber) {
                case 1:
                    partnerNumber = 3;
                    break;
                case 2:
                    partnerNumber = 4;
                    break;
                case 3:
                    partnerNumber = 1;
                    break;
                case 4:
                    partnerNumber = 2;
                    break;
                default:
                    // unreachable
                    partnerNumber = -1;
                    break;
        }                    
    }
    
    // given a suit, return an arraylist of all the suits that are not that suit
    public static ArrayList<Suit> getNonTrumpSuits(Suit suit) {
        // populate suitList with all suits
        ArrayList<Suit> suitList = new ArrayList<>(
                                            Arrays.asList(Suit.CLUBS,
                                                          Suit.DIAMONDS,
                                                          Suit.HEARTS,
                                                          Suit.SPADES));
        
        // remove the trump suit
        suitList.remove(suit);
                
        return suitList;
    }
    
    @Override
    public Card chooseCard(Hand hand, Trick currentTrick) {
        hand.sortByRank(); // ensure hand is sorted
        Card chosenCard = new Card(); // holds the chosen card to return
                
        // case 1: first player
        // detect if first player (currentTrick returns -1)
        if(currentTrick.getWinningPlayer() == -1) {

            // get the max card, add to arraylist
            ArrayList<Card> maxCardList = new ArrayList();
            Card maxCard = hand.getMax();
            maxCardList.add(maxCard);
                        
            // check if there's multiple highest cards 
            Iterator it = hand.sortedOrderIterator();
            while(it.hasNext()) {
                Card nextCard = (Card)it.next();
                if(nextCard != maxCard) {
                    if(nextCard.getRank() == maxCard.getRank()) {
                        maxCardList.add(nextCard);
                    }
                }
            }
            
            // randomly select one of the max cards                   
            Random rand = new Random();
            int max = maxCardList.size();
            chosenCard = maxCardList.get(rand.nextInt(max));              
       }
        
       // case 2: partner winning the trick
       // compare if trick's currently winning player matches partner number
        else if(currentTrick.getWinningPlayer() == partnerNumber) {
          
           // setup to check logic for part 2:
           // hand is already sorted, so iterator's next will return min card
           Iterator it = hand.sortedOrderIterator();
                      
           // follow suit if possible, so get partner's suit
           Card partnersCard = currentTrick.getCardByPlayerNum(partnerNumber);
                      
           // actual case 2 logic:
           // if player's hand has a suit matching partners card
           if(hand.countSuit(partnersCard.getSuit()) > 0) {
                // loop through iterator until card found
                boolean cardNotFound = true; // runs the while loop
               
                // can use while loop because iterator is guaranteed to have
                // the suit we are looking for
                while(cardNotFound) {
                    Card nextCard = (Card)it.next(); // get the next card
                    
                    // if the next card's suit matches partners suit card
                    if(nextCard.getSuit() == partnersCard.getSuit()) {
                        chosenCard = nextCard;  // get the card to return
                        cardNotFound = false;   // exit the whole loop
                    }                   
                }
            }
            // else, player does not have card matching partners card's suit, so
            // play highest non-trump suit (may still have to play a trump suit
            // card though)
            else {
                // bool to detect if card hasnt been found
                boolean cardNotFound = true;
               
                // get an arraylist of non trump suits
                ArrayList<Suit> nonTrumpSuits;
                nonTrumpSuits = getNonTrumpSuits(currentTrick.getTrumpSuit());
               
                // check if we have any cards of non-trump suits
                if(nonTrumpSuits.size() > 0) {
                    
                    // loop through until the next card to play has been found
                    while(cardNotFound) {
                        // loop through iterator
                        while(it.hasNext()) {
                            // TODO remove this casting
                            Card nextCard = (Card)it.next();

                            // loop through non-trump suits, if a non-trump card
                            // is found, then select the next 
                            for(int i = 0; i < nonTrumpSuits.size(); i++) {

                                // check if nextCard matches non trumps
                                if(nextCard.getSuit() == nonTrumpSuits.get(i)) {
                                    chosenCard = nextCard;
                                    cardNotFound = false;
                                }
                            }
                        }
                    }
                }
                // else no non-trump cards found, so have to play a trump
                else {
                    // set up iterator
                    Iterator iter = hand.sortedOrderIterator();
                    
                    // no non trumps exist so next() will just return lowest 
                    // trump card
                    chosenCard = (Card) iter.next();
                }               
            }           
        }
        
        // case 3: partner not winning or has yet to play
        else {
            // get the current best card in the trick
            int winningPlayer = currentTrick.getWinningPlayer();
            Card winningCard = currentTrick.getCardByPlayerNum(winningPlayer);            
            
            // if player can follow suit...
            if(hand.hasSuit(currentTrick.getLeadSuit())) {
                // ... and can beat current winning card with highest card
                
                // sort hand into descending order
                hand.sortByDescending();
                Iterator it = hand.sortedOrderIterator();

                // iterator definitely had the card we're looking for, so we 
                // can just iterate through it to find the max card of the suit
                // we know we have
                while(true) {
                    chosenCard = (Card) it.next();
                    if(chosenCard.getSuit() == currentTrick.getLeadSuit()) {
                        return chosenCard;
                    }
                } 
            }
            // if player cannot follow suit, trump if possible
            else {
                // check if player can trump
                if(hand.hasSuit(currentTrick.getTrumpSuit())) {
                    
                }
                // else, if player cannot trump
                else {
                    
                }
                
            }
            
            
        }
        
       
       

       
       
       
       
    return chosenCard;
    
    }

    @Override
    public void updateData(Trick completedTrick) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // testing harnesses
    public static void main(String args[]) {
        
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
        BasicStrategy strat = new BasicStrategy(1);
        
        // test case 1 of strategy
        Card chooseCardTest1 = new Card();
        chooseCardTest1 = strat.chooseCard(testHand, testTrick);
        System.out.println("Choose card (Case 1) test: " + chooseCardTest1);
        
        // set up for test of case 2
        Card testCard1 = new Card(Card.Rank.SIX, Card.Suit.SPADES);
        Card testCard2 = new Card(Card.Rank.FIVE, Card.Suit.SPADES);
        Card testCard3 = new Card(Card.Rank.TEN, Card.Suit.SPADES);
        Card testCard4 = new Card(Card.Rank.SIX, Card.Suit.HEARTS);
        
        // add cards to trick for player 3 and 4 (so player 3 is the strat's
        // partner and it checks that card for what to do next)
        testTrick.addCard(testCard1, 3);
        testTrick.addCard(testCard2, 4);
        
        // test case 2 
        Card chooseCardTest2 = new Card();
        chooseCardTest2 = strat.chooseCard(testHand, testTrick);
        System.out.println("Choose card (Case 2) test: " + chooseCardTest2);
        
        // test case 3
        // add a new winning card by player 2 - chooseCard() should now try to
        // beat it
        testTrick.addCard(testCard3, 2);
        Card chooseCardTest3 = new Card();
        chooseCardTest3 = strat.chooseCard(testHand, testTrick);
        System.out.println("Choose card (Case 3) test: " + chooseCardTest3);
        
        
    }
    
}
