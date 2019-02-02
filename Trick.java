// Module:      CMP-5015Y  Programming 2
// Assignment:  Coursework 1
// 
// Author:      Matthew Taylor
// 
// Description: Stores data about each trick

package whist;

import java.util.ArrayList; 
import java.util.Comparator;
import whist.Card.*;

public class Trick {

    // use three arraylists to hold all the data for the tricks
    private ArrayList<Card> cards = new ArrayList<>();      // holds cards
    private ArrayList<Integer> players = new ArrayList<>(); // holds player numbers
    private ArrayList<Boolean> lead = new ArrayList<>();    // holds if card is lead card
      
    private final Suit trumpSuit;    
    
    public Trick(Suit trump) {
        trumpSuit = trump;
    }
    
    // getter to return the number of cards currently in the trick
    public int getNumOfCardsInTrick() {
        return cards.size();
    }
    
    public ArrayList<Card> getCards() {
        return cards;
    }
    
    public ArrayList<Integer> getPlayers() {
        return players;
    }
    
    public ArrayList<Boolean> getLeadCard() {
        return lead;
    }
    
    public Suit getLeadSuit() {
        return cards.get(0).getSuit();
    }
        
    // adds a card to the trick
    public void addCard(Card c, int playerNum) {
        cards.add(c);
        players.add(playerNum);

        // if first card added, make it lead card
        if(cards.size() == 1) 
            lead.add(true);
        else    
            lead.add(false);
    }
    
    // determines winning player and returns the player number
    // returns -1 if no players are winning
    public int getWinningPlayer() {
        // initial check to see if any cards have been played
        // return -1 if nothing has been played
        if(cards.size() == 0)
            return -1;
        
        int winningIndex = 0; // holds index of winning card
        Suit leadSuit = cards.get(0).getSuit(); // get suit of lead card
        boolean trumpsFound = false;
        
        // loop through all the cards
        for(int i = 0; i < cards.size(); i++) {
            Card currentlyWinning = cards.get(winningIndex);         
            
            // if suit matches trump suit
            if(cards.get(i).getSuit() == trumpSuit) {
                
                // if currently winning card isn't a trump suit card then
                // set winning card to a trump and set trump found flag
                if(currentlyWinning.getSuit() != trumpSuit) {
                    winningIndex = i;
                    trumpsFound = true;
                }

                // compare ranks
                if(cards.get(i).getRank().compareTo(currentlyWinning.getRank()) > 0) {
                    winningIndex = i;
                }
            }
            // if suit matches lead suit
            else if(cards.get(i).getSuit() == leadSuit && trumpsFound == false) {
                // compare ranks
                if(cards.get(i).getRank().compareTo(currentlyWinning.getRank()) > 0) {
                    winningIndex = i; // if greater, update winning index
                }                    
            }
        }
        
        return players.get(winningIndex);
    }
    
    // returns a card corresponding to the player that played it
    public Card getCardByPlayerNum(int playerNum) {
        int index = -1;
        
        for(int i = 0; i < players.size(); i++) {
            if(playerNum == players.get(i)) 
                index = i;
        }
      
        return cards.get(index);        
    }
    
    // returns the trump suit of the trick
    public Suit getTrumpSuit() {
        return trumpSuit;
    }
    
}
