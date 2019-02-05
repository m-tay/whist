// Module:      CMP-5015Y  Programming 2
// Assignment:  Coursework 1
// 
// Author:      Matthew Taylor
// 
// Description: Runs a basic simulation, with 4 BasicPlayers

package whist;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import whist.Card.*;

public class BasicWhist {

    // game parameters
    static final int NO_TRICKS = 13;
    static final int WINNING_POINTS = 7;
    
    // stores the players
    Player[] players;
    
    // game state variables
    int roundNumber;
    int handNumber;
    int currentPlayer;
    Suit trumpSuit;
    Trick currentTrick;
    Deck deck; 
    int team1points = 0;
    int team2points = 0;
    
    // constructor
    public BasicWhist(Player[] p) {
        // set up players
        players = p;
        
        // game starts on round 1/hand 1
        roundNumber = 1;
        handNumber = 1;
        
        currentTrick = new Trick(trumpSuit);
        
        // create new deck (constructed shuffled)
        deck = new Deck();
    }
    
    // gets the next player
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    
    // sets the next player
    public void setNextPlayer() {
        currentPlayer++;
        
        if(currentPlayer > 4)
            currentPlayer = 1;
    }
    
    // deals cards to all the players
    public void dealHands() {
        for(int i = 0; i < 52; i++) {   
            players[i%4].dealCard(deck.deal());
        }
    }
    
    // gets card from each player for given trick, starting with first player
    public Trick playTrick() {
        // stores trick to be returned
        currentTrick = new Trick(trumpSuit);
        
        // stores card from player
        Card c;
        
        // calculated player number from ordering
        int pNum;
        
        // go through players in order
        for(int i = 0; i < 4; i++) {            
            // always returns players in order, starting with current player
            pNum = (i + currentPlayer) % 4;
            
            // get card from next player
            c = players[pNum].playCard(currentTrick);
            
            // add found card to trick with player's number
            currentTrick.setCard(c, pNum);
        }
        
        return currentTrick;
    }
    
    // runs a game (13 tricks)
    public void playGame() {

        // initialise game
        deck = new Deck();  // decks are created shuffled
        dealHands();        // deal cards to all players
        handNumber = 1;     // reset handNumber for each round
        
        // randomly determine starting player
        Random rand = new Random();
        currentPlayer = rand.nextInt(4) + 1;  
                        
        // determine trump suit for hand
        trumpSuit = Suit.getRandom();
        
        // for each player
        for (Player player : players) {
            player.resetState();
            player.setTrumps(trumpSuit);    // set trump suit for each player
        }
        
        // loop for playing each hand
        for(int i = 0; i < NO_TRICKS; i++) {
            Trick t = playTrick();
            
            // send completed trick to each player
            for (Player player : players) {
                player.viewTrick(t);
            }
        
            // print output of current game state after each trick 
            printGameState();
            
            // update game state
            handNumber++;
            setNextPlayer();
        }
        
        // determines winner of game and updates points
        int team1 = players[0].getTricksWon() + players[2].getTricksWon();
        int team2 = players[1].getTricksWon() + players[3].getTricksWon();
        int winningTeam;
        
        if(team1 > team2) {
            team1points += team1 - 6;
            winningTeam = 1;
        }
        else {
            team2points += team2 - 6;
            winningTeam = 2;
        }
        
        // reset player's points for round
        for (Player player : players) {
            player.resetTricksWon();
        }
        
        printRoundSummary(winningTeam);
        
        // update game state
        roundNumber++;
        
    }
    
    // runs a match (until either team has 7+ points)
    public void playMatch() {
        // ensure team's point are set to 0
        team1points = 0;
        team2points = 0;
        
        while(team1points < WINNING_POINTS && team2points < WINNING_POINTS) {
            playGame();
        }
        if(team1points>=WINNING_POINTS) {
            System.out.println("Winning team is Team 1 with " + team1points);
            System.out.println("-------------------------------------------------");
        }
        else {
            System.out.println("Winning team is Team 2 with " + team2points);
            System.out.println("-------------------------------------------------");
        }
        
    }
  
