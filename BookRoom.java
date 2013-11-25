import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
 

public class BookRoom implements Serializable {
	
	static final long serialVersionUID = 22L;
	
	static List<BookingDetails>     Booking      = new ArrayList<BookingDetails>();
	
	public static void StoreBookingDetails() throws IOException, ClassNotFoundException 
	{
		final String dataFile = "booking.txt";
		
				
		ObjectOutputStream out = null;
	    
		try {
	        out = new ObjectOutputStream(new
	                BufferedOutputStream(new FileOutputStream(dataFile)));

	        out.flush(); 
	        out.writeObject(Booking);
	         
	    }
		catch (Exception e) {
	        e.printStackTrace();
	    }
		finally {
	    	if(out != null){
	           out.close();
	    	}
	   }

	}

	
	@SuppressWarnings("unchecked")
	public static void ReadBookingDetails() throws IOException, ClassNotFoundException, FileNotFoundException
	{
		final String dataFile = "booking.txt";
				
		ObjectInputStream in = null;
	    
		try {
			in = new ObjectInputStream(new
	                BufferedInputStream(new FileInputStream(dataFile)));
						
			Booking.clear();
			Booking  = (List<BookingDetails>) in.readObject();
							         
	    }catch(FileNotFoundException e) {
	    	//e.printStackTrace();
	    	//System.out.println("File Not Found");
	       
	   }finally {
	    	if(in != null){
	    		 in.close();
	    	}
	   }

	}


	
	public void DoBooking(String City,Date Start,Date End,SearchResults S, List<Property> pts,String UserId,List<User> users) throws ClassNotFoundException, FileNotFoundException, IOException
	{
		 Random random = new Random();
		 int randomInteger = random.nextInt(100000);
		 int price = 0;
		 
		 int cancellation_penalty  = 0;
		 int cancellation_deadline = 0;
		 
		 ReadBookingDetails();
		 
		 		
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
				    	 
				    	// System.out.println(start + "   "+ end);  
				    	 
				    	 for (Date date = start.getTime(); !start.equals(end); 
				    			  start.add(Calendar.DATE, 1), date = start.getTime())
				    	 {
				    		 if(TempBed.BedInfo.get(date).Booked)
				    		 {
				    			 System.out.println("Beds are already booked with this search Id !!!!");
				    			 System.out.println();
				    			 return;
				    		 }
				    		 
				    		  TempBed.BedInfo.get(date).Booked     = true;
				    		  TempBed.BedInfo.get(date).BookingId  = randomInteger;
				    		  price += TempBed.BedInfo.get(date).Price;
				    		  
				    		  cancellation_penalty   = P.GetPenalty() ;
				    		  cancellation_deadline  = P.GetDedline() ;		  
				    		  
				   		 
				    		 
				    	 }		 
						 
						 
					 }
						
						
					}
						
					
				}
				
								
				
			}
		}
		 		 
		 BookingDetails     B      = new BookingDetails();
		 
		  B.Property              = S.PropertyID;
		  B.City                  = City;
		  B.CheckIn               = Start;
		  B.CheckOut              = End;
		  B.BookingId             = randomInteger;
		  B.TotalBeds             = S.Beds.size();
		  B.Price                 = price;
		  B.UserId     			  = Integer.parseInt(UserId);
		  for (User U :users)
		  { 
			  if (U.UserId == B.UserId )
			  {	  
			    B.Name = U.FirstName + "  " +U.LastName;
			    break;
			  }
		  }
		  B.cancellation_deadline = cancellation_deadline;
		  B.cancellation_penalty  = cancellation_penalty ;
		  
		  
		  System.out.println("\n\nBooking Successful!! Here's the booking details");
		  PrintBookingDetails(B);
		  Booking.add(B);
		  StoreBookingDetails(); 
		 			
	}
	
	public void ViewBooking(String SID) throws ClassNotFoundException, FileNotFoundException, IOException
	{
		ReadBookingDetails();
		boolean Found = false;
		
		for (BookingDetails BD : Booking)
		{
			if(BD.BookingId == Integer.parseInt(SID))
			{
			  Found = true;
			  System.out.println("\n\n Here's the booking details");
			  PrintBookingDetails(BD);
			  break;	
			}
		}
		
		if(!Found)
			System.out.println("\n No booking with " +SID +" booking id!!!");
	
	
	}
	
	public void CancelBooking(String SID,List<Property> pts) throws ClassNotFoundException, FileNotFoundException, IOException
	{
		ReadBookingDetails();
		boolean Found = false;
		
						
		for (BookingDetails BD : Booking)
		{
			if(BD.BookingId == Integer.parseInt(SID))
			{
			  Found = true;
			  System.out.println("Cancellation done !!!!!!!!!!!");
			  
			  Booking.remove(BD);
			  Date Today = new Date();
			  
//			  System.out.println(BD.cancellation_deadline);
//			  System.out.println(BD.cancellation_penalty);
			  
			  if( ( (BD.CheckIn.getTime() - Today.getTime())/(60 *60 *1000)) < BD.cancellation_deadline)
			  {
				  System.out.println("Due to cancellation policy :"+BD.cancellation_penalty+"% amount will be deducted");
				  System.out.println("Booking amount is : $"+BD.Price);
				  System.out.println("Refund amount is  : $"+(double)(BD.Price *( (double)(100 - BD.cancellation_penalty)/100)));
				  
			  }
			  
			
			  break;	
			}
		}
		
		  StoreBookingDetails(); 
		
		if(!Found)
			System.out.println("No booking with " +SID +" booking id!!!");
		else
		{
			
			for(Property P : pts)
			{
		  	  for(Bed TempBeds : P.Beds)
			  {
		  		for (Map.Entry<Date,Bed.BedInformation> entry : TempBeds.BedInfo.entrySet())
		  		{
		    	   		 Date key =  entry.getKey();
		    	   		 
		    	   		 if(TempBeds.BedInfo.get(key).BookingId == Integer.parseInt(SID))
		    	   		 {
		    	   			TempBeds.BedInfo.get(key).Booked    = false;
		    	   			TempBeds.BedInfo.get(key).BookingId = 0;
		    	   		    
		    	   			
		    	   		 }
		
		  		}
			  
		  	  }
					
			}
			
		}
	
	
	}
	
	private void PrintBookingDetails(BookingDetails BD)
	{
		 SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		 df.setLenient(false);
		 
		  System.out.print(BD.Property + ",");
		  System.out.println(BD.City);
		  System.out.println("Check-in Date  : "+df.format(BD.CheckIn));
		  System.out.println("Check-out Date : "+df.format(BD.CheckOut));
		  System.out.println("Beds           : " + BD.TotalBeds);
		  System.out.println("Booking id     : "+BD.BookingId );
		  System.out.println("Name           : " + BD.Name);
		  
		  System.out.println("Price          : $" + BD.Price);
		  
		  System.out.println();
		  
		 	
	}
	
	public void GetStatus(String Command,String City,String Start,String End, List<Property> pts) throws ClassNotFoundException, FileNotFoundException, IOException
	{
		List<Property> SelectedProp = new ArrayList<Property>();
		
		Date StartDate = new Date();
		Date EndDate = new Date();
		
		int Revenue     = 0;
		int BookedRooms = 0;
		int TotalRooms  = 0;
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		df.setLenient(false);
		
		try {
		  	 
		  	 		  	 
		  	 if(Start != null)
		  	   StartDate =  df.parse(Start);
		  	 
		  	 if(End != null)
		  	     EndDate   =  df.parse(End);
		  	  	 
		  	 
		  	 if ((Start != null) && (End != null))
		  	 {
               if(EndDate.before(StartDate) || EndDate.equals(StartDate))
               {
          	       System.out.println("End Date is past or equal start date");
          	       System.out.println("Please enter proper date ");
          	        return;
                }
		  	 }

		  	}
		  	catch(ParseException pe) {
		  		System.out.println();
		  		System.out.println("Arguement to date is not proper");
               System.out.println("Please enter proper date in YYYYMMDD format");
               System.out.println();
               return;
                                    
          }

		
		if ( City != null)
		{	
		  for(Property P : pts)
		  {
		        if (P.GetCityName().equals(City))
		   	        	SelectedProp.add(P);
		        	        	
		  }
		}
		
		else
		{	
			for(Property P : pts)
			  {
			   SelectedProp.add(P);
			  }
		}	
		
		if(SelectedProp.isEmpty())
	  	{
	  	  System.out.println("No Hostels found in city "+City);
	  	  		return ;
	  	}
		
		System.out.println();
		
		
		for(Property P : pts)
		{
			Revenue=0;
			BookedRooms = 0;
			TotalRooms  = 0;
			
			Map<Date,SimpleSearchResults> SelectedBeds = new TreeMap<Date,SimpleSearchResults>();  
			
			for (Bed B : P.Beds)
			{
				Map<Date,Bed.BedInformation> SortedBedInfo= new TreeMap<>(B.BedInfo); 
		    	   
		    	   
		    	   for (Map.Entry<Date,Bed.BedInformation> entry : SortedBedInfo.entrySet())
		    	   {
		    		   Date key =  entry.getKey();
		    	       
		    	       
		    	       if((Start != null) && (StartDate.after(key)))
		    	           continue;
		    	       
		    	       
		    		   if ((End != null) && (EndDate.equals(key)))
		    		    	 break;
		    		 
		    		   if(SortedBedInfo.get(key).Booked)
		    	   	   {
		    			   Revenue += SortedBedInfo.get(key).Price;
		    		       BookedRooms++;
		    	   	   }
		    		   
		    		   TotalRooms++;
				
			      }
			
			
		    }
			
			if (Command.equals("revenue"))
			  System.out.println(P.GetPropertyName() +", "+P.GetCityName()+"  "+ Revenue);
		
			else if (Command.equals("occupancy"))
			{
				//System.out.println(BookedRooms);
				long occupancy = ((BookedRooms*100/TotalRooms));
				System.out.println(P.GetPropertyName() +", "+P.GetCityName()+",  "+ occupancy+"%");
			}
		}
		System.out.println();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
