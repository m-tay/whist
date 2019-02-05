// Module:      CMP-5015Y  Programming 2
// Assignment:  Coursework 1
// 
// Author:      Matthew Taylor
// 
// Description: Implements the methods to run a basic AI player

package whist;

import whist.Card.Suit;

public class BasicPlayer implements Player {

    // holds the player number of this player
    private int playerID = 5;
    
    // holds the strategy - for basic players, use basic strategy
    private Strategy strat = new BasicStrategy(playerID);
    
    // holds the player's hand
    private Hand hand = new Hand();
    
    // stores the number of won tricks
    private int tricksWon = 0;
    
    // stores the current trump suit
    private Suit trumpSuit;

    // constructor
    public BasicPlayer(int id) {
        playerID = id;
    }
    

    @Override
    // gets the number of won tricks
    public int getTricksWon() {
        return tricksWon;
    }
    
    @Override
    // resets the number of tricks won
    public void resetTricksWon() {
        tricksWon = 0;
    }    
    
    @Override
    // removes cards from hand
    public void removeCard(Card c) {
        hand.remove(c);
    }

    @Override
    // adds a hand to the player's hand
    public void addHand(Hand h) {
        hand.add(h);
    }

    @Override
    // removes a hand from the player's hand
    public void removeHand(Hand h) {
        hand.remove(h);
    }

    @Override
    // uses the current trick and the player's current strategy, returns the
    // card the strategy has chosen
    public Card playCard(Trick t) {
        Card c = strat.chooseCard(hand, t);
        hand.remove(c);
        return c;
    }

    @Override
    // adds a dealt card to the player's hand
    public void dealCard(Card c) {
        hand.add(c);
    }

    @Override
    // sets the player's strategy
    public void setStrategy(Strategy s) {
        strat = s;
    }

    @Override
    // stores a completed trick given by the game
    public void viewTrick(Trick t) {
        // send completed tricks to the strategy
        strat.updateData(t);
        
        // check if this player has won the trick
        if(t.findWinner() == (playerID - 1))
            tricksWon++;
    }

    @Override
    // sets the trump suit, so the player can use it
    public void setTrumps(Card.Suit s) {
        trumpSuit = s;
    }

    @Override
    // gets the players unique ID (1 to 4)
    public int getID() {
        return playerID;
    }
    

    @Override
    // resets a player's strategy's state
    public void resetState() {
        strat.resetState();
    }
    
    @Override
    // returns details about the current player's state
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append("BasicPlayer | ID: " + getID() + "\n");
        s.append("> Tricks won: " + tricksWon + "\n");
        s.append(hand + "\n\n");
        
        return s.toString();
    }


}
