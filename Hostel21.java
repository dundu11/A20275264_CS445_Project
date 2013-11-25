import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException; 

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element; 



class Hostel21 implements Serializable{

	static final long serialVersionUID = 42L;
	static List<Property> properties = new ArrayList<Property>();
	static List<User>     users      = new ArrayList<User>();
	 
			
	static Search srh     = new Search();
	
	public static void StoreUser() throws IOException, ClassNotFoundException 
	{
		final String dataFile = "user.txt";
		
				
		ObjectOutputStream out = null;
	    
		try {
	        out = new ObjectOutputStream(new
	                BufferedOutputStream(new FileOutputStream(dataFile)));

	        out.flush(); 
	        out.writeObject(users);
	         
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
	public static void ReadUsers() throws IOException, ClassNotFoundException, FileNotFoundException
	{
		final String dataFile = "user.txt";
				
		ObjectInputStream in = null;
	     
		try {
			in = new ObjectInputStream(new
	                BufferedInputStream(new FileInputStream(dataFile)));
						
			users.clear();
			users  = (List<User>) in.readObject();
							         
	    }catch(FileNotFoundException e) {
	    	//e.printStackTrace();
	    	//System.out.println("File Not Found");assertTrue(file.exists());
	       
	   }finally {
	    	if(in != null){
	    		 in.close();
	    	}
	   }

	}
			
    public static void StoreProperty() throws IOException, ClassNotFoundException 
	{
		final String dataFile = "property.txt";
		
				 
		ObjectOutputStream out = null;
	    
		try {
	        out = new ObjectOutputStream(new
	                BufferedOutputStream(new FileOutputStream(dataFile)));

	        out.flush(); 
	        out.writeObject(properties);
	         
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
	public static void ReadProperty() throws IOException, ClassNotFoundException
	{
		final String dataFile = "property.txt";
				
		ObjectInputStream in = null;
	    
		try {
			in = new ObjectInputStream(new
	                BufferedInputStream(new FileInputStream(dataFile)));
						
			properties.clear();
			properties  = (List<Property>) in.readObject();
							         
	    }catch(FileNotFoundException e) {
	    	//e.printStackTrace();
	    	//System.out.println("File Not Found");
	       
	   }finally {
	    	if(in != null){
	    		 in.close();
	    	}
	   }

	}

	public static void StoreSR() throws IOException, ClassNotFoundException ,FileNotFoundException
	{
		final String dataFile = "searchresults.txt";
	//	System.out.println("StoreSR");
		
				
		ObjectOutputStream out = null;
	    
		try {
	        out = new ObjectOutputStream(new
	                BufferedOutputStream(new FileOutputStream(dataFile)));

	       
	        out.flush();
	        
	        out.writeObject(srh);
	         
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

	public static void ReadSR() throws IOException, ClassNotFoundException 
	{
		final String dataFile = "searchresults.txt";
				
		ObjectInputStream in = null;
	    
		try {
			in = new ObjectInputStream(new
	                BufferedInputStream(new FileInputStream(dataFile)));
						
			srh  = (Search) in.readObject();
			
													         
	    }catch(FileNotFoundException e) {
	    	//e.printStackTrace();
	    	//System.out.println("File Not Found");
	       
	   }finally {
	    	if(in != null){
	    		 in.close();
	    	}
	   }

	}


	
	public static void main(String[] args)  throws IOException, ClassNotFoundException, ParseException
	{
		String Command = null;
		String City    = null;
		String Start   = null;
		String End     = null;
		int    beds    = 0; 
		
		Date StartDate = new Date();
		Date EndDate   = new Date();
	    
		Random random = new Random();
		int UserId = random.nextInt(100000);
				
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		df.setLenient(false);
		
		if(args.length == 0)
		{
			System.out.println("No arguements to main");
			System.out.println();
			return ;
		}
		
		Command = args[0];
		if(Command.equals("search"))
		{
			ReadProperty();
			
			if(properties.isEmpty())
		  	{
		  	  System.out.println("Hostel properties are not loaded !!!!! ");
		  	  		return ;
		  	}
			for(int i =1;i<args.length;i+=2){
				 
				if(args[i].equals("--city"))
				{ 
					if( i == args.length-1)
					{
						System.out.println();
			  			System.out.println("\nNo arguments for : " + args[i]);
			  			System.out.println(); 
			  			return ;	
						
					}
				 
		  			City = args[i+1];
				}
				else if (args[i].equals("--start_date"))
				{	
					if( i == args.length-1)
					{
						System.out.println();
			  			System.out.println("\nNo arguments for : " + args[i]);
			  			System.out.println(); 
			  			return ;	
						
					}
		  			
					Start = args[i+1];
				}
		  		else if (args[i].equals("--end_date"))
		  		{
		  			if( i == args.length-1)
					{
						System.out.println(); 
			  			System.out.println("\nNo arguments for : " + args[i]);
			  			System.out.println(); 
			  			return ;	
						
					}
		  			End = args[i+1];
		  		}
		  		else if (args[i].equals("--beds"))
		  		{
		  			if( i == args.length-1)
					{
						System.out.println();
			  			System.out.println("\nNo arguments for : " + args[i]);
			  			System.out.println(); 
			  			return ;	
						
					}
		  			beds = Integer.parseInt(args[i+1]);
		  		}
		  		else
		  		{	
		  			System.out.println();
		  			System.out.println("\nNot Valid command: Search usage is as follows ");
		  			System.out.println("search [--city \"City name\"] [--start_date YYYYMMDD] [--end_date YYYYMMDD] [--beds x]");
		  			System.out.println(); 
		  			return ;
		  		}
		  	}
		  	
		  	try {
		  	 
		  	 Date Today = new Date();
		  	 
		  	 if(Start != null)
		  	 {
		  	   StartDate =  df.parse(Start);
		  	   if(StartDate.before(Today))
               {
           	     System.out.println("Entered Date is past date");
           	     System.out.println("Please enter proper date ");
           	      return ;
               }
		  	 }
		  	 
		  	 if(End != null)
		  	 {
		  		 EndDate   =  df.parse(End);
		  		 // System.out.println(End);
		  		 
		  		 if(EndDate.before(Today))
	             {
	           	  System.out.println("Entered Date is past date");
	           	  System.out.println("Please enter proper date ");
	           	  return;
	             }
		  	 }
		  	 
		  	 if ((Start != null) && (End != null))
		  	 {
                if(EndDate.before(StartDate) || EndDate.equals(StartDate))
                {
                   System.out.println();
                   System.out.println("End Date is past start date or same as start date");
           	       System.out.println("Please enter proper date ");
           	       System.out.println();
           	       return ;
                 }
		  	 }

		  	}
		  	catch(ParseException pe) {
		  		System.out.println();
		  		System.out.println("Arguement to date is not proper");
                System.out.println("Please enter proper date in YYYYMMDD format");
                System.out.println();
                return ;
                                     
           } 
		  	
		  	SimpleDateFormat df1 = new SimpleDateFormat("MM/dd/yyyy");
   		    df1.setLenient(false);
   		 
		  	System.out.println("\nSearching Details");
		    System.out.println("-----------------");
		    if(City != null)
		    System.out.println("City Name      : " + City);
		    if(Start != null)
		    System.out.println("Start Date     : " + df1.format(StartDate));
		    if(End != null)
		    System.out.println("End Date       : " + df1.format(EndDate));
		    if(beds != 0)
		    System.out.println("Number of Beds : " + beds);
		  	
		    System.out.println("\n\nSearching Results :");
			System.out.println("-------------------");
		    
			if ((City != null) && (Start != null) && (End != null) && (beds != 0) )
			{
				srh.SearchBeds(City,StartDate,EndDate,beds,properties);
				//System.out.println("StoreSR");
				StoreSR();
			}
			else
			{	 
			   srh.SimpleSearch(City,Start,End,beds,properties);	
			   
			}
						
		}
		
		else if (Command.equals("admin"))
		{
			//System.out.println(args.length);
			
			if(args.length ==1)
            { 
				System.out.println();
	  			System.out.println("No Argumenets to : " + Command);
	  			System.out.println(); 
	  			return ;
					
		   }

			if(args[1].equals("load"))
			{
				  properties.clear();
				  				  
				  if(args.length == 3)
				  {
					 ParseXML(args[2]);
				     StoreProperty();
			      } 
				  else
					  System.out.println("\nNo arguments for : " + args[1]);
					  
			}
			else if (args[1].equals("revenue") || args[1].equals("occupancy"))
			{
				BookRoom BR = new BookRoom();
				ReadProperty();
				
				for(int i =2;i<args.length;i+=2)
				{
					
					if(args[i].equals("--city"))
					{ 
						if( i == args.length-1)
						{
							System.out.println();
				  			System.out.println("\nNo arguments for : " + args[i]);
				  			System.out.println(); 
				  			return ;	
							
						}
					 
			  			City = args[i+1];
					}
					else if (args[i].equals("--start_date"))
					{	
						if( i == args.length-1)
						{
							System.out.println();
				  			System.out.println("\nNo arguments for : " + args[i]);
				  			System.out.println(); 
				  			return ;	
							
						}
			  			
						Start = args[i+1];
					}
			  		else if (args[i].equals("--end_date"))
			  		{
			  			if( i == args.length-1)
						{
							System.out.println();
				  			System.out.println("\nNo arguments for : " + args[i]);
				  			System.out.println(); 
				  			return ;	
							
						}
			  			End = args[i+1];
			  		}
			  		else
			  		{	
			  			System.out.println();
			  			System.out.println("\nNot valid arguement : " + args[i]);
			  			System.out.println(); 
			  			return ;
			  		}
			  	}
				
				if(properties.isEmpty())
			  	{
			  	  System.out.println("Hostel properties are not loaded !!!!! ");
			  	  		return ;
			  	}
			  		
			  	
				 BR.GetStatus(args[1],City,Start,End,properties);
				
				
			}
						
			else
			{
				System.out.println();
	  			System.out.println("\nInvalid command  : " + args[1]);
	  			System.out.println(); 
	  			return ;
				
			}
			
		}
		
		else if (Command.equals("book"))
		{
			BookRoom BR = new BookRoom();
			
			String SID =null;
			String UID =null;
            if(args.length ==1)
            { 
				System.out.println();
	  			System.out.println("No Argumenets to : " + Command);
	  			System.out.println(); 
	  			return ;
					
		   }
            
			
			if(args[1].equals("add"))
			{
				//System.out.println(args.length);
				
				if(args.length != 6)
				{
					System.out.println("\nArguements are not proper/complete: Usage is as follows ");
					System.out.println("book add --search_id xxxxx --user_id yyyyy");
					System.out.println();
					return ;
				}
				
				for(int i =2;i<args.length;i+=2)
				{
					
					if(args[i].equals("--search_id"))
					{ 
						if( i == args.length-1)
						{
							System.out.println();
				  			System.out.println("\nNo arguments for : " + args[i]);
				  			System.out.println(); 
				  			return ;	
							
						}
					 
			  			SID = args[i+1];
					}
					else if (args[i].equals("--user_id"))
					{	
						if( i == args.length-1)
						{
							System.out.println();
				  			System.out.println("\nNo arguments for : " + args[i]);
				  			System.out.println(); 
				  			return ;	
							
						}
			  			
						UID = args[i+1];
					}
			  		else
			  		{	
			  			System.out.println();
			  			System.out.println("\nNot valid arguement : " + args[i]);
			  			System.out.println(); 
			  			return ;
			  		}
			  	}
				 
				ReadSR();
				ReadProperty();
				ReadUsers();
			
				SearchResults S1 = new SearchResults ();
				Boolean ProperSearchName = false;
				Boolean UserFound        = false;
				
				
				for(User U : users)
				{
					if( U.UserId == Integer.parseInt(UID))
					{
					   UserFound = true;
					}
					
				}
				
				if(!UserFound)
				{
					System.out.println("\n User does not exist. Please enter proper User");
					return ;
				}
				
				for(SearchResults S : srh.SR){
					
							
					if( S.SearchID.equals(SID))
					{
						ProperSearchName = true;
						S1=S;
						break;
						 
					}
				}
				
				if(ProperSearchName)
				{	
			  	  BR.DoBooking(srh.GetSearchCity(),srh.GetStartDate(),srh.GetEndDate(),S1,properties,UID,users);
			  	  StoreProperty();
				}
			  	 else
					System.out.println("Please Enter Proper Search Id : " + SID );
				
			}
			
			else if(args[1].equals("cancel"))
			{ 
				//To do
				ReadProperty();
				//System.out.println("Cancel booking");
				
				if(args.length != 4)
				{	
					System.out.println("\nArguements are not proper/complete: Usage is as follows ");
					System.out.println("book cancel --booking_id xxxxx");
					System.out.println();
					return ;
				
				}	
				if(!(args[2].equals("--booking_id")))
				{	
					System.out.println("Invalid Command Option " + args[2] );
					System.out.println("Following is the Usage");
					System.out.println("book cancel --booking_id xxxxx");
					return ;
				
				}	
				BR.CancelBooking(args[3],properties);
				StoreProperty();
			}
			
			else if(args[1].equals("view"))
			{
				
				System.out.println("View booking");
				if(args.length != 4)
				{	
					System.out.println("\nArguements are not proper/complete: Usage is as follows ");
					System.out.println("book view --booking_id xxxxx");
					System.out.println();
					return ;
				
				}	
				if(!(args[2].equals("--booking_id")))
				{	
					System.out.println("Invalid Command " + args[1] );
					System.out.println("book view --booking_id xxxxx");
					return ;
				
				}
				
				BR.ViewBooking(args[3]);
			}
			else
			{ 
					System.out.println();
		  			System.out.println("\nInvalid command  : " + args[1]);
		  			System.out.println(); 
		  			return ;
						
				
			}
		
		}
		else if(Command.equals("user"))
		{
			if(args.length == 1)
			{
				System.out.println();
	  			System.out.println("\nNo arguments for : " + Command);
	  			System.out.println(); 
	  			return ;
				
			}
			
			if(args[1].equals("add"))
			{
				
				if(args.length == 2)
				{
					System.out.println();
		  			System.out.println("\nNo arguments for : " + args[1]);
		  			System.out.println(); 
		  			return ;
					
				}
				
				String  FirstName    = null;
				String  LastName     = null; 
				String  EmailID      = null;
				String  CCNumber     = null;
				String  SecurityCode = null;
				String  PhoneNumber  = null;
				
				Date    DateCreation = new Date();
				Date    ExpiryDate   = new Date();
				
				boolean bFirst= false;
				boolean bLast= false;
				boolean bEmail= false;
				
												
				//System.out.println("UserId --> " + UserId);
				
				for(int i =2;i<args.length; i=i+2)
				{
					if(args[i].equals("--first_name"))
					{
						if( i == args.length-1)
						{
							System.out.println();
				  			System.out.println("\nNo arguments for : " + args[i]);
				  			System.out.println(); 
				  			return ;	
							
						}
						
						FirstName = args[i+1];
						bFirst = true;
					}
					else if(args[i].equals("--last_name"))
					{
						if( i == args.length-1)
						{
							System.out.println();
				  			System.out.println("\nNo arguments for : " + args[i]);
				  			System.out.println(); 
				  			return ;	
							
						}
						
						LastName = args[i+1];
						bLast = true;
					}
					else if(args[i].equals("--email"))
					{
						if( i == args.length-1)
						{
							System.out.println();
				  			System.out.println("\nNo arguments for : " + args[i]);
				  			System.out.println(); 
				  			return ;	
							
						}
						
						EmailID = args[i+1];
						bEmail = true;
					}
					else if(args[i].equals("--cc_number"))
					{
						if( i == args.length-1)
						{
							System.out.println();
				  			System.out.println("\nNo arguments for : " + args[i]);
				  			System.out.println(); 
				  			return ;	
							
						}
						CCNumber = args[i+1];
					}
					else if(args[i].equals("--security_code"))
					{
						if( i == args.length-1)
						{
							System.out.println();
				  			System.out.println("\nNo arguments for : " + args[i]);
				  			System.out.println(); 
				  			return ;	
							
						}
						
						SecurityCode = args[i+1];
					}
					else if(args[i].equals("--phone"))
					{
						if( i == args.length-1)
						{
							System.out.println();
				  			System.out.println("\nNo arguments for : " + args[i]);
				  			System.out.println(); 
				  			return ;	
							
						}
						PhoneNumber = args[i+1];
					}
					else if(args[i].equals("--expiration_date"))
					{
						if( i == args.length-1)
						{
							System.out.println();
				  			System.out.println("\nNo arguments for : " + args[i]);
				  			System.out.println(); 
				  			return ;	
							
						}
						ExpiryDate = df.parse(args[i+1]);
					}
					else
					{
						System.out.println();
						System.out.println("Not proper command option :" + args[i]);
						System.out.println();
						return ;
					}
					
				}
				
				 if(!(bFirst && bLast && bEmail))
				 {
					 System.out.println();
					 System.out.println("First name ,Last name and Email Id are must for user to register");
					 System.out.println("Following is the usage : ");
					 System.out.println("user add --first_name \"abc\" --last_name \"bcd\" --email \"abc@xyz.com\" [ --cc_number --expiration_date --security_code --phone ]");
					 
					 System.out.println();
					
					 return ;
					 
				 }
				
				    					
		    		ReadUsers();
		    		boolean UserPresent = false;
		    		
		    		//System.out.println(EmailID);
		    				    		
					for (User U : users)
					{
						//System.out.println(EmailID);
						//System.out.println(U.EmailID);
						if(U.EmailID.equals(EmailID))
						{
						  System.out.println("User is already added.!!! ");
						  UserPresent = true;
						  break;
						}
					}
					
					if(!UserPresent)
					{
						User AddUser = new User();
						AddUser.CCNumber      = CCNumber;
						AddUser.DateCreation  = DateCreation;
						AddUser.EmailID       = EmailID;
						AddUser.ExpiryDate    = ExpiryDate;
						AddUser.FirstName     = FirstName;
						AddUser.LastName      = LastName;
						AddUser.PhoneNumber   = PhoneNumber;
						AddUser.SecurityCode  = SecurityCode;
						AddUser.UserId        = UserId;
						
						users.add(AddUser);
						StoreUser();
						System.out.println("\n\n Following are the details of User");
						System.out.println("---------------------------------");
						
						 
						 System.out.println("User Id       : " +AddUser.UserId);
						
						 if(AddUser.FirstName != null)
					  	 System.out.println("First Name    : " +AddUser.FirstName);
						 
						 if(AddUser.LastName != null)
					  	 System.out.println("Last  Name    : " +AddUser.LastName);
					  	 
						 if(AddUser.EmailID != null)
					  	 System.out.println("Email Id      : " +AddUser.EmailID);
					  	 
						 if(AddUser.DateCreation != null)
					  	 System.out.println("Date Creation : " +AddUser.DateCreation);
					  	 
						 if(!(AddUser.ExpiryDate.equals(AddUser.DateCreation)))
					  	 System.out.println("Expire Date   : " +AddUser.ExpiryDate);
					  	 
						 if(AddUser.PhoneNumber != null)
					  	 System.out.println("Phone Number  : " +AddUser.PhoneNumber);
						 
						 if(AddUser.SecurityCode != null)
					  	 System.out.println("Security Code : " +AddUser.SecurityCode);
					  	 
						 if(AddUser.CCNumber != null)
					  	 System.out.println("CC Number     : " +AddUser.CCNumber);	
					
					}
					
					
					
			}
			
			else if(args[1].equals("view"))
			{
				if(args.length != 4)
				{	
					System.out.println("Arguements are not proper/complete: Usage is as follows ");
					System.out.println("user view --user_id xxxxx");
					System.out.println();
					return ;
				
				}	
				
				if(!(args[2].equals("--user_id")))
				{	
					System.out.println("Invalid Command " + args[1] );
					System.out.println("user view --user_id xxxxx");
					return ;
				
				}
								
				else
				{
			 	
				  ReadUsers();
				  boolean found =false;
				
				  for(User U:users)
				  {
					 if ( U.UserId == Integer.parseInt(args[3]))
					 {
					 	 found = true;
					 	 
					 	 System.out.println("User Id       : " +U.UserId);
					  	 
					 	 if(U.FirstName != null)
					 	 System.out.println("First Name    : " +U.FirstName);
					 	 
					 	 if(U.LastName != null)
					  	 System.out.println("Last  Name    : " +U.LastName);
					  	 
					 	 if(U.EmailID != null)
					 	 System.out.println("Email Id      : " +U.EmailID);
					  	 
					 	 if(U.DateCreation != null)
					 	 System.out.println("Date Creation : " +U.DateCreation);
					  	 
					 	 if (!(U.ExpiryDate.equals(U.DateCreation)))
					 	 System.out.println("Expire Date   : " +U.ExpiryDate);
					  	 
					 	 if(U.PhoneNumber != null)
					 	 System.out.println("Phone Number  : " +U.PhoneNumber);
					  	 
					 	 if(U.SecurityCode != null)
					 	 System.out.println("Security Code : " +U.SecurityCode);
					  	 
					 	 if(U.CCNumber != null)
					 	 System.out.println("CC Number     : " +U.CCNumber);
					  	 break;
					 }
				  }
				  
				  if(!found)
				  {
					  System.out.println();
					  System.out.println("No User present!!!!  Please enter proper User Id");
					  System.out.println();
				  }
				  
				}
				
					
			} 
			else if(args[1].equals("change"))
			{
				boolean UserPresent = false;
				ReadUsers();
				
				if(args.length < 3)
				{	
					System.out.println("arguements are not proper/complete: Usage is as follows ");
					System.out.println("user change --user_id xxxxx [ --first_name --last_name --email [ --cc_number --expiration_date --security_code --phone ]]");
					System.out.println();
					return ;
				 
				}
				
				
				if(!(args[2].equals("--user_id")))
				{
					System.out.println();
					System.out.println("Not Valid command : " + args[2]);
					System.out.println();
					return ;
				}
						
												
				for (User U : users)
				{
					//System.out.println(U.UserId);
					if(U.UserId == Integer.parseInt(args[3]))
					{
					  UserPresent = true; 
					  for(int i =4;i<args.length;i=i+2)
					  {
						if(args[i].equals("--first_name"))
						{
							if( i == args.length-1)
							{
							//	System.out.println();
					  			System.out.println("\nNo arguments for : " + args[i]);
					  			System.out.println(); 
					  			return ;	
								
							}
							
							U.FirstName = args[i+1];
						}
						else if(args[i].equals("--last_name"))
						{
							if( i == args.length-1)
							{
								System.out.println();
					  			System.out.println("\nNo arguments for : " + args[i]);
					  			System.out.println(); 
					  			return ;	
								
							}
							
							U.LastName = args[i+1];
						}
						else if(args[i].equals("--email"))
						{
							if( i == args.length-1)
							{
								System.out.println();
					  			System.out.println("\nNo arguments for : " + args[i]);
					  			System.out.println(); 
					  			return ;	
								
							}
							
							U.EmailID = args[i+1];
						}
						else if(args[i].equals("--cc_number"))
						{
							if( i == args.length-1)
							{
								System.out.println();
					  			System.out.println("\nNo arguments for : " + args[i]);
					  			System.out.println(); 
					  			return ;	
								
							}
							
							U.CCNumber = args[i+1];
						}
						else if(args[i].equals("--security_code"))
						{
							if( i == args.length-1)
							{
								System.out.println();
					  			System.out.println("\nNo arguments for : " + args[i]);
					  			System.out.println(); 
					  			return ;	
								
							}
							
							U.SecurityCode = args[i+1];
						}
						else if(args[i].equals("--phone"))
						{
							if( i == args.length-1)
							{
								System.out.println();
					  			System.out.println("\nNo arguments for : " + args[i]);
					  			System.out.println(); 
					  			return ;	
								
							}
							
							U.PhoneNumber = args[i+1];
						}
						else if(args[i].equals("--expiration_date"))
						{
							if( i == args.length-1)
							{
								System.out.println();
					  			System.out.println("\nNo arguments for : " + args[i]);
					  			System.out.println(); 
					  			return ;	
								
							}
							
							U.ExpiryDate = df.parse(args[i+1]);
						}
						else
						{
							System.out.println();
							System.out.println("Arguements are not proper/complete: Usage is as follows ");
							System.out.println("user change --user_id xxxx [ --first_name --last_name --email [ --cc_number --expiration_date --security_code --phone ]]");
							System.out.println();
						}
						
					 }
					  
					  System.out.println();
					  System.out.println("User with Id " +args[3]+" is updated" );
					  System.out.println();
					  StoreUser();
					
					  break;
					}
				}
							
				if(!UserPresent)
				{
				 System.out.println("Entered User does not exist");
			    } 
			}
			else
			{
				System.out.println();
				System.out.println("Invalid command : " + args[1]);
				System.out.println();
			}
			
			
		}
		else
		{
			System.out.println();
			System.out.println("Invalid command : " + Command);
			System.out.println();
			return ;
		}
			
		
		}


	public static void ParseXML(String FileName) {
		try { 

			File stocks = new File(FileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(stocks);
			doc.getDocumentElement().normalize();

			//System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
			NodeList nodes = doc.getElementsByTagName("hostel");
		//	System.out.println("==========================" + nodes.getLength());
			
									
			for (int i = 0; i < nodes.getLength(); i++) {
				 Node node = nodes.item(i);
			
				 Property PT = new Property();
				 
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				
				//System.out.println("Property Name: " + getValue("name", element));
				PT.SetPropertyName(getValue("name", element));
				
				//System.out.println("Street: " + getValue("street", element));
				PT.SetState(getValue("street", element));
				
				//System.out.println("City: " + getValue("city", element));
				PT.SetCityName(getValue("city", element));
				
				//System.out.println("State: " + getValue("state", element));
				PT.SetState(getValue("state", element));
				
				//System.out.println("Postal_code: " + getValue("postal_code", element));
				PT.SetPostalCode(getValue("postal_code", element));
				
				//System.out.println("Country: " + getValue("country", element));
				PT.SetCountry(getValue("country", element));
				
				//System.out.println("Phone: " + getValue("phone", element));
				PT.SetPhoneNumber(getValue("phone", element));
				
				//System.out.println("Email: " + getValue("email", element));
				PT.SetEmailId(getValue("email", element));
				
				//System.out.println("Facebook: " + getValue("facebook", element));
				PT.SetFaceBookID(getValue("facebook", element));
				
				//System.out.println("Web: " + getValue("web", element));
				PT.SetWebSite(getValue("web", element));
				
				//System.out.println("Check_in: " + getValue("check_in_time", element));
				String CheckIn = getValue("check_in_time", element);
				PT.SetCheck_In(getValue("check_in_time", element));
				
				//System.out.println("check_out: " + getValue("check_out_time", element));
				PT.SetCheck_Out(getValue("check_out_time", element));
				
				//System.out.println("smoking: " + getValue("smoking", element));
				PT.SetSmoking(getValue("smoking", element));
				
				//System.out.println("alcohol: " + getValue("alcohol", element));
				PT.SetAlcohol(getValue("alcohol", element));
				
				//System.out.println("cancellation_deadline: " + getValue("cancellation_deadline", element));
				PT.SetDedline(getIntegerValue("cancellation_deadline", element));
				
				//System.out.println("cancellation_penalty: " + getValue("cancellation_penalty", element));
				PT.SetPenalty(getValue("cancellation_penalty", element));
				
				
				NodeList inner_nodes = element.getElementsByTagName("availability");
			    
				
			//	System.out.println("==========================" + inner_nodes.getLength());

				for (int j = 0; j < inner_nodes.getLength(); j++) {
					Node inner_node = inner_nodes.item(j);
					Bed NewBed  = new Bed();
					boolean isBedPresent = false;

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) inner_node;
					
					//System.out.println("\n==========================");
					
										
					//System.out.println("room: " + getValue("room", element));
					NewBed.SetRoomNumber(getIntegerValue("room", element));
				//	System.out.println("room: " + NewBed.GetRoomNumber());
					
					//System.out.println("bed: " + getValue("bed", element));
					NewBed.SetBedNumber(getIntegerValue("bed", element));
				//	System.out.println("room: " + NewBed.GetBedNumber());
					
					for (Bed OldBed : PT.Beds)
					{
						if(OldBed.GetBedNumber() == getIntegerValue("bed", element) )
						{
							isBedPresent = true;
							NewBed = OldBed;
							break;
						}

					}
					
					//System.out.println("price: " + getValue("price", element));
					NewBed.AddRoomInformation(getProperDate("date", element,CheckIn),
							                  getIntegerValue("price", element));
					
										
				  	
				 
				}
				if(!isBedPresent)
			 	 PT.Beds.add(NewBed); 
				}
				
			}
			
			UpdateProperty(PT);
			//properties.add(PT);
			
			}
			} catch (Exception ex) {
				System.out.println();
				System.out.println("Entered file '"+ FileName + "' does not exist " );
				System.out.println();
				return ;
			}
		}

		private static String getValue(String tag, Element element) {
			NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
			Node node =  nodes.item(0);
			return node.getNodeValue();
		}
		
		private static int getIntegerValue(String tag, Element element) {
			NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
			Node node =  nodes.item(0);
			return Integer.parseInt(node.getNodeValue());
		}
		
		private static Date getProperDate(String tag, Element element,String CheckIn) {
			NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
			Node node = nodes.item(0);
			node.getNodeValue();
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			//String dateInString = node.getNodeValue()+CheckIn;
			String dateInString = node.getNodeValue();
			Date date = new Date();
			
			try {
		 		date = formatter.parse(dateInString);
		 		return date;			 
			} catch (ParseException e) {
				e.printStackTrace();
				return date;
			}finally{
				
				//System.out.println(date);
			}
			
			
		}

		
		private static void UpdateProperty(Property PT) throws IOException, ClassNotFoundException
		{
		boolean found = false;	
		Property PFound = new Property();
		ReadProperty();
		
		
		 for(Property TempP : properties){
			 
			// System.out.println(TempP.GetPropertyName());
			// System.out.println(PT.GetPropertyName());
			 
			 if(TempP.GetPropertyName().equals(PT.GetPropertyName()) &&
				TempP.GetCityName().equals(PT.GetCityName())	 )
			 {
				 PFound = TempP;
				 found = true;
				 break;
			 }
				 
		 }
		 
		 if(!found)
		 {
		  System.out.println();
		  System.out.println("Its new Hostel. So adding it to database");
		  System.out.println();
		  properties.add(PT);
		 
		 }
		  else
		 {
			System.out.println();
			System.out.println("Hostel already exist so updating its inventory");
			System.out.println();
			
			PFound.SetAlcohol(PT.GetAlcohol());
			PFound.SetCheck_In(PT.GetCheck_In());
			PFound.SetCheck_Out(PT.GetCheck_Out());
			PFound.SetStreetName(PT.GetStreetName());
			PFound.SetPhoneNumber(PT.GetPhoneNumber());
			PFound.SetEmailId(PT.GetEmailId());
			PFound.SetFaceBookID(PT.GetFaceBookID());
			PFound.SetWebSite(PT.GetWebSite());
			PFound.SetSmoking(PT.GetSmoking());
			
			for (Bed B: PT.Beds)
			{
				for (Bed FB : PFound.Beds)
				{
					if(B.GetBedNumber() == FB.GetBedNumber())
					{
						FB.SetRoomNumber(B.GetRoomNumber());
						
						
						for (Map.Entry<Date,Bed.BedInformation> entry : B.BedInfo.entrySet())
						{
							Date key =  entry.getKey();
							//System.out.println(key);
							
													
							Bed.BedInformation FBentry = FB.BedInfo.get(key);
							
							if( FBentry==null)
							{
								Bed.BedInformation newFBentry =  FB.new BedInformation();
								newFBentry.Booked    = false;
								newFBentry.BookingId = 0;
								newFBentry.Price     = entry.getValue().Price;
								FB.BedInfo.put(key, newFBentry);
								
							}
							
							else
							if (!(FBentry.Booked))
							{
								FBentry.Price = entry.getValue().Price;
								
							}
													
						}
						
						
					}
				}
				
			}
			
			
			
		 }
			
		}
		
	
}
