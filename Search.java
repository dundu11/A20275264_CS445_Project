import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;


public class Search implements Serializable{

private String SearchCity;
private int    NumberOfBeds;
private Date   StartDate;
private Date   EndDate;

private boolean IsContinuous;

static final long serialVersionUID = 100L;

List<Property> SelectedProp = new ArrayList<Property>();

Map<String,List<BedInfo>> BedMap = new HashMap<String,List<BedInfo>>(); 

public List<SearchResults>  SR = new ArrayList<SearchResults>();


BookRoom BR = new BookRoom();

public String GetSearchCity(){
	return SearchCity;
}

public Date GetStartDate(){
	return StartDate;
}

public Date GetEndDate(){
	return EndDate;
}

public int GetNumberOfBeds(){
	return NumberOfBeds;
}


public void SimpleSearch(String City,Date Start,Date End,int BedCount,List<Property> properties)
{
	
	SearchCity    =  City;
	NumberOfBeds  =  BedCount;
	StartDate     =  Start;
	EndDate       =  End;

	SR.clear();
	BedMap.clear();
	
	SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	df.setLenient(false);
    
		
	if ( City != null)
	{	
	  for(Property P : properties)
	  {
	        if (P.GetCityName().equals(SearchCity))
	   	        	SelectedProp.add(P);
	        	        	
	  }
	}
	else
	{	
		for(Property P : properties)
		  {
		   SelectedProp.add(P);
		  }
	}	
	
	for(Property P : SelectedProp)
	{
		 System.out.println(P.GetPropertyName() +"," + P.GetCityName());
		 		 
		 Calendar start = Calendar.getInstance();
    	 start.setTime(StartDate);
    	 Calendar end = Calendar.getInstance();
    	 end.setTime(EndDate);
    	 
    	 
    	 for (Date date = start.getTime(); !start.after(end); 
    			  start.add(Calendar.DATE, 1), date = start.getTime())
    	 {
    		 int MinPrice = 999999999;
    		 int MaxPrice = 0;
    		 int count    = 0;
    		 
    		 String SD = df.format(date);
    	//	 Date nextDate = (Date)start.add(Calendar.DATE, 1);
    	//	 String ED = df.format(nextDate);  
    		 
    		 for(Bed BD :P.Beds)
    		 {
    			 Bed.BedInformation  BI = BD.BedInfo.get(date);
    			 if(!BI.Booked)
    			 {
    			   count++;	 
    		       
    			   if (MinPrice > BI.Price)
    				  MinPrice = BI.Price;
    				  
    			   if(MaxPrice < BI.Price)
    				  MaxPrice = BI.Price;
    				 
    			 }
    		 
    		 }
    		 System.out.print(SD +" to " + SD +": "  + count + " Beds available between ");
    		 System.out.println("$"+ MinPrice + " and $" + MaxPrice);
    		 
    	   		 
    		 
    	 }
	      	        	        	
	
    	  System.out.println("------------------------------------");
	}
	
}

public void SearchBeds(String City,Date Start,Date End,int BedCount,List<Property> properties)
{
	SearchCity    =  City;
	NumberOfBeds  =  BedCount;
	StartDate     =  Start;
	EndDate       =  End;
	
	SR.clear();
	BedMap.clear();
	
	SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	df.setLenient(false);
    
	String SD = df.format(StartDate);
    String ED = df.format(EndDate);
    
      for(Property P : properties)
	  {
	        if (P.GetCityName().equals(SearchCity))
	   	        	SelectedProp.add(P);
	        	        	
	  }
	 
	  //System.out.println("SelectedProp Size :" + SelectedProp.size());
	  
	  if(SelectedProp.isEmpty()){
		  System.out.println(" No Property exist in "+City+" city!!!!!!!!!!!");
		  return ;
	  }
	  
	  IsContinuous = false;
	  

	  for(Property  SP: SelectedProp)
	  {
		  boolean IsAvail = true;
		  
		  List<BedInfo>  BedList = new ArrayList<BedInfo>();
		  
		  for(Bed BD :SP.Beds){
			  
			  IsAvail=true;
			  
			  BedInfo LC = new BedInfo();
	    	 	        	
	    	 Calendar start = Calendar.getInstance();
	    	 start.setTime(StartDate);
	    	 Calendar end = Calendar.getInstance();
	    	 end.setTime(EndDate);
	    	 
	    	 
	    	 for (Date date = start.getTime(); !start.after(end); 
	    			  start.add(Calendar.DATE, 1), date = start.getTime())
	    	 {
	    		 Bed.BedInformation  BI = BD.BedInfo.get(date);
	    		 
	    		 if (BI.Booked)
	    		 {	 
	    			IsAvail = false;
	    			break;
	    		 }
	    	 }
	    		
	    	 if (IsAvail)
	    	 {
	    		 Bed.BedInformation  BI = BD.BedInfo.get(Start);
	    		 
	    		 LC.BedNumber   = BD.GetBedNumber();
    			 LC.RoomNumber  = BD.GetRoomNumber(); 
    			 LC.Price      += BI.Price;
    			 LC.PropertyName = SP.GetPropertyName();
    			 
    			// System.out.println("LC.BedNumber    ==" + LC.BedNumber);
				// System.out.println("LC.PropertyName ==" + LC.PropertyName );
					
    			   			 
    			 BedList.add(LC);   			 
	    	 
	    	 }
	    	   	   	 
	     }
		 
		  if(BedList.size() >= BedCount)
			  IsContinuous = true;
			  
		//    for( BedInfo B : BedList){
		//		 System.out.println(B.BedNumber+ "  "+ SP.GetPropertyName());
		//	 }
		  
		//  System.out.println("  "+ SP.GetPropertyName());
		  BedMap.put(SP.GetPropertyName(), BedList);
		  
		 // System.out.println("------------------------------------"); 
		  	  	  	        	        	
	  }
	  
	  if (NumberOfBeds == 0)
	  {
	    int MinPrice = 999999999;
	    int MaxPrice = 0;
	    
		Set<String> Keys = BedMap.keySet();
	    Iterator itr = Keys.iterator();
	   
	    String key;
	   
	    while(itr.hasNext()) {
		  
		  key = (String)itr.next();
		  //System.out.println(key);
        
		  List<BedInfo> TempBI = BedMap.get(key);
		  
		      
		  if(!TempBI.isEmpty())
		  {
			System.out.println("\nProperty -->"+ key);
			System.out.println("--------------------------");
			System.out.print(SD +" to " + ED +": "  + TempBI.size() + " Beds available between ");
		  	  
		    for(BedInfo B:TempBI)
		    {
			  if (MinPrice > B.Price)
				  MinPrice = B.Price;
			  
			  if(MaxPrice < B.Price)
				  MaxPrice = B.Price;
		   }
		  
		    System.out.println("$"+ MinPrice + " and $" + MaxPrice);
		  }
		  else
		  {	  
			  System.out.print("No Beds availabe in any property from " +SD +" to "+ED);
		  
		  }
	   }
	 	System.out.println("------------------------");
	 	System.out.println();
	  }  
	  
	  else
	  ConstructSearchResults();	 
	  
}



void ConstructSearchResults(){

	if(IsContinuous){
	
	  Set<String> Keys = BedMap.keySet();
	   Iterator itr = Keys.iterator();
	   
	   String key;
	   
	   while(itr.hasNext()) {
		  
		  key = (String)itr.next();
		  //System.out.println(key);
        
		  List<BedInfo> BI = BedMap.get(key);
			
			if(BI.size() >= NumberOfBeds){
				int k = BI.size() / NumberOfBeds;
				
				for(int i=0;i<k;i++)
				{
					SearchResults SR1 = new SearchResults();
					SR1.PropertyID = BI.get(0).PropertyName;
					SR1.SearchID   = BI.get(0).PropertyName + "#"+BI.get(i*NumberOfBeds).BedNumber;
									
				    for(int j=0;j<NumberOfBeds;j++)
				    {
					   SR1.Beds.add(BI.get(j+(NumberOfBeds)*i));
				    }
				    
				    				   
				    SR.add(SR1);
			   }					
				
			
		}
		
	}
	
	
 }
	
	if(!SR.isEmpty()){
		
		//System.out.println(" SR size " + SR.size());
		
		System.out.println("\n Search Results");
		System.out.println("\n --------------");
		System.out.println("\n Search ID                 Property                        Beds");
		
		for(SearchResults S:SR)
		{
			System.out.print(S.SearchID);
			System.out.print("      "+S.PropertyID+"      ");
			for(BedInfo B:S.Beds){
			
				System.out.print(B.BedNumber+",");	
			}
			System.out.println();
			
		}
		
	
	}
	else
		System.out.println("\n No property found. Please do another search");
	
	
	}
}

