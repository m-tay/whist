// Module:      CMP-5015Y  Programming 2
// Assignment:  Coursework 1
// 
// Author:      Matthew Taylor
// 
// Description: Implements the methods to run a basic AI player

package whist;

public class BasicPlayer implements Player {

    // holds the player number of this player
    private int playerNumber = 5;
    
    // holds the strategy - for basic players, use basic strategy
    private Strategy strat = new BasicStrategy(playerNumber);
    
    // holds the player's hand
    private Hand hand = new Hand();
    
    // stores the number of won tricks
    private int tricksWon = 0;
    
    // constructor
    public BasicPlayer(int playerNum) {
        playerNumber = playerNum;
    }
    
    // gets the number of won tricks
    public int getTricksWon() {
        return tricksWon;
    }
    
    // increases the number of won tricks
    public void increaseTricksWon() {
        tricksWon++;
    }
    
    @Override
    public void addCard(Card c) {
        hand.add(c);
    }

    @Override
    public void removeCard(Card c) {
        hand.remove(c);
    }

    @Override
    public void addHand(Hand h) {
        hand.add(h);
    }

    @Override
    public void removeHand(Hand h) {
        hand.remove(h);
    }

    @Override
    public Card playCard() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
