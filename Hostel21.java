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
	       System.out.println("File Not Found");
	       
	   }finally {
	    	if(in != null){
	    		 in.close();
	    	}
	   }

	}

	public static void StoreSR() throws IOException, ClassNotFoundException 
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
	       System.out.println("File Not Found");
	       
	   }finally {
	    	if(in != null){
	    		 in.close();
	    	}
	   }

	}


	
	public static void main(String[] args)  throws IOException, ClassNotFoundException
	{
		String Command = null;
		String City    = null;
		String Start   = null;
		String End     = null;
		int    beds    = 0;
		
		Date StartDate = new Date();
		Date EndDate   = new Date();
	    
				
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
		  	 
		  	 if ((Start != null) || (End != null))
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
		  	
		  	System.out.println("      Searching Details     ");
		    System.out.println("----------------------------");
		    if(City != null)
		    System.out.println("City Name      : " + City);
		    if(Start != null)
		    System.out.println("Start Date     : " + StartDate.toString());
		    if(End != null)
		    System.out.println("End Date       : " + EndDate.toString());
		    if(beds != 0)
		    System.out.println("Number of Beds : " + beds);
		  	
		    System.out.println("\nSearching Results :");
			System.out.println("\n--------------------");
		    
			if ((City != null) && (Start != null) && (End != null) && (beds != 0) )
			{
				srh.SearchBeds(City,StartDate,EndDate,beds,properties);
				//System.out.println("StoreSR");
				StoreSR();
			}
			else
			{	
			   srh.SimpleSearch(City,StartDate,EndDate,beds,properties);	
			   
			}
						
		}
		
		else if (Command.equals("admin"))
		{
			if(args[1].equals("load"))
			{
				  properties.clear();
				  ParseXML(args[2]);
				  StoreProperty();
				  
			}
			else if (args[1].equals("revenue"))
			{
				//Need to Do
			}
			if(args[1].equals("occupancy"))
			{
				//Need to Do
			}
			
		}
		
		else if (Command.equals("book"))
		{
			if(args[1].equals("add"))
			{
				ReadSR();
				ReadProperty();
	/*			
				System.out.println("Size is : "+ srh.SR.size());
				for (SearchResults S : srh.SR)
				{	
					for(BedInfo B : S.Beds)
		  		     System.out.println(B.BedNumber + " "+B.Price);
					
					System.out.println("------------------------");
				
				}
		*/		
				SearchResults S1 = new SearchResults ();
				Boolean ProperSearchName = false;
				BookRoom BR = new BookRoom();
				
				for(SearchResults S : srh.SR){
					
					System.out.println("Search Id : " + S.SearchID);
					
					if( S.SearchID.equals(args[2]))
					{
						ProperSearchName = true;
						S1=S;
						break;
						
					}
				}
				
				if(ProperSearchName)
				{	
			  	  BR.DoBooking(srh.GetSearchCity(),srh.GetStartDate(),srh.GetEndDate(),S1,properties);
			  	  StoreProperty();
				}
			  	 else
					System.out.println("Please Enter Proper Search Id : " + args[2] );
				
			}
			
			else if(args[1].equals("cancel"))
			{
				//To do
				System.out.println("Cancel booking");
			}
			
			else if(args[1].equals("view"))
			{
				//To do
				System.out.println("View booking");
			}
		
		}
	
	
				
		/*
		int choice = 0;
		Scanner in = new Scanner(System.in);
 	    do
		{
		  System.out.println();
		  System.out.println("<------------MENU------------>");
		  System.out.println("1 -----> Load XML file ");
		  System.out.println("2 -----> Store Property ");
		  System.out.println("3 -----> Display Property Details ");
		  System.out.println("4 -----> Search Property  ");
		  
		  System.out.println("0 -----> Exit ");
		  
		  System.out.println("Enter your choice--> ");
		  choice =  in.nextInt();
		  
		  switch (choice)
		  {
		  case 1:
		  {
			  properties.clear();
			  ParseXML("hostel.xml");
			  break;
		  }

		  case 2:
		  {
			  StoreProperty();
			  break;
		  }

		  case 3:
		  {
			  System.out.println("Displaying proprety Details :");
			  System.out.println("=============================");
			  
			  ReadProperty();
			  
			  for(Property p : properties)
		        p.DisplayPropertyDetails(); 
		      
			  break;
		  }
		  case 4:
		  {
			 
			  ReadProperty();
			  System.out.println("Searching Property for booking :");
		      Search srh = new Search();
		      
		      String Name;
		      Date StartDate=null,EndDate=null;
		      int beds;
		      
		      System.out.println("Enter city,Start Date(MM/DD/YYYY), End Date (MM/DD/YYYY) and number of beds");
		      Name = in.next();
              
		                    
		      try {
		    
		     Date Today = new Date();
	         //System.out.println("Today's Date "  + df.format(TempDate));
	              
		      SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		      df.setLenient(false);
		      
		      String dateString = in.next();
              StartDate = (Date) df.parse(dateString);
              
                            
              if(StartDate.before(Today))
              {
            	  System.out.println("Entered Date is past date");
            	  System.out.println("Please enter proper date ");
            	  break;
              }
              
              dateString = in.next();
              EndDate = (Date) df.parse(dateString);
              
              if(EndDate.before(Today))
              {
            	  System.out.println("Entered Date is past date");
            	  System.out.println("Please enter proper date ");
            	  break;
              }
              
              if(EndDate.before(StartDate) || EndDate.equals(StartDate))
              {
            	  System.out.println("End Date is past start date");
            	  System.out.println("Please enter proper date ");
            	  break;
              }
              
           
                                         
		      } catch(ParseException pe) {
	                System.out.println("Date format or entered is not proper");
	                System.out.println("Please enter proper date ");
	                
                    break;        
               }
		      
               beds = in.nextInt();
               
              		      
		       System.out.println("      Searching Details     ");
		       System.out.println("----------------------------");
		       System.out.println("City Name      : " + Name);
		       System.out.println("Start Date     : " + StartDate.toString());
		       System.out.println("End Date       : " + EndDate.toString());
		       System.out.println("Number of Beds : " + beds);
		       
		       srh.SearchBeds(Name,StartDate,EndDate,beds,properties);
		      
			  break;
		  }
		  default:
			  break;
		  
		  }
		  
		  
			
		}while(choice != 0);
		
		System.out.println("Program ends ");
		if (in != null) 
		   in.close();
		*/
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
			System.out.println("==========================" + nodes.getLength());
			
									
			for (int i = 0; i < nodes.getLength(); i++) {
				 Node node = nodes.item(i);
			
				 Property PT = new Property();
				 
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				
				System.out.println("Property Name: " + getValue("name", element));
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
				PT.SetCheck_In(getValue("check_in_time", element));
				
				//System.out.println("check_out: " + getValue("check_out_time", element));
				PT.SetCheck_Out(getValue("check_out_time", element));
				
				//System.out.println("smoking: " + getValue("smoking", element));
				PT.SetSmoking(getValue("smoking", element));
				
				//System.out.println("alcohol: " + getValue("alcohol", element));
				PT.SetAlcohol(getValue("alcohol", element));
				
				NodeList inner_nodes = element.getElementsByTagName("availability");
			    
				
				System.out.println("==========================" + inner_nodes.getLength());

				for (int j = 0; j < inner_nodes.getLength(); j++) {
					Node inner_node = inner_nodes.item(j);
					Bed NewBed  = new Bed();

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) inner_node;
					
					//System.out.println("\n==========================");
					
										
					//System.out.println("room: " + getValue("room", element));
					NewBed.SetRoomNumber(getIntegerValue("room", element));
				//	System.out.println("room: " + NewBed.GetRoomNumber());
					
					//System.out.println("bed: " + getValue("bed", element));
					NewBed.SetBedNumber(getIntegerValue("bed", element));
				//	System.out.println("room: " + NewBed.GetBedNumber());
					
					//System.out.println("price: " + getValue("price", element));
					NewBed.AddRoomInformation(getProperDate("start_date", element),
							                  getProperDate("end_date", element),
							                  getIntegerValue("price", element));
					
					
					
				}
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
		
		private static Date getProperDate(String tag, Element element) {
			NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
			Node node = nodes.item(0);
			node.getNodeValue();
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			String dateInString = node.getNodeValue();
			Date date = new Date();
			
			try {
		 		date = formatter.parse(dateInString);
		 		return date;			 
			} catch (ParseException e) {
				e.printStackTrace();
				return date;
			}finally{
				
			}
			
			
		}

		
		private static void UpdateProperty(Property PT)
		{
		boolean found = false;	
		Property PFound;
		 for(Property TempP : properties){
			 if(TempP.GetPropertyName().equals(PT.GetPropertyName())){
				 PFound = TempP;
				 found = true;
				 break;
			 }
				 
		 }
		 
		 if(!found)
		  properties.add(PT);
		 else
		 {
			//To be done
			 System.out.println(" Update inventory");
		 }
			
		}
		
	
}
