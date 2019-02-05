// Module:      CMP-5015Y  Programming 2
// Assignment:  Coursework 1
// 
// Author:      Matthew Taylor
// 
// Description: Stores data about each trick

package whist;

import java.util.ArrayList; 
import whist.Card.*;

public class Trick {

    // use three arraylists to hold all the data for the tricks
    private ArrayList<Card> cards = new ArrayList<>();      // holds cards
    private ArrayList<Integer> players = new ArrayList<>(); // holds player numbers
    private ArrayList<Boolean> lead = new ArrayList<>();    // holds if card is lead card
    
    // stores the trump suit of the trick
    private static Suit trumps;    
    
    public Trick(Suit trump) {
        trumps = trump;
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
    
    // gets the player number (0 to 3) at a position in the trick
    public int getPlayerAt(int i) {
        // check for exceptions
        if(i < 0 || i > players.size())
            throw new ArrayIndexOutOfBoundsException("No such player exists");
        
        return players.get(i);
    }
    
    // gets the card number (0 to 3) at a position in the trick
    public Card getCardAt(int i) {
        // check for exceptions
        if(i < 0 || i > cards.size())
            throw new ArrayIndexOutOfBoundsException("No such card exists");
        
        return cards.get(i);        
    }
    
    // gets the lead indicator (0 to 3) at a position in the trick
    public boolean getLeadAt(int i) {
        // check for exceptions
        if(i < 0 || i > lead.size())
            throw new ArrayIndexOutOfBoundsException("No such element exists");
        
        return lead.get(i); 
    }
    
    public Suit getLeadSuit() {
        return cards.get(0).getSuit();
    }
        
    // adds a card to the trick
    public void setCard(Card c, int playerNum) {
        cards.add(c);
        players.add(playerNum);

        // if first card added, make it lead card
        if(cards.size() == 1) 
            lead.add(true);
        else    
            lead.add(false);
    }
    
    // determines winning player and returns the player number
    // returns -1 if no players are winning (no cards played into trick)
    public int findWinner() {
        // initial check to see if any cards have been played
        // return -1 if nothing has been played
        if(cards.isEmpty())
            return -1;
        
        int winningIndex = 0; // holds index of winning card
        Suit leadSuit = cards.get(0).getSuit(); // get suit of lead card
        boolean trumpsFound = false;
        
        // loop through all the cards
        for(int i = 0; i < cards.size(); i++) {
            Card currentlyWinning = cards.get(winningIndex);         
            
            // if suit matches trump suit
            if(cards.get(i).getSuit() == trumps) {
                
                // if currently winning card isn't a trump suit card then
                // set winning card to a trump and set trump found flag
                if(currentlyWinning.getSuit() != trumps) {
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
    public Card getCard(int playerNum) {
        int index = -1;
        
        for(int i = 0; i < players.size(); i++) {
            if(playerNum == players.get(i)) 
                index = i;
        }
      
        return cards.get(index);        
    }
    
    // returns the trump suit of the trick
    public Suit getTrumpSuit() {
        return trumps;
    }
    
    @Override
    // returns basic details of trick
    public String toString() {
        StringBuilder s = new StringBuilder();
        
        // check if trick is currently empty
        if(cards.isEmpty()) {
            s.append("No cards currently played into trick.\n");
        }
        else {
            // loop through trick
            for(int i = 0; i < cards.size(); i++) {
                s.append("Card " + (i+1) + " by Player " + (players.get(i) + 1));
                s.append(": " + cards.get(i) + "\n");
            }
        }
        
        return s.toString();
    }
    
    // test harness
    public static void main(String[] args) {
        
        Trick t = new Trick(Suit.HEARTS); // create test trick
        
        // add test elements to trick
        t.setCard(new Card(Rank.SIX,   Suit.HEARTS),  0);
        t.setCard(new Card(Rank.SEVEN, Suit.HEARTS),  1);
        t.setCard(new Card(Rank.FIVE,  Suit.HEARTS),  2);
        t.setCard(new Card(Rank.EIGHT, Suit.HEARTS),  3);

        // get getCard() 
        System.out.print("getCard(2) test: ");
        System.out.println(t.getCard(2));
        
        // test getCards
        System.out.print("getCards() test: ");
        System.out.println(t.getCards());
        
        // test getPlayers
        System.out.print("getPlayers() test: ");
        System.out.println(t.getPlayers());
        
        // test getLead
        System.out.print("getLead() test: ");
        System.out.println(t.getLeadCard());
        
        // test getLeadSuit
        System.out.print("getLeadSuit() test: ");
        System.out.println(t.getLeadSuit());
        
        // test numCardsInTrick
        System.out.println("Number of cards in trick is " + t.getNumOfCardsInTrick());
        
        // test toString()
        System.out.println("Testing toString(): ");
        System.out.println(t);
        
    }
    
}