    // displays the current game state
    public void printGameState() {
        // flags to hold ends of trick/round
        boolean trickComplete = false; 
        
        if(currentTrick.getNumOfCardsInTrick() == 4) 
            trickComplete = true;
                        
        System.out.println("-------------------------------------------------");
        System.out.println("Round: " + roundNumber + " | Hand: " + handNumber + 
                            " | Trump suit is: " + trumpSuit);
        System.out.println("-------------------------------------------------");
        System.out.println("Tricks won this round:");
        System.out.println("[Team 1] Player 1: " + players[0].getTricksWon());
        System.out.println("[Team 2] Player 2: " + players[1].getTricksWon());
        System.out.println("[Team 1] Player 3: " + players[2].getTricksWon());
        System.out.println("[Team 2] Player 4: " + players[3].getTricksWon());
        System.out.println("-------------------------------------------------");
        System.out.println("Current trick:");
        printCurrentTrick();
        
        // if trick is complete, print out the winner
        if(trickComplete) {
            int winningID = currentTrick.findWinner();
            System.out.println("-------------------------------------------------");
            System.out.println("Player " + (winningID + 1) + " wins the trick!");
            System.out.println("-------------------------------------------------");
        }
        
        System.out.println("\n");
    }
    
    // displays round summary
    public void printRoundSummary(int winningTeam) {
        System.out.println("-------------------------------------------------");
        System.out.println("ROUND " + roundNumber + " SUMMARY");
        System.out.println("-------------------------------------------------");
        System.out.println("Team " + winningTeam + " wins!");
        System.out.println("-------------------------------------------------");
        System.out.println("Team scores are now");
        System.out.println("> Team 1: " + team1points);
        System.out.println("> Team 2: " + team2points);
        System.out.println("-------------------------------------------------");
    }         
    
    // logic to print the current trick
    public void printCurrentTrick() {
        if(currentTrick.getNumOfCardsInTrick() == 0) {
            System.out.println("No cards currently played.");
        }
        else {
            // get trick information from currentTrick
            ArrayList<Card> cards = currentTrick.getCards();
            ArrayList<Integer> players = currentTrick.getPlayers();
            ArrayList<Boolean> lead = currentTrick.getLeadCard();
            
            // loop through cards in trick
            for(int i = 0; i < currentTrick.getNumOfCardsInTrick(); i++) {
                // print player and card
                System.out.print("P" + (players.get(i) + 1) + ": " + 
                                   cards.get(i));
                
                // print if lead card or not
                if(lead.get(i))
                    System.out.print(" (lead)");
                
                // always print new line
                System.out.print("\n");
            }
        }
    }
    
    public static void basicGame() {
        boolean playGame = true;
        
        // loop that runs as long as play another option set to y
        while(playGame) {
            Player[] playerList = { new BasicPlayer(1),
                                    new BasicPlayer(2),
                                    new BasicPlayer(3),
                                    new BasicPlayer(4),
            };

            BasicWhist whist = new BasicWhist(playerList);

            whist.playMatch();
            
            System.out.println("\nPlay another? (y/n)");
            
            // get and process user input
            char c = 'a';
            while(c != 'y' && c!= 'n') {
                Scanner reader = new Scanner(System.in);
                c = reader.next().charAt(0);
                
                if(c == 'n') 
                    playGame = false;                
            }
        }
        
        System.exit(0); // quit game        
    }
    
