import java.util.*;
import java.io.Serializable;


public class BookRoom implements Serializable {

	
	public void DoBooking(String City,Date Start,Date End,SearchResults S, List<Property> pts)
	{
		 Random random = new Random();
		 int randomInteger = random.nextInt(100000);
		 int price = 0;
		 
		 for(BedInfo B : S.Beds)
  		     System.out.println(B.BedNumber + "-> "+B.Price);
		 
		 System.out.println(pts.size());
		 
		 
		
		for(Property P : pts){
			
			if((P.GetCityName().equals(City)) && (P.GetPropertyName().equals(S.PropertyID)))
			{
				
				for(BedInfo TempBedInfo : S.Beds){
					
					for(Bed TempBed : P.Beds) {
						
					 if (TempBedInfo.BedNumber == TempBed.GetBedNumber())
					 {
						 Calendar start = Calendar.getInstance();
				    	 start.setTime(Start);
				    	 Calendar end = Calendar.getInstance();
				    	 end.setTime(End);
				    	 
				    	 System.out.println("Bye");  
				    	 
				    	 for (Date date = start.getTime(); !start.after(end); 
				    			  start.add(Calendar.DATE, 1), date = start.getTime())
				    	 {
				    	   
				    		   
				    		  TempBed.BedInfo.get(date).Booked     = true;
				    		  TempBed.BedInfo.get(date).BookingId  = randomInteger;
				    		  price += TempBed.BedInfo.get(date).Price;
				    		  
				    		  System.out.println("HI");   
				    		 
				    		 
				    	 }		 
						 
						 
					 }
						
						
					}
						
					
				}
				
								
				
			}
		}
			
		 System.out.println("Booking Successful!! Here's the booking details");
		  System.out.println(City);
		  System.out.println(S.PropertyID);
		  System.out.println("Check-in Date  : "+Start);
		  System.out.println("Check-out Date : "+End);
		  System.out.println("Beds :" + S.Beds.size());
		  System.out.println("Price :" + price);
		  
		 // Hostel21.StoreProperty();
			
	}
}
