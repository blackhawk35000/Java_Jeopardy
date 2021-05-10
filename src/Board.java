import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class Board {
	// board datatypes
    String category;
    Scanner myScanner = new Scanner(System.in);
    ArrayList<Clue> clueList = new ArrayList<>();
    ArrayList<String> categoryList = new ArrayList<>();
    boolean isDoubleJeopardy;
    int dailyDoubleOrigin;
    
    // create board uses data imported in importBoard and playerNames to
    // diplay a Jeopardy board and a running score tally to the user 
    public void createBoard(ArrayList<Player> playerNames) {
    	// array iterators
        int i = 0;
        int pos = 0;
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
        // if this is not the Double Jeopardy! round(This is done so boards look properly formated)
        if(this.isDoubleJeopardy == false) {
        	// This outputs the Category row of the Jeopardy board
        	if (pos == 0) {
        		while (i != 6) {
        			// pipe to enclose board
        			System.out.print("|");
        			// display category
        			System.out.printf("%21s", categoryList.get(i).trim());
        			if (i == 5) {
        				// closing pipe for last value
        				System.out.print("|");
        			}
        			// iterating
        			i++;
        			pos++;
        		}
        	}
        	// endline reset i
        	System.out.println();
        	i = 0;

        	// this is output for each row going forward ie 200 row to 1000
        	if (pos != 0) {
        		while (i != 30) {
        			// Output each clue if they are active
                	if (clueList.get(i).getStatus() == false) {
                		System.out.print("|");
                		System.out.printf("%21s", clueList.get(i).getValue());
                    // output blank space if not
                	} else if(clueList.get(i).getStatus()==true) {
                		System.out.print("|");
                		System.out.printf("%21s", "");
                    
                	}

                	i++;
                	// endline and put ending pipe
                	if (i != 0 && i % 6 == 0) {
                		System.out.print("|");
                		System.out.println();
                	}

        		}

        	}
        	// same process as above but formated for Double Jeopardy.
        }else if(this.isDoubleJeopardy == true) {
        	// reset lists for new board
        	
        	if (pos == 0) {
        		while (i != 6) {
        			System.out.print("|");
        			System.out.printf("%30s", categoryList.get(i).trim());
        			if (i == 5) {
        				System.out.print("|");
        			}
        			i++;
        			pos++;
        		}
        	}
        	System.out.println();
        	i = 0;


        	if (pos != 0) {
        		while (i != 30) {

                	if (clueList.get(i).getStatus() == false) {
                		System.out.print("|");
                		System.out.printf("%30s", clueList.get(i).getValue());
                    
                	} else if(clueList.get(i).getStatus()==true) {
                		System.out.print("|");
                		System.out.printf("%30s", "");
                    
                	}

                	i++;
                	if (i != 0 && i % 6 == 0) {
                		System.out.print("|");
                		System.out.println();
                	}

        		}

        	}
        }
        // current scoreboard for players
        System.out.println("Current Scores: ");
        System.out.print(playerNames.get(0).getName()+": "+playerNames.get(0).getBankBalance()+" ");
        System.out.print(playerNames.get(1).getName()+": "+playerNames.get(1).getBankBalance()+" ");
        System.out.print(playerNames.get(2).getName()+": "+playerNames.get(2).getBankBalance()+" "+"\n");
    }
    
    // Display a clue to the active player assuming they put in the proper information for an active clue
    public int displayClue(String Category, int Val){
        int i=0;
        while(i!=clueList.size()) {
            // clue has to have correct value
            if (clueList.get(i).getValue() == Val ) {
                // clue has to be of the right cat and be active
                if (clueList.get(i).getCategory().toUpperCase().trim().equals(Category)==true && clueList.get(i).getStatus()==false) {
                    Scanner ans = new Scanner(System.in);
                    System.out.println(clueList.get(i).getClue());
                    System.out.println("What is your answer?");
                    // take in players answer to clue
                    String answer = ans.nextLine();
                    // if the clue is correct remove from play and give points
                    if(clueList.get(i).getQuestion().toUpperCase().trim().equals(answer.toUpperCase().trim())){
                        clueList.get(i).removeFromPlay();
                        return clueList.get(i).getValue();
                        // if it is not subtract points
                    }else{
                        return 0-clueList.get(i).getValue();
                    }
                    // if the clue is not active award no points and inform the user.
                }else if(clueList.get(i).getCategory().trim().equals(Category) && clueList.get(i).getStatus() == true) {
                    System.out.println("This Clue has already been used! You are sneaky!");
                    return 0;
                }
            }
            i++;
        }
        return 0;
    }
    
    // Method to check if a clue is active 
    public boolean isActiveClue(String Category, int value) {
    	// iterator
        int i = 0;
        // Check the whole arraylist until you find a clue or the data given doesn't exist
        while (i != clueList.size()) {
        	// if the value is correct and it's not dailyDouble
            if (clueList.get(i).getValue() == value&&clueList.get(i).dailyDoubleStatus()!=true) {
            	// if the category exists and it's active status return true
                if (clueList.get(i).getCategory().toUpperCase().trim().equals(Category) && clueList.get(i).getStatus() == false) {
                    return true;
                }
                // if it exists but it is not active return false
                if (clueList.get(i).getCategory().toUpperCase().trim().equals(Category) && clueList.get(i).getStatus() == true) {
                    return false;
                }
            }
            //iterate
            i++;
        }
        //return false if the clue doesn't exist
        return false;
    }
    
    // method that removes clue from active status
    public int removeClue(String Category, int value){
    	// iterator
        int i = 0;
        // check the whole arraylist until you find a clue or data given doesn't exist
        while (i != clueList.size()) {
        	// if value exists and it's not dailydouble
            if (clueList.get(i).getValue() == value && clueList.get(i).dailyDoubleStatus()!=true) {
            	// if category is the same and value
                if (clueList.get(i).getCategory().toUpperCase().trim().equals(Category)) {
                	// remove the clue from play exit
                    clueList.get(i).removeFromPlay();
                    return 0;
                }
                // for daily double
            }else if(clueList.get(i).getValue() == value && clueList.get(i).dailyDoubleStatus()==true) {
            	// same as above
            	if (clueList.get(i).getCategory().toUpperCase().trim().equals(Category)) {
                    clueList.get(i).removeFromPlay();
                    return 0;
                }
            }
            i++;
        }
        return 0;
    }
    // imports data from a CSV file to form a board
    public void importBoard(boolean doubleStatus){
    		// if it is not Double Jeopardy
        	if(doubleStatus == false) {
        		// data imported from round1.csv
        		String pathname = "src\\Round1.csv";
        		// try catch for file i/o
        		try {
        			// fileReader using round1.csv
        			FileReader game = new FileReader(pathname);
        			// Scanner using fileReader
        			Scanner filein = new Scanner(game);
        			// using the two delimiters of , and end of line
        			filein.useDelimiter(",|\n");
        			// iterator just to switch from importing strings for category
        			// to importing clues
        			int i = 0;
        			//while file has data to import
        			while(filein.hasNext()) {
        				// if I doesn't=6
        				if(i!=6) {
        					// importing category strings and putting them into arrayList
        					String win = filein.next().trim();
        					categoryList.add(win);
            	   			i++;
        				}else {
        					// importing clues 3 strings 1 int and using clue constructor to make
        					// clue objects to put into our arrayList of Clue objects
        					String clue = filein.next();
                        	String question = filein.next();
                        	String category = filein.next();
                        	int value = Integer.parseInt(filein.next().trim());
                        	Clue clue1 = new Clue(clue, category,value, question);
                        	clueList.add(clue1);
        				}
            	   	
        			}
        			// close the file
        			filein.close();	 
        			//catch file exceptions
        		}catch(FileNotFoundException ex){
        			System.out.println("File not found");
        		}
        		// if it is double Jeopary import round2.csv
        	}else {
        		// rest arrays
        		categoryList = new ArrayList<String>();
            	clueList=new ArrayList<Clue>();
        		// pathname for csv
        		String pathname = "src\\Round2.csv";
        		try {
        			//filereader using this pathname
        			FileReader game = new FileReader(pathname);
        			// scanner to work with file reader
        			Scanner filein = new Scanner(game);
        			// delim for scanner
        			filein.useDelimiter(",|\n");
        			// iterator
        			int i = 0;
        			// while file has data
        			while(filein.hasNext()) {
        				// importing categorys
        				if(i!=6) {
        					String win = filein.next().trim();
        					categoryList.add(win);
            	   			i++;
            	   			// importing clues
        				}else {
        					String clue = filein.next();
                        	String question = filein.next();
                        	String category = filein.next();
                        	int value = Integer.parseInt(filein.next().trim());
                        	Clue clue1 = new Clue(clue, category,value, question);
                        	clueList.add(clue1);
        				}
            	   	
        			}
        			// close file
        			filein.close();	 
        			// exception handling
        		}catch(FileNotFoundException ex){
        			System.out.println("File not found");
        		}
        	}
        	//set double jeopardy status to true or false
        this.isDoubleJeopardy = doubleStatus;
              
    }


    // method checking to see if a board is empty
    public boolean isEmpty(){
    	// iterator
        int i=0;
        // tally of active clues
        int numbActive_clues=0;
        // run through the clue list till the end
        while(i!=clueList.size()){
        	// if there are active clues return false we are not
        	// in an empty board situation
            if (numbActive_clues>0){
                return false;// should break if not problem
             // otherwise check if there are active clues   
            }else if(clueList.get(i).getStatus()==false) {
                    numbActive_clues++;
                }
            i++;
            }
        
        
        // if there are no active clues return true we are empty
        //otherwise return false
        if(numbActive_clues==0){
            return true;
        }else{
            return false;
        }
    }
    
    // makes the clue in a given position in the array a Daily Double
    public void makeDailyDouble(int pos) {
    	// clue in the pos is make a daily double using method in clue
    	clueList.get(pos).makeDailyDouble();
    	// daily double origin value is used for a specific glitch that showed up
    	// when a user bet exactly the value of another clue in the category essential
    	// once a daily double is eliminated the value is reset to it's original value
    	// to prevent this quick work around
    	this.dailyDoubleOrigin = clueList.get(pos).getValue();
    }
    
    // isDailyDouble return true or false if the clue is a Daily Double
    public boolean isDailyDouble(String Category, int Val) {
    	// iterator for entire arraylist
    	int i=0;
    	// while there is still values to check
         while(i!=clueList.size()) {
             // clue has to have correct value
             if (clueList.get(i).getValue() == Val ) {
                 // clue has to be of the right cat and be active and be a daily double for this too be true
                 if (clueList.get(i).getCategory().toUpperCase().trim().equals(Category)==true && clueList.get(i).getStatus()==false && clueList.get(i).dailyDoubleStatus()==true) {
                	 return true;
                     // don't try and use a clue that has been used before that isn't nice
                 }else if(clueList.get(i).getCategory().trim().equals(Category) && clueList.get(i).getStatus() == true) {
                     System.out.println("This Clue has already been used! You are sneaky!");
                     return false;
                     // return false if the clue is active and exists but is not a daily double
                 }else if(clueList.get(i).getCategory().toUpperCase().trim().equals(Category)==true && clueList.get(i).getStatus()==false && clueList.get(i).dailyDoubleStatus()==false) {
                	 return false;
                 }
             }
             i++;
         }
         // false if the clue just doesn't exist
         return false;
     }

        
        
    
    	
    // Daily Double value this method lets the user that got the daily double bet an amount to wager.
    public int dailyDoubleValue(String Category, int Val, Player player) {
    	// max wager possible
    	int max;
    	// minimum wager didn't know this was a thing before now but you have to wager at least 5 dollars.
    	int min = 5;
    	// if this is not doubleJeopardy
    	if(this.isDoubleJeopardy == false) {
    		// another special rule you can bet a max of 1000 even if you have less in the bank or even a negative
    		// bank
    		if(player.getBankBalance()<1000) {
    			max = 1000;
    		// if you have more then 1000 in the bank your bank balance in now the max	
    		}else {
    			max = player.getBankBalance();
    		}
    		// for double jeopardy
    	}else {
    		// max is now 2000 if you have less or negative balance
    		if(player.getBankBalance()<2000) {
    			max = 2000;
    		// otherwise your bank balance is the max	
    		}else {
    			max = player.getBankBalance();
    		}
    	}
    	// terator 
    	int i=0;
        while(i!=clueList.size()) {
            // clue has to have correct value
            if (clueList.get(i).getValue() == Val ) {
                // clue has to be of the right cat and is a daily double
                if (clueList.get(i).getCategory().toUpperCase().trim().equals(Category)==true) {
                	// user informed about the daily double and allowed to wager 
                	System.out.println("DAILY DOUBLE!");
                	System.out.println("You can wager up to "+max + "! The minium wager is " + min);
                	System.out.println("What is your wager?:");
                	Scanner wager= new Scanner(System.in);
                	// making sure the user doesn't put in anything other then an int
                	while(!wager.hasNextInt()) {
                		wager.next();
                		System.out.println("Please enter a correct wager:");
                	}
                	int wage = wager.nextInt();
                	// making sure the user put in a proper int value
                	while(min==5) {
                		if(wage<=max && wage>=min) {
                			clueList.get(i).setValue(wage);
                			return wage;
                		}else {
                			System.out.println("You can wager up to "+max + "! The minium wager is " + min);
                        	System.out.println("What is your wager?:");
                        	while(!wager.hasNextInt()) {
                        		wager.next();
                        		System.out.println("Please enter a correct wager:");
                        	}
                        	wage = wager.nextInt();
                		}
                	}
                }
            }
            i++;
        }
        return 0;
    	
    }
    
    // method to removeDailyDouble
    public void removeDailyDouble(String cat, int val) {
    	//iterator
    	int i=0;
    	// run throught the whole clue list
        while(i!=clueList.size()) {
            // clue has to have correct value
            if (clueList.get(i).getValue() == val ) {
                // clue has to be of the right cat and be active and a dailydouble
                if (clueList.get(i).getCategory().toUpperCase().trim().equals(cat)==true && clueList.get(i).getStatus()==false && clueList.get(i).dailyDoubleStatus()==true) {
                	// remove the daily double from active status and reset it's value
               	 	clueList.get(i).removeFromPlay();
               	 	clueList.get(i).setValue(this.dailyDoubleOrigin);
                    
                }
                
            }
            i++;
        }
    }
    
    // final Jeopardy method
    public void finalJeopardy(ArrayList<Player> playerNames) {
    	// import clue from csv
    	String pathname = "src\\Final_Jeopardy.csv";
    	// reset arraylist for final jeopardy
    	clueList=new ArrayList<Clue>();
    	try {
    		// importing final jeopardy from file
    		FileReader game = new FileReader(pathname);
    		Scanner filein = new Scanner(game);
    		filein.useDelimiter(",|\n");
    		String clue = filein.next();
    		String question = filein.next();
    		String category = filein.next();
    		int value = Integer.parseInt(filein.next().trim());
    		Clue clue1 = new Clue(clue, category,value, question);
    		clueList.add(clue1);
    	}catch(FileNotFoundException ex){
			System.out.println("File not found");
		}
    	// showing current scores
    	 System.out.println("Current Scores: ");
         System.out.print(playerNames.get(0).getName()+": "+playerNames.get(0).getBankBalance()+" ");
         System.out.print(playerNames.get(1).getName()+": "+playerNames.get(1).getBankBalance()+" ");
         System.out.print(playerNames.get(2).getName()+": "+playerNames.get(2).getBankBalance()+" "+"\n");
         
         //announce final jeopardy
         System.out.println("FINAL JEOPARDY!");
         

         Scanner val = new Scanner(System.in);
         // This loop gets every user to wager an amount for final jeopardy using their bank balances
         // as max if the balance is less then 1 you cannot enter final jeopardy.
         for(int i=0; i!=3; i++) {
        	 // max is your bank balance
        	 int max = playerNames.get(i).getBankBalance();
        	 // if your max isn't at least a dollar no final Jeopardy for you
        	 if(max>0) {
        		 // wager set to negative so the while runs
        		 int wager = -1;
        		 // if the user bets less then zero or more then max keep asking for a value
        		 while(wager<0||wager>playerNames.get(i).getBankBalance()) {
        			 // ask the user for a value
        			 System.out.print(playerNames.get(i).getName()+" Enter a wager up to "+ playerNames.get(i).getBankBalance()+": ");
        			 // making sure they put in an int value
        			 while(!val.hasNextInt()) {
        				 val.next();
        				 System.out.println("Please enter a proper Wager: ");
        			 }
        		 	 wager = val.nextInt();
        		 }
        		 // final jeopardy wager for that user set
        		 playerNames.get(i).setFinalJeopardy(wager);
        		}else {
        			// otherwise if they have a 0 bank or negative bank they are not involved in final jeopardy
        		 System.out.println(playerNames.get(i).getName()+" will not be in final Jeopardy!");
        		 
        		}
        	 
         
    	

         } 
         
         // show question and get answer from every user in final jeopary
         for(int i=0; i<3; i++) {
        	 if(playerNames.get(i).finalWager()>0) {
        		 System.out.println(clueList.get(0).getClue());
        	 	System.out.println(playerNames.get(i).getName()+" what is your answer?: ");
        	 	val = new Scanner(System.in);
        	 	int test = playerNames.get(i).finalWager();
        	 	String ans = val.nextLine();
        	 	// if the player is right
        	 	if(clueList.get(0).getQuestion().toUpperCase().trim().equals(ans.toUpperCase().trim())){
        		 	playerNames.get(i).addToBank(playerNames.get(i).finalWager());

        		 	// if it is not subtract points
             	}else{
            	 	playerNames.get(i).addToBank(0-playerNames.get(i).finalWager());
             	}
        	 	
        	 }
        	 i++;
         }
         // Display final scores to users
         System.out.println("Current Scores: ");
         System.out.print(playerNames.get(0).getName()+": "+playerNames.get(0).getBankBalance()+" ");
         System.out.print(playerNames.get(1).getName()+": "+playerNames.get(1).getBankBalance()+" ");
         System.out.print(playerNames.get(2).getName()+": "+playerNames.get(2).getBankBalance()+" "+"\n");
         
    	}
    
}
