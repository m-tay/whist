// Module:      CMP-5015Y  Programming 2
// Assignment:  Coursework 1
// 
// Author:      Matthew Taylor
// 
// Description: Implements the advanced strategy for AI players

package whist;

import java.util.*;
import whist.Card.Suit;

public class AdvancedStrategy implements Strategy {
    
    // holds the current player number
    private int playerNumber;
    private int partnerNumber;
    private int oppoNumber[] = new int[2];
    
    // holds game state information, passed via updateData()
    // Hand objects to store the played cards of each player
    // position in array matches internal player IDs (0 to 3)
    private ArrayList<Hand> playerHands = new ArrayList();
    
    // arraylist of hashmaps, which holds of each player (position 0 to 3) has
    // any of the suits left in their hand
    private ArrayList<HashMap<Card.Suit, Boolean>> playerHasSuit = 
                                                                new ArrayList();
    
    
    // constructor
    public AdvancedStrategy(int playerNum) {
        // constructor just calls reset function, so we can easily reset the
        // object to it's starting state outside of constructor
        reset(playerNum);
    }
    
    // sets strategy's initial state (done outside constructor so it can be
    // reset easily each round)
    private void reset(int playerNum) {
                playerNumber = playerNum; // get the player number
        
        // set the partner number
        switch(playerNumber) {
                case 1:
                    partnerNumber = 3;
                    oppoNumber[0] = 4;
                    oppoNumber[1] = 2;                    
                    break;
                case 2:
                    partnerNumber = 4;
                    oppoNumber[0] = 1;
                    oppoNumber[1] = 3;      
                    break;
                case 3:
                    partnerNumber = 1;
                    oppoNumber[0] = 2;
                    oppoNumber[1] = 4;      
                    break;
                case 4:
                    partnerNumber = 2;
                    oppoNumber[0] = 1;
                    oppoNumber[1] = 3;      
                    break;
                default:
                    // unreachable
                    partnerNumber = -1;
                    break;
        }   
        
        // initialise the playerHands list to have 4 hands (0 to 3) for all
        // players in the game
        playerHands.add(new Hand());
        playerHands.add(new Hand());
        playerHands.add(new Hand());
        playerHands.add(new Hand());
        
        // initialise the playerHasSuit arraylist
        // create hashmap of all suits being true
        HashMap<Card.Suit, Boolean> map0 = new HashMap();   
        HashMap<Card.Suit, Boolean> map1 = new HashMap(); 
        HashMap<Card.Suit, Boolean> map2 = new HashMap(); 
        HashMap<Card.Suit, Boolean> map3 = new HashMap(); 
        playerHasSuit.add(map0);
        playerHasSuit.add(map1);
        playerHasSuit.add(map2);
        playerHasSuit.add(map3);
        
        // populate playerHasSuit arraylist hashmaps with all trues
        for(int i = 0; i < playerHasSuit.size(); i++) {
            playerHasSuit.get(i).put(Suit.CLUBS,    Boolean.TRUE);
            playerHasSuit.get(i).put(Suit.DIAMONDS, Boolean.TRUE);
            playerHasSuit.get(i).put(Suit.HEARTS,   Boolean.TRUE);
            playerHasSuit.get(i).put(Suit.SPADES,   Boolean.TRUE);
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
    
    // tests whether an opponent has a suit
    public boolean opponentsHaveSuit(Suit suit) {
        // loop through opponent number array
        for(int i = 0; i < oppoNumber.length; i++) {
            // if opponent has that suit
            if(playerHasSuit.get(oppoNumber[i] - 1).get(suit)) {
                return true;
            }            
        }
        
        return false;
    }
    
    @Override
    public Card chooseCard(Hand hand, Trick currentTrick) {
        hand.sortByRank(); // ensure hand is sorted
        Card chosenCard = new Card(); // holds the chosen card to return
                
        // case 1: first player
        // detect if first player (currentTrick returns -1)
        if(currentTrick.findWinner() == -1) {
            
            // check if we think opponents have trump cards and then play
            // highest trump if so
            if(opponentsHaveSuit(currentTrick.getTrumpSuit())) {
                // and this player has trump suit
                if(hand.hasSuit(currentTrick.getTrumpSuit())) {
                    
                    // play highest trumps
                    System.out.println("P" + playerNumber + " thinks opponents have trumps so is playing them");
                    return hand.getMax(currentTrick.getTrumpSuit()); 

                }
                
            }
            
            
            // check if partner can trump
            if(playerHasSuit.get(partnerNumber - 1).get(currentTrick.getTrumpSuit())) {
                
                // and check if partner is out of any non-trump suits
                ArrayList<Suit> nonTrumpSuits;
                nonTrumpSuits = getNonTrumpSuits(currentTrick.getTrumpSuit());
                
                // create hand to store smallest cards that partner does not 
                // have in same suit - if we have multiple suits our partner
                // does not have then we want to select the smallest
                Hand minCards = new Hand();
                
                // loop through non trump suits
                for(int i = 0; i < nonTrumpSuits.size(); i++) {
                    
                    // if partner does not have a non trump suit...
                    if (!playerHasSuit.get(partnerNumber - 1).get(nonTrumpSuits.get(i))) {
                        
                        // and player does have a card of that suit
                        if(hand.hasSuit(nonTrumpSuits.get(i))) {
                            // add to temp hand
                            minCards.add(hand.getMin(nonTrumpSuits.get(i)));    
                        }
                    }
                }
                
                // if any minCards are present, return it
                if(minCards.size() > 0) {                                 
                    chosenCard = minCards.getMin();
                    System.out.println("P" + playerNumber+ " I think my partner is out of a suit so I'll play +" + chosenCard);
                }
                // else, return max
                else {
                    return hand.getMax();
                }
            }
            // otherwise just play highest card
            else {
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
       }
        
       // case 2: partner winning the trick
       // compare if trick's currently winning player matches partner number
        else if(currentTrick.findWinner() == partnerNumber) {

            // setup to check logic for part 2:
            // hand is already sorted, so iterator's next will return min card
            Iterator<Card> it = hand.sortedOrderIterator();
                      
            // follow suit if possible, so get partner's suit
            Card partnersCard = currentTrick.getCard(partnerNumber);
                      
            // actual case 2 logic:
            // if player's hand has a suit matching partners card
            if(hand.countSuit(partnersCard.getSuit()) > 0) {
                // loop through iterator until card found
                boolean cardNotFound = true; // runs the while loop
               
                // can use while loop because iterator is guaranteed to have
                // the suit we are looking for
                while(cardNotFound) {
                    Card nextCard = it.next(); // get the next card
                    
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
                int index = 0;
                
                it = hand.sortedOrderIterator();
                
                // get an arraylist of non trump suits
                ArrayList<Suit> nonTrumpSuits;
                nonTrumpSuits = getNonTrumpSuits(currentTrick.getTrumpSuit());
               
                // check if we have any cards of non-trump suits
                if(nonTrumpSuits.size() > 0) {
                    
                    // loop through until the next card to play has been found
                    while(cardNotFound && index < hand.size()) {
                        // loop through iterator
                        while(it.hasNext()) {
                            Card nextCard = it.next();

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
                        
                        // increment index so if only trumps remain, we check
                        // all cards in hand then exit the while loop
                        index++;
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
            // if player can follow suit...
            if(hand.hasSuit(currentTrick.getLeadSuit())) {
                // if player has a card in suit that can win...
                Card maxInSuit = hand.getMax(currentTrick.getLeadSuit());
                if(maxInSuit != null) {
                    return maxInSuit;
                }
                else {
                    return hand.getMin(currentTrick.getLeadSuit());
                }
            }
            
            // if player cannot follow suit, trump if possible
            else {
                
                // check if player can trump
                if(hand.hasSuit(currentTrick.getTrumpSuit())) {
                    return hand.getMax(currentTrick.getTrumpSuit());
                }
                // else, if player cannot trump
                else {
                    // just return minimum card
                    return hand.getMin();
                }
            }
        }
        
    return chosenCard;    
    }

    @Override
    // updates the advanced strategy's model of the game
    public void updateData(Trick completedTrick) {

        // loop through completed trick 
        for(int i = 0; i < completedTrick.getNumOfCardsInTrick(); i++) {
            
            // save each card player has played
            // get player number from i'th position in trick
            int player = completedTrick.getPlayerAt(i);
            
            // add card from completed trick in position of player number
            playerHands.get(player).add(completedTrick.getCardAt(i));
            
            // check if any players could not follow lead suit - this means
            // they do not have those suits
            Suit leadSuit = completedTrick.getLeadSuit(); // get lead suit
            
            // loop through all non-lead cards (start loop from 1, not 0)
            for(int j = 1; j < completedTrick.getNumOfCardsInTrick(); j++) {
                
                // check if card does not match lead suit
                if(completedTrick.getCardAt(i).getSuit() != leadSuit) {
                    // if does not match lead suit, player must be out of them
                    int pNum = completedTrick.getPlayerAt(i);
                    
                    // update hashmap in arraylist of position of player (0-3)
                    // to set the suit to false
                    playerHasSuit.get(pNum).put(leadSuit, false);
                    
                    System.out.println("P" + playerNumber + ": player " + pNum + " is out of " + leadSuit);
                }
            }
            
            
        }
    }
    
    @Override    
    // resets the state - used after a round is over
    public void resetState() {
        this.reset(playerNumber);
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
        AdvancedStrategy strat = new AdvancedStrategy(1);
        
        // test case 1 of strategy
        Card chooseCardTest1 = strat.chooseCard(testHand, testTrick);
        System.out.println("Choose card (Case 1) test: " + chooseCardTest1);
        
        // set up for test of case 2
        Card testCard1 = new Card(Card.Rank.SIX, Card.Suit.SPADES);
        Card testCard2 = new Card(Card.Rank.FIVE, Card.Suit.SPADES);
        Card testCard3 = new Card(Card.Rank.TEN, Card.Suit.SPADES);
        
        // add cards to trick for player 3 and 4 (so player 3 is the strat's
        // partner and it checks that card for what to do next)
        testTrick.setCard(testCard1, 3);
        testTrick.setCard(testCard2, 4);
        
        // test case 2 
        Card chooseCardTest2 = strat.chooseCard(testHand, testTrick);
        System.out.println("Choose card (Case 2) test: " + chooseCardTest2);
        
        // test case 3
        // add a new winning card by player 2 - chooseCard() should now try to
        // beat it
        testTrick.setCard(testCard3, 2);
        Card chooseCardTest3 = strat.chooseCard(testHand, testTrick);
        System.out.println("Choose card (Case 3) test: " + chooseCardTest3);
        
        // set up for further testing of case 3
        // (partner not winning + player can trump)
        Card testCard4 = new Card(Card.Rank.THREE, Card.Suit.DIAMONDS);
        Card testCard5 = new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS);
        Trick testTrick2 = new Trick(Card.Suit.CLUBS);
        
        testTrick2.setCard(testCard4, 2);
        testTrick2.setCard(testCard5, 3);
        
        Card chooseCardTest4 = strat.chooseCard(testHand, testTrick2);
        System.out.println("Choose card (Case 3) test: " + chooseCardTest4);
        
        // test updateData() method
        // start by completing the trick
        Card testCard6 = new Card(Card.Rank.SEVEN, Card.Suit.DIAMONDS);
        Card testCard7 = new Card(Card.Rank.NINE, Card.Suit.DIAMONDS);
        
        testTrick2.setCard(testCard6, 0);
        testTrick2.setCard(testCard7, 1);
        
        // run updateData() and print out the playerHands that's been updated
        strat.updateData(testTrick2);
        System.out.println(strat.playerHands);
        
    }
    
}
