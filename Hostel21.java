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
	    	//System.out.println("File Not Found");
	       
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
		
		//DatabaseConnection Connect = new DatabaseConnection();
				
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		df.setLenient(false);
		
		Command = args[0];
		if(Command.equals("search") || Command.equals("Search"))
		{
			ReadProperty();
			
			if(properties.isEmpty())
		  	{
		  	  System.out.println("Hostel properties are not loaded !!!!! ");
		  	  		return ;
		  	}
			for(int i =1;i<args.length;i++){
		  		if(args[i].equals("--city"))
		  			City = args[i+1];
		  		if (args[i].equals("--start_date"))
		  			Start = args[i+1];
		  		if (args[i].equals("--end_date"))
		  			End = args[i+1];
		  		if (args[i].equals("--beds"))
		  			beds = Integer.parseInt(args[i+1]);
		  		
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
           	      return;
               }
		  	 }
		  	 
		  	 if(End != null)
		  	 {
		  		 EndDate   =  df.parse(End);
		  		
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
           	       System.out.println("End Date is past start date");
           	       System.out.println("Please enter proper date ");
           	        return;
                 }
		  	 }

		  	}
		  	catch(ParseException pe) {
                System.out.println("Date format or entered is not proper");
                System.out.println("Please enter proper date ");
                                     
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
			if(args[1].equals("load"))
			{
				 properties.clear();
				  ParseXML(args[2]);
				 // System.out.println(properties.size());
				  //Connect.StoreUser(properties);
				  StoreProperty();
				  //Connect.getData();
				  
			}
			else if (args[1].equals("revenue"))
			{
				BookRoom BR = new BookRoom();
				ReadProperty();
				BR.GetRevenue(args[2],args[3],properties);
			}
			if(args[1].equals("occupancy"))
			{
				//Need to Do
				BookRoom BR = new BookRoom();
				ReadProperty();
				BR.GetOccupancy(args[2],args[3],properties);
			}
			
		}
		
		else if (Command.equals("book"))
		{
			BookRoom BR = new BookRoom();
			
			if(args[1].equals("add"))
			{
				ReadSR();
				ReadProperty();
				ReadUsers();
			
				SearchResults S1 = new SearchResults ();
				Boolean ProperSearchName = false;
				Boolean UserFound        = false;
				
				
				for(User U : users)
				{
					if( U.UserId == Integer.parseInt(args[5]))
					{
					   UserFound = true;
					}
					
				}
				
				if(!UserFound)
				{
					System.out.println("\n User does not exist. Please enter proper User");
					return;
				}
				
				for(SearchResults S : srh.SR){
					
							
					if( S.SearchID.equals(args[3]))
					{
						ProperSearchName = true;
						S1=S;
						break;
						
					}
				}
				
				if(ProperSearchName)
				{	
			  	  BR.DoBooking(srh.GetSearchCity(),srh.GetStartDate(),srh.GetEndDate(),S1,properties,args[5],users);
			  	  StoreProperty();
				}
			  	 else
					System.out.println("Please Enter Proper Search Id : " + args[3] );
				
			}
			
			else if(args[1].equals("cancel"))
			{ 
				//To do
				ReadProperty();
				System.out.println("Cancel booking");
				BR.CancelBooking(args[3],properties);
				StoreProperty();
			}
			
			else if(args[1].equals("view"))
			{
				//To do
				System.out.println("View booking");
				BR.ViewBooking(args[3]);
			}
		
		}
		else if(Command.equals("user"))
		{
						
			if(args[1].equals("add"))
			{
				
				String  FirstName    = null;
				String  LastName     = null; 
				String  EmailID      = null;
				String  CCNumber     = null;
				String  SecurityCode = null;
				String  PhoneNumber  = null;
				
				Date    DateCreation = new Date();;
				Date    ExpiryDate   = new Date();;
												
				System.out.println("UserId --> " + UserId);
				
				for(int i =2;i<args.length;i++)
				{
					if(args[i].equals("--first_name"))
					{
						FirstName = args[i+1];
					}
					else if(args[i].equals("--last_name"))
					{
						LastName = args[i+1];
					}
					else if(args[i].equals("--email"))
					{
						EmailID = args[i+1];
					}
					else if(args[i].equals("--cc_number"))
					{
						CCNumber = args[i+1];
					}
					else if(args[i].equals("--security_code"))
					{
						SecurityCode = args[i+1];
					}
					else if(args[i].equals("--phone"))
					{
						PhoneNumber = args[i+1];
					}
					else if(args[i].equals("--expiration_date"))
					{
						ExpiryDate = df.parse(args[i+1]);
					}
				}
				
				    					
		    		ReadUsers();
		    		boolean UserPresent = false;
		    				    		
					for (User U : users)
					{
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
				if(args[2].isEmpty())
				{
					System.out.println("User ID is empty !!!!!!!!");
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
					  System.out.println("No User present!!!!  Please enter proper User Id");  
				  }
				  
				}
				
					
			}
			else if(args[1].equals("change"))
			{
				boolean UserPresent = false;
				ReadUsers();
				//System.out.println(args[2]);
								
				for (User U : users)
				{
					//System.out.println(U.UserId);
					if(U.UserId == Integer.parseInt(args[2]))
					{
					  UserPresent = true;
					  for(int i =3;i<args.length;i++)
					  {
						if(args[i].equals("--first_name"))
						{
							U.FirstName = args[i+1];
						}
						else if(args[i].equals("--last_name"))
						{
							U.LastName = args[i+1];
						}
						else if(args[i].equals("--email"))
						{
							U.EmailID = args[i+1];
						}
						else if(args[i].equals("--cc_number"))
						{
							U.CCNumber = args[i+1];
						}
						else if(args[i].equals("--security_code"))
						{
							U.SecurityCode = args[i+1];
						}
						else if(args[i].equals("--phone"))
						{
							U.PhoneNumber = args[i+1];
						}
						else if(args[i].equals("--expiration_date"))
						{
							U.ExpiryDate = df.parse(args[i+1]);
						}
						
					 }
					  StoreUser();
					
					  break;
					}
				}
							
				if(!UserPresent)
				{
				 System.out.println("Entered User does not exist");
			    } 
			}
			
			
		}
			
		}


	public static void ParseXML(String FileName) {
		try {

			File stocks = new File(FileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(stocks);
			doc.getDocumentElement().normalize();

			System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
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
				
				System.out.println("cancellation_deadline: " + getValue("cancellation_deadline", element));
				PT.SetDedline(getIntegerValue("cancellation_deadline", element));
				
				System.out.println("cancellation_penalty: " + getValue("cancellation_penalty", element));
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
				ex.printStackTrace();
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
			 
			 System.out.println(TempP.GetPropertyName());
			 System.out.println(PT.GetPropertyName());
			 
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
		  System.out.println("Its new property. So adding it");
		  properties.add(PT);
		 
		 }
		  else
		 {
			System.out.println("Property already present so updating its inventory");
			
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
						
						if(FB.StartingDate.after(B.StartingDate))
							   FB.StartingDate = B.StartingDate;
							
						if( FB.EndingDate.before(B.EndingDate))
							   FB.EndingDate = B.EndingDate;
						
						for (Map.Entry<Date,Bed.BedInformation> entry : B.BedInfo.entrySet())
						{
							Date key =  entry.getKey();
							
													
							Bed.BedInformation FBentry = FB.BedInfo.get(key);
							
							if(!FBentry.Booked)
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
