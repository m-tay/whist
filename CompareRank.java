// Module:      CMP-5015Y  Programming 2
// Assignment:  Coursework 1
// 
// Author:      Matthew Taylor
// 
// Description: Comparator class for Cards, in ascending order of rank only

package whist;

import java.util.Comparator;

public class CompareRank implements Comparator<Card> {

    @Override
    public int compare(Card a, Card b) {
        // sorts by rank in ascending order
        return a.getRank().getValue() - b.getRank().getValue();
    }
    
}
