import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;


public class Game {
    public static void startGame() {
        Board b = new Board();
        ArrayList<Player> playerNames = new ArrayList<Player>();

        Scanner myScanner = new Scanner(System.in);
        System.out.println("Enter Player 1 Name: ");
        String name = myScanner.nextLine();
        Player p1 = new Player(name);
        System.out.println("Enter Player 2 Name: ");
        name = myScanner.nextLine();
        Player p2 = new Player(name);
        System.out.println("Enter Player 3 Name: ");
        name = myScanner.nextLine();
        Player p3 = new Player(name);
        playerNames.add(p1);
        playerNames.add(p2);
        playerNames.add(p3);

        b.testArrays();
        b.createBoard();
        playerTurn(playerNames, b);
        
        // while board is still full
            // each player chooses a category and turn
    }
    
    public static void playerTurn(ArrayList<Player> playerNames, Board b) {
        Scanner myScanner = new Scanner(System.in);
        Random rand = new Random();
        int n = rand.nextInt(3);

        while (b.isEmpty() == false) {
            playerNames.get(n).setActiveStatus();
            System.out.println(playerNames.get(n).getName()+"'s Turn:");
            System.out.println("Enter a category to answer: ");
            String cat = myScanner.nextLine();
            System.out.println("Enter the clues value: ");
            int val = myScanner.nextInt();
            cat = cat.toUpperCase();
            cat = cat.trim();
            b.displayClue(cat,val);
            myScanner.nextLine();
            b.createBoard();
        }
    }

    public static void startScreen() {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Welcome to Jeopardy!");
        System.out.println("Rules [1]");
        System.out.println("Start Game [2]");
        System.out.println("Quit [3]");
        System.out.println(" ");
        System.out.println(" Press [1] , [2] , or [3]");
        int x = myScanner.nextInt();
        switch(x) {
            case 1:
                displayRules();
            case 2:
                startGame();
            case 3:
                System.exit(0);

        }
    }

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

    public static void main(String[] args) {
        startScreen();
    }
}
