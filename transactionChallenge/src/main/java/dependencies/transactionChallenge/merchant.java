package dependencies.transactionChallenge;

public class merchant extends actor{
	Long shortestDuration;
	public merchant(String name){
		super(name);
	}
	Long getShortestDuration(){
		return this.shortestDuration;
	}
}
