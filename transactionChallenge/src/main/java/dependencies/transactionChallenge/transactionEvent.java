package dependencies.transactionChallenge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class transactionEvent {
	
	//Store transactions using transaction class
	static ArrayList<transaction> events = new ArrayList<transaction>();
	
	
	static ArrayList<customer> customers = new ArrayList<customer>();
	static ArrayList<merchant> merchants = new ArrayList<merchant>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//First, read JSON, and instantiate transaction objects
		createTransactionObjects();
		
		createCustomerObjectsAndMerchantObjects();
		
		
		findTransactionAverageAndRemainingBalance();
		
		//Task1
		displayHighestTransactionAverage("customer");
		//Task2
		displayHighestTransactionAverage("merchant");
		//Task3
		displayHighestRemainingBalance();
		//Task4
		displayShortestDifferenceOneAndFour();
		
	}

	
	
	



	private static void displayHighestRemainingBalance() {
		// TODO Auto-generated method stub
		
		customers.sort(Comparator.comparing(customer::getRemainingBalance).reversed());
		
		System.out.println("");
		System.out.println("Customers with highest balances are;");
		for(int i=0;i<5;i++){
			if(customers.size()<i+1){
				break;
			}
			System.out.println(customers.get(i).name +" with "+ customers.get(i).remainingBalance);
		}
	}



	private static void createCustomerObjectsAndMerchantObjects() {
		//This function also creates merchants
		
		// TODO Auto-generated method stub
		//avoid duplicates we must
		for(int i=0;i<events.size();i++){
			boolean notDupliate = checkIfExists(events.get(i).customerId, "customer");//customers
			if(notDupliate){
				customers.add(new customer(events.get(i).customerId));
			}
			
			notDupliate = checkIfExists(events.get(i).merchantId, "merchant");//merchants
			if(events.get(i).merchantId!=null && notDupliate){
				merchants.add(new merchant(events.get(i).merchantId));
			}
			
		}
		
	}



	private static boolean checkIfExists(String actorId, String actorType) {
		// TODO Auto-generated method stub
		if(actorType.equals("customer")){
			for(customer c: customers){
				if(c.name.equals(actorId)){
					return false;
				}
			}
			return true;
		}else{
			for(merchant m: merchants){
				if(m.name.equals(actorId)){
					return false;
				}
			}
			return true;
		}
		
	}



	private static void findTransactionAverageAndRemainingBalance() {
		// TODO Auto-generated method stub
		
		//for each customer, find total amounts, then divide
		for(int i=0;i<customers.size();i++){
			int total = 0;
			int numberOfTransanctions =0;
			for(int j=0;j<events.size();j++){
				if(events.get(j).customerId.equals(customers.get(i).name)){
					total+=events.get(j).amount;
					numberOfTransanctions++;
					
					if(events.get(j).eventType.equals("deposit")){
						customers.get(i).remainingBalance+=events.get(j).amount;
					}else{
						customers.get(i).remainingBalance-=events.get(j).amount;
					}
				}
			}
			customers.get(i).setAverageAmount(total/numberOfTransanctions);
		}
		
		
		
		
		//Same process for merchants, but not recording total balance
		for(int i=0;i<merchants.size();i++){
			int total = 0;
			int numberOfTransanctions =0;
			for(int j=0;j<events.size();j++){
				if(events.get(j).merchantId!=null && events.get(j).merchantId.equals(merchants.get(i).name)){
					total+=events.get(j).amount;
					numberOfTransanctions++;
				}
			}
			merchants.get(i).setAverageAmount(total/numberOfTransanctions);
		}
	}



	private static void displayHighestTransactionAverage(String actorType) {
		// TODO Auto-generated method stub
			ArrayList <actor> actorList = new ArrayList <actor>();
			
			System.out.println(" ");
			if(actorType.equals("merchant")){
				System.out.print("Merchant");
				
				//hashlist to array
				for(int i=0;i<merchants.size();i++){
					actorList.add(merchants.get(i));
				}
			}else{
				System.out.print("Customer");
				//hashlist to array
				for(int i=0;i<customers.size();i++){
					actorList.add(customers.get(i));
				}
			}
			actorList.sort(Comparator.comparing(actor::getAverageAmount).reversed());
			
			
			System.out.println(" with highest average transaction amount;");
			for(int i=0;i<5;i++){
				if(actorList.size()-1<i){
					break;
				}
				System.out.println(actorList.get(i).name+" with an average of "+ actorList.get(i).averageAmount+" per transaction");
			}
		}
		
		
		//display top five (descending order)
		
	



	private static void createTransactionObjects() {
		// TODO Auto-generated method stub
		JSONParser parser = new JSONParser();
		String fileName = ".//src//main//java//dependencies//transactionChallenge//exampleInput.json";

        //Use buffered reader to read line by line
	    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	        	Object obj = null;
				try {
					obj = parser.parse(line);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	JSONObject jsonObject = (JSONObject) obj;
	     
				//Amount is set as a Long value, which is not appropriate, will convert later
				Long amount = (Long) (jsonObject.get("amount"));
					events.add(new transaction(
							(String)jsonObject.get("eventType"), 
							(String)jsonObject.get("depositId"),
							(String)jsonObject.get("customerId"), 
							(String)jsonObject.get("merchantId"), 
							(String)jsonObject.get("time"), 
							amount.intValue()
							)
							);
	        }
	    } catch (IOException e) {
	        System.out.println(e.getMessage());
	    }
	}
	
	
	private static void displayShortestDifferenceOneAndFour() {
		// TODO Auto-generated method stub
		for(int i=0;i<merchants.size();i++){
			ArrayList<LocalDateTime> dates = new ArrayList<LocalDateTime>();
			
			int discreteDifference = 4;
			
			for(int j=0;j<events.size();j++){
				
				if(("Merch"+(i+1)).equals(events.get(j).merchantId)){
					String correctFormatTime = events.get(j).time.substring(0, events.get(j).time.length()-1);
					LocalDateTime date = LocalDateTime.parse(correctFormatTime);
					dates.add(date);
					//dates.add(events.get(j).time);
				}
			}
			
			
			if(dates.size()<5){//if the merchant has less than 5 events, mark as invalid
				merchants.get(i).shortestDuration=(long) 999;
			}else{
				merchants.get(i).shortestDuration = (long) (dates.get(discreteDifference).getDayOfYear()-dates.get(0).getDayOfYear());
				for(int n=0;n<dates.size()-discreteDifference;n++){
					if((long) (dates.get(n+discreteDifference).getDayOfYear()-dates.get(n).getDayOfYear())<merchants.get(i).shortestDuration){
						merchants.get(i).shortestDuration = (long) (dates.get(n+discreteDifference).getDayOfYear()-dates.get(n).getDayOfYear());
					}
				}
				
			}
		}
		
		merchant[] m = new merchant[merchants.size()];
		for(int i=0;i<m.length;i++){
			m[i] = merchants.get(i);
		}
		
		merchants.sort(Comparator.comparing(merchant::getShortestDuration).reversed());
			
		System.out.println("");
		System.out.println("Merchants with shortest time differences are;");
		for(int i=0;i<5;i++){
			if(merchants.size()<i+1){
				break;
			}else if(m[i].shortestDuration==999){
				System.out.println("undefined");
			}else{
				System.out.println(m[i].name +" with "+ m[i].shortestDuration+" days");
			}
		}
	}
}
