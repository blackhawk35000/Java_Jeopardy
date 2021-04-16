import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    String category;
    Scanner myScanner = new Scanner(System.in);
    ArrayList<Clue> clueList = new ArrayList<>();
    ArrayList<String> categoryList = new ArrayList<>();
    //File Data = new File (// add csv file path here //);

    //returns array list of categories (assuming its the first line in the CSV file)
    public ArrayList<String> importCategories(String path){
        File dataFile = new File(path);
        String line = "";
        ArrayList<String> categories = new ArrayList<String>();
        try{
            Scanner reader = new Scanner(dataFile);
            line = reader.nextLine();
            categories.add(line);
        }catch (Exception myEx){
            System.out.println("Error");
        }
        //return ArrayList of categories
        return categories;
    }
    public ArrayList<String> importClues(String path){
        File dataFile = new File(path);
        String line = "";
        ArrayList<String> clues = new ArrayList<>();
        try{
            Scanner reader = new Scanner(dataFile);
            reader.nextLine(); //skip header
            //loop through file
            while(reader.hasNextLine()){
                line = reader.nextLine();
                clues.add(line);
            }
        }catch (Exception myEx){
            System.out.println("Error");
        }
        //return ArrayList of clues
        return clues;

    }
    public void createBoard() {
        int i = 0;
        int pos=0;
        while (pos!=6){
            while(i!=6){
                if(pos==0){
                    System.out.print("|");
                    System.out.printf("%10s",categoryList.get(i));
                    System.out.print("          ");
                    if(i==5) {
                        System.out.print("|");
                    }
                }else{
                	if(clueList.get(i).getStatus()==false) {
                		System.out.print("|");
                		System.out.printf("%10s",clueList.get(i).getValue());
                		System.out.print("          ");
                	}else {
                		System.out.print("|");
                		System.out.printf("%10s","X");
                		System.out.print("          ");
                	}
                    if(i==5) {
                        System.out.print("|");
                    }
                }

                i++;
            }
            System.out.println();
            i=0;
            pos++;
        }
    }
    public boolean displayClue(String Category, int Val){
        //   if (Data.isFile()){
        int i=0;
        while(i!=clueList.size()) {
            if (clueList.get(i).getValue() == Val) {
            	
                if (clueList.get(i).getCategory().contains(Category) && clueList.get(i).getStatus() == false) {
                    System.out.println(clueList.get(i).getClue());
                    clueList.get(i).removeFromPlay();
                    return true;
                }else if(clueList.get(i).getCategory() == Category && clueList.get(i).getStatus() == true) {
                    System.out.println("This Clue has already been used! You are sneaky!");
                    return false;
                }else {
                    i++;
                    continue;
                }
            }
        }
        return false;
    }
    //  }
    public void removeClue(){
    	
    }

    public void testArrays(){

        for(int i =0; i!=6; i++){
            categoryList.add("ME");
        }
        for(int i=0; i!=30; i++){
            Clue clue = new Clue("Name of me","ME",200,"Who is Waldo.");
            clueList.add(clue);
        }

    }

    public boolean isEmpty(){
        int i=0;
        int numbActive_clues=0;
        while(i!=clueList.size()){
            if (numbActive_clues>0){
                return false;// should break if not problem
            }else{
                if(clueList.get(i).getStatus()==false) {
                    numbActive_clues++;
                }
            }
            i++;
        }
        if(numbActive_clues==0){
            return true;
        }else{
            return false;
        }
    }

}
/*
    public static void printList(ArrayList<String> clueList) {
        System.out.print("\nJeapordy: \n");
        for (int i = 0; i < cluesList.size(); i++) {
            System.out.print(cluesList.get(i) + ", " + cluesList.get(i+1) + ", " + clueList.get(i+2) + "\n");
        }
        System.out.println("__________\n\n");
    }

 */
