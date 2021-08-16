package dependencies.transactionChallenge;

import java.util.ArrayList;

import org.junit.Test;

import dependencies.featureSpaceEvents.AppTest;
import junit.framework.TestCase;

public class transactionEventTest extends TestCase {
	static ArrayList<transaction> events = new ArrayList<transaction>();
	static ArrayList<customer> customers = new ArrayList<customer>();
	static ArrayList<merchant> merchants = new ArrayList<merchant>();
	static ArrayList<transaction> testList = new ArrayList<transaction>();
	@Test
	public void test(){
		events = transactionEvent.createTransactionObjects(events, ".//src//main//java//dependencies//transactionChallenge//testInput.json");
		testList.add(new transaction("deposit", "Cust1", null, "2021-01-01T09:00:00Z", 500));
		testList.add(new transaction("transaction", "Cust1", "Merch1", "2021-01-01T10:00:00Z", 10));
		testList.add(new transaction("transaction", "Cust1", "Merch1", "2021-01-02T11:00:00Z", 20));
		
		assertEquals(testList.get(0).amount, events.get(0).amount);
		assertEquals(testList.get(1).merchantId, events.get(1).merchantId);
		assertEquals(testList.get(1).customerId, events.get(1).customerId);
		assertEquals(testList.get(2).amount, events.get(2).amount);
		
		customers.add(new customer("Cust1"));
		transactionEvent.createCustomerObjectsAndMerchantObjects(customers, merchants);
		assertEquals(customers.get(0).name, "Cust1");
		
		customers = transactionEvent.findTransactionAverageAndRemainingBalanceCust(customers, events);
		merchants = transactionEvent.findTransactionAverageAndRemainingBalanceMerch(merchants, events);
		
		assertEquals(customers.get(0).averageAmount, 176.0);
		
	}
}
