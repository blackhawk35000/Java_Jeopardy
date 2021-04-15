
public class Clue {
	private String Clue;
	private String Category;
	private int Value;
	private String Question; // Might remain this to answer if that would be easier to work with
	private boolean IsUsed;
	
	// Clue 
	public Clue(String Clue, String Category, int Value, String Question) {
		this.Clue = Clue;
		this.Category = Category;
		this.Value = Value;
		this.Question = Question;
		this.IsUsed = false; // This value will start false and be changed when the Clue is used
	}
	
	// setters only making these just in case there is a need to append one of these values 
	// we probably will not be needing these since a clue object shouldn't be really changing
	public void setClue(String clue) {
		this.Clue = clue;
	}
	
	public void setCategory(String Category) {
		this.Category = Category;
	}
	
	public void setValue(int value) {
		this.Value = value;
	}
	
	public void setQuestion(String question) {
		this.Question = question;
	}
	
	public void setStatus(boolean used) {
		this.IsUsed = used;
	}
	
	// Getters
	public String getClue() {
		return this.Clue;
	}
	
	public String getCategory() {
		return this.Category;
	}
	
	public int getValue() {
		return this.Value;
	}
	
	public String getQuestion() {
		return this.Question;
	}
	
	public boolean getStatus() {
		return this.IsUsed;
	}
	
	// Functions
	
	// Right now this just sets IsUsed to false depending on how we want to build the board logic this 
	// could also remove all other values as well we'll talk about it
	public void removeFromPlay() {
		this.IsUsed = true;
	}

}