    public static void humanGame() {
        boolean playGame = true;
        
        // loop that runs as long as play another option set to y
        while(playGame) {
            Player[] playerList = { new BasicPlayer(1),
                                    new BasicPlayer(2),
                                    new BasicPlayer(3),
                                    new BasicPlayer(4),
            };
            
            playerList[0].setStrategy(new HumanStrategy(1));

            BasicWhist whist = new BasicWhist(playerList);

            whist.playMatch();
            
            System.out.println("\nPlay another? (y/n)");
            
            // get and process user input
            char c = 'a';
            while(c != 'y' && c!= 'n') {
                Scanner reader = new Scanner(System.in);
                c = reader.next().charAt(0);
                
                if(c == 'n') 
                    playGame = false;                
            }
        }
        
        System.exit(0); // quit game        
    }
    
    
    public static void advancedGame() {
        boolean playGame = true;
        
        // loop that runs as long as play another option set to y
        while(playGame) {
            Player[] playerList = { new BasicPlayer(1),
                                    new BasicPlayer(2),
                                    new BasicPlayer(3),
                                    new BasicPlayer(4),
            };

            // set players 1 and 3 to use advanced strategy
            playerList[0].setStrategy(new AdvancedStrategy(1));
            playerList[2].setStrategy(new AdvancedStrategy(3));
            
            BasicWhist whist = new BasicWhist(playerList);

            whist.playMatch();
 
            System.out.println("\nPlay another? (y/n)");
            
            // get and process user input
            char c = 'a';
            while(c != 'y' && c!= 'n') {
                Scanner reader = new Scanner(System.in);
                c = reader.next().charAt(0);
                
                if(c == 'n') 
                    playGame = false;                
            }
        }
                
        System.exit(0); // quit game        
    }

    
    
    
    
    // test harness
    public static void main(String args[]) {
//        // test construcutor / game initialisation        
//        Player[] playerList = { new BasicPlayer(1),
//                                new BasicPlayer(2),
//                                new BasicPlayer(3),
//                                new BasicPlayer(4),
//        };
//        
//        BasicWhist whist = new BasicWhist(playerList);
        //whist.trumpSuit = Suit.DIAMONDS;
        //System.out.println("Random starting player is " + whist.getCurrentPlayer());
        
//        // test game state printing
//        whist.printGameState();
//        System.out.println("Currently winning player is " + whist.currentTrick.findWinner());
//        
//        // test adding cards to tricks and printing game state
//        Card card = new Card(Rank.SEVEN, Suit.HEARTS);        
//        whist.currentTrick.setCard(card, 1);        
//        whist.printGameState();
//        System.out.println("Currently winning player is " + whist.currentTrick.findWinner());
//        
//        Card card2 = new Card(Rank.NINE, Suit.DIAMONDS);        
//        whist.currentTrick.setCard(card2, 2);     
//        whist.printGameState();
//        System.out.println("Currently winning player is " + whist.currentTrick.findWinner());
//        
//        Card card3 = new Card(Rank.TEN, Suit.DIAMONDS);        
//        whist.currentTrick.setCard(card3, 3);     
//        whist.printGameState();
//        System.out.println("Currently winning player is " + whist.currentTrick.findWinner());
//        
//        Card card4 = new Card(Rank.EIGHT, Suit.HEARTS);        
//        whist.currentTrick.setCard(card4, 4);     
//        whist.printGameState();
//
//        System.out.println("Winning player is " + whist.currentTrick.findWinner());
//
//        // test dealHands() method
//        whist.dealHands();
//        
//        // output result of dealHands() test
//        System.out.println(whist.players[0]);
//        System.out.println(whist.players[1]);
//        System.out.println(whist.players[2]);
//        System.out.println(whist.players[3]);
//        
//        // test playTrick() method
//        whist.printGameState();
//        Trick testTrick = whist.playTrick();
//        System.out.println(testTrick);
//        whist.printGameState();
//
//        // test playGame() implementation
//        whist.playGame();
//
//        // test playMatch() implementation
//        whist.playMatch();
//        
//        // test basicGame()
//        basicGame();
//
//        // test humanGame()
//        humanGame();
//        
        // test advancedGame()
        advancedGame();
                
    }
    
}
