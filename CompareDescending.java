// Module:      CMP-5015Y  Programming 2
// Assignment:  Coursework 1
// 
// Author:      Matthew Taylor
// 
// Description: Comparator class for Cards, sorts by ranks, then suits (with
//              suits always sorted in Club, Diamond, Heart, Spade order)
package whist;

import java.util.Comparator;
import whist.Card;

public class CompareDescending implements Comparator<Card> {

    @Override
    public int compare(Card a, Card b) {
        
        // if not the same rank, return comparison
        if(a.getRank() != b.getRank()) {
            // sorts by rank in descending order
            return b.getRank().getValue() - a.getRank().getValue();
        }
        // if they are the same rank, sort by suit (in same sort order)
        else {
            return a.getSuit().getValue() - b.getSuit().getValue();
        }

    }
}
