// Module:      CMP-5015Y  Programming 2
// Assignment:  Coursework 1
// 
// Author:      Matthew Taylor
// 
// Description: Holds methods to add/remove cards and hands, sets game and 
//              strategy

package whist;

import whist.Card.Suit;

public interface Player {
    
    // adds card to players hand
    void dealCard(Card c);
    
    // sets strategy of player
    void setStrategy(Strategy s);
    
    // determines which of players cards to play
    Card playCard(Trick t);
    
    void viewTrick(Trick t);
    
    // sets the trumps value so the player knows what the trump suit is
    void setTrumps(Suit s);
    
    int getID();
    
    // gets the number of won tricks
    int getTricksWon();    
        
    // methods to add/remove cards
    public void removeCard(Card c);
    
    // methods to add/remove the current hand
    public void addHand(Hand h);
    public void removeHand(Hand h);
    
}
