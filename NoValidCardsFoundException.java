// Module:      CMP-5015Y  Programming 2
// Assignment:  Coursework 1
// 
// Author:      Matthew Taylor
// 
// Description: Custom exception that is thrown when no valid cards are found

package whist;

public class NoValidCardsFoundException extends Exception {
    
    public NoValidCardsFoundException(String message) {
        super(message);
    }    
}
