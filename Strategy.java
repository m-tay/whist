// Module:      CMP-5015Y  Programming 2
// Assignment:  Coursework 1
// 
// Author:      Matthew Taylor
// 
// Description: Contains the methods needed to implement various strategies

package whist;

public interface Strategy {

    // chooses the next card to be played
    // needs the current trick to make informed decision
    public Card chooseCard(Hand h, Trick t);
    
    // method to update the strategy's data model of the game
    public void updateData(Trick completedTrick);
}
