// Module:      CMP-5015Y  Programming 2
// Assignment:  Coursework 1
// 
// Author:      Matthew Taylor
// 
// Description: Holds methods to add/remove cards and hands, sets game and 
//              strategy

package whist;

public interface Player {
    
    // methods to add/remove cards
    public void addCard(Card c);
    public void removeCard(Card c);
    
    // methods to add/remove the current hand
    public void addHand(Hand h);
    public void removeHand(Hand h);
    
    // method to select the next card to be played
    public Card playCard();
    
    // TODO: strategy selection method?
    
    
}
