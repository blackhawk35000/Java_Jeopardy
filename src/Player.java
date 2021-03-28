
public class Player {
	private String Name;
	private int BankBalance;
	private boolean IsActive; //Is active will be used to determine which player is selecting next clue 
	
	
	// Constructor(Requires Name)
	public Player(String Name) {
		this.Name = Name;
		this.BankBalance = 0;
		// I admit that this could cause a bug if we are not careful my logic is that when we are making
		// a game we will be setting one of the players later to active while the other will be already
		// Initialized to inactive.
		this.IsActive = false; 
	}
	
	
	// setters
	public void setName(String name) {
		this.Name = name;
	}
	
	public void setBankBalance(int balance) {
		this.BankBalance = balance;
	}
	
	public void setActiveStatus(boolean active) {
		this.IsActive = active;
	}
	
	// Getters
	public String getName() {
		return this.Name;
	}
	
	public int getBankBalance() {
		return this.BankBalance;
	}
	
	public boolean getActiveStatus() {
		return this.IsActive;
	}
	
	// functions for player
	
	// This will be used to add clue values to players BankBalance
	public void AddToBank(int value) {
		this.BankBalance = this.BankBalance + value;
		// We will have to talk about this part but I was thinking this could also
		// set player to active.
		if(this.IsActive = false) {
			this.IsActive = true;
		}
	}
}
