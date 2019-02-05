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
    private int playerNumber;       // holds the current player number
    private int partnerNumber;      // holds the players partners number
    private int oppoNumber[] = new int[2];// stores the opponents of this player
    
    // game state variables, updated via updateData()
    // stores remaining cards in game in a Deck object
    private Deck cardsRemaining = new Deck();
    
    // arraylist of hashmaps, which holds of each player (position 0 to 3) has
    // any of the suits left in their hand
    private ArrayList<HashMap<Card.Suit, Boolean>> playerHasSuit = 
                                                               new ArrayList();
    
    // counters for how many of each suit has been played (by players other
    // than current player
    private int clubsPlayed;
    private int heartsPlayed;
    private int diamondsPlayed;
    private int spadesPlayed;
    
    // maintain a copy of the player's hand in strategy for evaluating whether
    // all cards of a suit have been played or not
    private Hand playersHand = new Hand();
    
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
        
        // initialise Deck so it is full of cards
        cardsRemaining.newDeck();
        
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
        
        // reset the counters for total of each suit played
        clubsPlayed     = 0;
        heartsPlayed    = 0;
        diamondsPlayed  = 0;
        spadesPlayed    = 0;
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
    
    // returns the shortest suit in a players hand
    public Suit getShortestSuit(Hand playersHand) {
        Suit shortest = Suit.CLUBS;
        int count = 13;
        
        if(playersHand.countSuit(Suit.CLUBS) < count && playersHand.countSuit(Suit.CLUBS) > 0)
            
            shortest = Suit.CLUBS;
        if(playersHand.countSuit(Suit.DIAMONDS) < count && playersHand.countSuit(Suit.CLUBS) > 0) 
            shortest = Suit.DIAMONDS;
        if(playersHand.countSuit(Suit.SPADES) < count && playersHand.countSuit(Suit.CLUBS) > 0) 
            shortest = Suit.SPADES;
        if(playersHand.countSuit(Suit.HEARTS) < count && playersHand.countSuit(Suit.CLUBS) > 0) 
            shortest = Suit.HEARTS;
        
        return shortest;
    }
    
    @Override
    public Card chooseCard(Hand hand, Trick currentTrick) {
        // update strategy's copy of hand
        playersHand = hand;
        hand.sortByRank(); // ensure hand is sorted
        Card chosenCard = new Card(); // holds the chosen card to return
                
        // case 1: first player
        // detect if first player (currentTrick returns -1)
        if(currentTrick.findWinner() == -1) {
            
            // if our shortest suit is < 3, try to get rid of it quickly
            Suit shortestSuit = getShortestSuit(playersHand);
            if(playersHand.countSuit(shortestSuit) < 3 && playersHand.countSuit(shortestSuit) > 0) {
                return hand.getMin(shortestSuit);
            }
            
            // check if we think opponents have trump cards and then play
            // highest trump if so
            if(opponentsHaveSuit(currentTrick.getTrumpSuit())) {
                // and this player has trump suit
                if(hand.hasSuit(currentTrick.getTrumpSuit())) {
                    
                    // play highest trumps
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
        
        // make sure players cards are removed from the remaining cards
        Iterator it = playersHand.sortedOrderIterator();
        while(it.hasNext()) {
            cardsRemaining.remove((Card)it.next());
        }
        
        // update strategys copy of hand with completed tricks
        // subtract 1 from playerNumber because getCard expects 0 - 3 type
        playersHand.remove(completedTrick.getCard(playerNumber - 1));
        
        // loop through completed trick 
        for(int i = 0; i < completedTrick.getNumOfCardsInTrick(); i++) {
            
            // remove played card from cardsRemaining
            cardsRemaining.remove(completedTrick.getCardAt(i));
            
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
                }
            }
            
            // update counters of suits played, to keep track of whether all
            // cards of a suit played
            switch(completedTrick.getCardAt(i).getSuit().toString()) {
                case "HEARTS":
                    heartsPlayed++;
                    break;
                case "SPADES":
                    spadesPlayed++;
                    break;
                case "DIAMONDS":
                    diamondsPlayed++;
                    break;
                case "CLUBS":
                    clubsPlayed++;
                    break;
            }

            // check if any of them are 13 (all cards of a suit played) then
            // mark all players as being out of that suit
            if(heartsPlayed == (13 - playersHand.countSuit(Suit.HEARTS))) {
                playerHasSuit.forEach((playerHasSuitE) -> {
                    playerHasSuitE.put(Suit.HEARTS, Boolean.FALSE);
                });
            }
            if(diamondsPlayed == (13 - playersHand.countSuit(Suit.DIAMONDS))) {
                playerHasSuit.forEach((playerHasSuitE) -> {
                    playerHasSuitE.put(Suit.DIAMONDS, Boolean.FALSE);
                });
            }
            if(spadesPlayed == (13 - playersHand.countSuit(Suit.SPADES))) {
                playerHasSuit.forEach((playerHasSuitE) -> {
                    playerHasSuitE.put(Suit.SPADES, Boolean.FALSE);
                });
            }
            if(clubsPlayed == (13 - playersHand.countSuit(Suit.CLUBS))) {
                playerHasSuit.forEach((playerHasSuitE) -> {
                    playerHasSuitE.put(Suit.CLUBS, Boolean.FALSE);
                });
            
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
        System.out.println(strat.cardsRemaining);
        
    }
    
}
