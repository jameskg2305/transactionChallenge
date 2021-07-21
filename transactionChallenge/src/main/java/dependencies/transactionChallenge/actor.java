package dependencies.transactionChallenge;

public class actor {
	String name;
	double averageAmount;
	int remainingBalance;
	public actor(String name){
		this.name = name;
	}
	public void setAverageAmount(int x){
		this.averageAmount = x;
	}
	public double getAverageAmount(){
		return this.averageAmount;
	}
	public double getRemainingBalance(){
		return this.remainingBalance;
	}
}
