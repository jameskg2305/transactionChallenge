package dependencies.transactionChallenge;

import java.util.Date;

public class transaction {
	String eventType;
	String customerId;
	String merchantId;
	String time;
	int amount;
	
	public transaction(String eventType, String customerId, String merchantId, String time, int amount){
		this.eventType = eventType;
		this.customerId = customerId;
		this.merchantId = merchantId;
		this.time = time;
		this.amount = amount;
	}
}
