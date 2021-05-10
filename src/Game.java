import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;


public class Game {
    public static void startGame() {
    	// Create a new instance of board to be used in gameplay
        Board b = new Board();
        // Players will be stored in an array list of object player
        ArrayList<Player> playerNames = new ArrayList<Player>();
        
        // Random to seed our dailyDoubles
        Random r = new Random();
        // This corresponds to an array position in board that will
        // convert that clue into a dailyDouble
        int dailyDouble= r.nextInt((29-0)+1)+0;
        
        // Scanner used to enter player data
        Scanner myScanner = new Scanner(System.in);
        // Player names are entered by the user/users for the 3 players
        System.out.println("Enter Player 1 Name: ");
        String name = myScanner.nextLine();
        Player p1 = new Player(name);
        System.out.println("Enter Player 2 Name: ");
        name = myScanner.nextLine();
        Player p2 = new Player(name);
        System.out.println("Enter Player 3 Name: ");
        name = myScanner.nextLine();
        Player p3 = new Player(name);
        // Player object created above are placed in our player array
        playerNames.add(p1);
        playerNames.add(p2);
        playerNames.add(p3);
        
        // importBoard takes a Boolean value because to determine if it 
        // is Double Jeopardy! or not false imports round 1 true imports
        // Double Jeopardy!
        b.importBoard(false);
        // creating a Daily Double question with our random seed
        b.makeDailyDouble(dailyDouble);
        // CreateBoard takes in playerNames array because those player
        // objects are used for scoreboard displayed under a standard
        //Jeopardy Board
        b.createBoard(playerNames);
        // Playerturn starts the gameplay loop with the current players
        // and board
        playerTurn(playerNames, b);
        
        // reseeding dailyDouble
        dailyDouble= r.nextInt((29-0)+1)+0;
        
        // Same as about importing our game board making daily double
        // keeping the current players because this is double jeopardy 
        // and reentering gameplay loop
        b.importBoard(true);
        b.makeDailyDouble(dailyDouble);
        b.createBoard(playerNames);
        playerTurn(playerNames,b);
        
        // Final Jeopardy
        playerNames.get(0).addToBank(16800);
        playerNames.get(1).addToBank(-1200);
        playerNames.get(2).addToBank(10000);
        b.finalJeopardy(playerNames);
    }
    public static void playerTurn(ArrayList<Player> playerNames, Board b) {
        Scanner myScanner = new Scanner(System.in);

        int n = 0;
        // While game has clues keep playing
        while (b.isEmpty() == false) {
            // Current n is active player. Kind of like a base case to set 
        	//status set status in the case everyone got a question wrong.
            playerNames.get(n).setActiveStatus();
            while(playerNames.get(n).getActiveStatus() == true) {
                // Display splash menu to players
                System.out.println(playerNames.get(n).getName() + "'s Turn:");
                System.out.println("Enter a category to answer: ");
                // take in player choice or cat
                String cat = myScanner.nextLine();
                System.out.println("Enter the clues value: ");
                while(!myScanner.hasNextInt()) {
                	myScanner.next();
                	System.out.println("Please enter a proper value(ie 200): ");
                	
                }
                // take in player chosen val
                int val = myScanner.nextInt();
                // make cat upper and trim leading or trailing whitespace
                cat = cat.toUpperCase();
                cat = cat.trim();

                if(b.isDailyDouble(cat, val) == false) {
                // Current active player will now be answering the clue
                	playerNames.get(n).addToBank(b.displayClue(cat, val));
                }else if(b.isDailyDouble(cat,val)==true){
                	val = b.dailyDoubleValue(cat, val, playerNames.get(n));
                	playerNames.get(n).addToBank(b.displayClue(cat,val));
                	b.removeDailyDouble(cat,val);
                	val=0;
                }
                
                // i while loop condition
                int i = 0;
                // used to control player array pos in the steal process
                int steal = n;
                
                // if the previous player got it wrong the questions should still
                // be active if this is the case we should give other players the
                // chance to steal
                while(b.isActiveClue(cat, val)==true&&i<=1&&b.isDailyDouble(cat,val)==false){
                    // Next player
                    steal++;
                    // if we are at risk of exceeding the player array do
                    // this one
                    if(steal==3){
                        steal=0;
                        System.out.println(playerNames.get(steal).getName() + "'s steal:");
                        playerNames.get(steal).addToBank(b.displayClue(cat, val));
                        // If player steals set them to active
                        if(playerNames.get(steal).getActiveStatus()==true) {
                        	n = steal;
                        	steal = 5;
                        	i=5;
                        }
                        i++;
                        
                    // otherwise if players active status is false and we haven't given
                    // a chance for everyone to steal give someone a chance to steal
                    }else if(playerNames.get(steal).getActiveStatus()==false&&steal<3){
                        System.out.println(playerNames.get(steal).getName() + "'s Turn to steal:");
                        playerNames.get(steal).addToBank(b.displayClue(cat, val));
                        // if player steals set them to active player
                        if(playerNames.get(steal).getActiveStatus()==true) {
                        	n = steal;
                        	steal = 5;
                        	i=5;
                        }
                        i++;
                    }
                   
                }

                // If after the true is over the question is still active
                // everyone has gotten it wrong remove from play
                if(b.isActiveClue(cat,val)==true){
                    b.removeClue(cat,val);
                    if(n==2) {
                    	n=0;
                    }else if(n<3) {
                    	n++;
                    }
                }
                // reseting scanner because I got annoyed by leading an trailing whitespace
                myScanner = new Scanner(System.in);
                // Create a board with clue removed and scores updated
                if(b.isEmpty()==false) {
                	b.createBoard(playerNames);
                }else {
                	break;
                }
            }
        }
    }

    // User is asked why they would like to do
    public static void startScreen() {
    	// run = 1 so this will loop until player ends the game
    	int run = 1;
    	while(run == 1){
    		//Scanner for user input for this menu
    		Scanner myScanner = new Scanner(System.in);
        	System.out.println("Welcome to Jeopardy");
        	System.out.println("[1] Rules");
        	System.out.println("[2] Start Game");
        	System.out.println("[3] Quit Game");
        	// making sure a user puts in an int and handling it 
        	// if not
        	while(!myScanner.hasNextInt()) {
        		myScanner.next();
        		System.out.println("Please enter 1, 2, or 3:");
        	}
        	int x = myScanner.nextInt();
        	// case switch form menu
        	switch(x) {
            	case 1:
                	displayRules();
                	break;
            	case 2:
                	startGame();
                	break;
            	case 3:
                	System.exit(0);
                	break;

        	}
    	}
    }
    
    // Jeopardy Rules
    public static void displayRules(){
        System.out.println("__________________________________Jeopardy Rules___________________________________");
        System.out.println(" Type the name of category you want to answer");
        System.out.println(" Type the value of question you want to answer");
        System.out.println(" Each question is worth the specified value displayed on the board");
        System.out.println(" Type your answer and hit enter.");
        System.out.println(" If a question is answered correctly, that player receives the points ");
        System.out.println(" If a player answered wrong or runs out of time the next player has a chance to steal");
        System.out.println(" The game is over when all the clues have been answered for.");
        System.out.println(" The player with the most points wins the game.");
    }
    // main
    public static void main(String[] args) {
        startScreen();
    }
}