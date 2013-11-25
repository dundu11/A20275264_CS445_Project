import java.io.Serializable;
import java.text.ParseException;
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

Map<String,List<BedInfo>> BedMap = new TreeMap<String,List<BedInfo>>(); 

public List<SearchResults>  SR = new ArrayList<SearchResults>();


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


public void SimpleSearch(String City,String SDate,String EDate,int BedCount,List<Property> properties) throws ParseException 
{
	
	SearchCity    =  City;
	NumberOfBeds  =  BedCount;
	StartDate     =  new Date();
	EndDate       =  new Date();
 
	BedMap.clear();
	SelectedProp.clear();

	SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
	df1.setLenient(false);
	
	if ( SDate != null)
	{
		StartDate = df1.parse(SDate);
	}
				
	if ( EDate != null)
	{
		EndDate = df1.parse(EDate);
		
	}
	else
	{
		 Calendar c = Calendar.getInstance(); 
		 c.setTime(StartDate); 
		 c.add(Calendar.DATE, 720);
		 
		 EndDate = c.getTime();
		 
	}
		
	
			
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
	
	 if(SelectedProp.isEmpty()){
		  System.out.println(" No Property exist in "+City+" city!!!!!!!!!!!");
		  return ;
	  }
	
	for(Property P : SelectedProp)
	{
		 System.out.println(P.GetPropertyName() +"," + P.GetCityName());
		 boolean availFlag = true;
		 		 
		 Calendar start = Calendar.getInstance();
		 start.setTime(StartDate);
		 
    	 Calendar end = Calendar.getInstance();
    	 end.setTime(EndDate);
    	 
    	 Map<Date,SimpleSearchResults> SelectedBeds = new TreeMap<Date,SimpleSearchResults>();    		 
    	     	 
    	   	    	 
    	 for(Bed BD :P.Beds)
		 {
    		     		 
    		// int MinPrice = 999999999;
    		// int MaxPrice = 0;
    		 
    		    		 
    		Map<Date,Bed.BedInformation> SortedBedInfo= new TreeMap<>(BD.BedInfo); 
    	   
    	   
    	   for (Map.Entry<Date,Bed.BedInformation> entry : SortedBedInfo.entrySet())
    	   {
    		   Date key =  entry.getKey();
    	       
    	       
    	       if(StartDate.after(key))
    	           continue;
    	       
    	       
    		 if (EndDate.before(key))
    		 { 	 
    			 break;
    		 }
    		 
    		 if(!SortedBedInfo.get(key).Booked)
  			 {
    		   if (SelectedBeds.get(key) == null)
    		   {
    			   SimpleSearchResults SSR =new SimpleSearchResults();
    			   
    			   SSR.Count = 1;
    			   SSR.MaxPrice = SortedBedInfo.get(key).Price;
    			   SSR.MinPrice = SortedBedInfo.get(key).Price;		   
    		     
    			   SelectedBeds.put(key, SSR);
    		   }
    		   else	  
  			   if(SelectedBeds.get(key) != null)
  			   {	   
  				  
  				  SimpleSearchResults  SSR   = SelectedBeds.get(key);
  				  SSR.Count++;
  				  				  				  				 
  		       
  			     if (SSR.MinPrice > BD.BedInfo.get(key).Price)
  				     SSR.MinPrice = BD.BedInfo.get(key).Price;
  				  
  			     if(SSR.MaxPrice < BD.BedInfo.get(key).Price)
  				     SSR.MaxPrice = BD.BedInfo.get(key).Price;
  			     
  			      SelectedBeds.put(key, SSR);
  			   }
  			       
    		     			       
  			   
  			  }
    		   
    	   }
    	   
   	 
    		 
    	}
    	 
    	 
    	 for (Map.Entry<Date,SimpleSearchResults> entry : SelectedBeds.entrySet())
    	 {
    		 Date key =  entry.getKey();
    		 
    		 SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    		 df.setLenient(false);
    		 
    		 Calendar c = Calendar.getInstance(); 
    		 c.setTime(key); 
    		 c.add(Calendar.DATE, 1);
    		 Date NextDate = c.getTime();
    		 
    		 
    		     		 
    		 if( key.before(EndDate))
      		 {	 
      		   if(SelectedBeds.get(key).Count == 0)
      		   {
      			 System.out.print(df.format(key) + " to "+df.format(NextDate) + " : " +"No beds available"); 
      		   }
      		   else
      		   {
    		   System.out.print(df.format(key) + " to "+df.format(NextDate) + " : " + SelectedBeds.get(key).Count + " Beds available between ");
      		   System.out.println("$"+ SelectedBeds.get(key).MinPrice + " and $" + SelectedBeds.get(key).MaxPrice);
      		   availFlag = false;
      		   }
      		 }	
    		    	 
    	 }
    	 
    	 if(availFlag)
    		System.out.println("Sorry ! All beds have been booked");
    	 
    	 System.out.println();
    	 
    	  
	}
	
}

public void SearchBeds(String City,Date Start,Date End,int BedCount,List<Property> properties) throws ParseException
{
	SearchCity    =  City;
	NumberOfBeds  =  BedCount;
	StartDate     =  Start;
	EndDate       =  End;
	
	SR.clear();
	BedMap.clear();
	
	SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	df.setLenient(false);
    
	//String SD = df.format(StartDate);
    //String ED = df.format(EndDate);
    
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
			  //System.out.println("Inside Search " + BD.GetBedNumber());
			  int SearchPrice = 0;
			  	    	 	        	
	    	 Calendar start = Calendar.getInstance();
	    	 start.setTime(StartDate);
	    	 Calendar end = Calendar.getInstance();
	    	 end.setTime(EndDate);
	    	 
	    	 
	    	 
	    	 for (Date date = start.getTime(); !start.equals(end); 
	    			  start.add(Calendar.DATE, 1), date = start.getTime())
	    	 {
	    	    
	    		// System.out.println("Inside Search " + date);
	    		 if(BD.BedInfo.get(date) == null)
	    		 {
	    			 IsAvail = false;
		    			break;
	    		 }
	    		 else
	    		 if (BD.BedInfo.get(date).Booked)
	    		 {	 
	    			IsAvail = false;
	    			break;
	    		 }
	    		 else
	    			 SearchPrice += BD.BedInfo.get(date).Price;
	    	 }
	    		
	    	 if (IsAvail)
	    	 {
	    		 Bed.BedInformation  BI = BD.BedInfo.get(Start);
	    		 //System.out.println("Inside Search " + BD.GetBedNumber());
	    		 BedInfo LC = new BedInfo();
	    		 
	    		 LC.BedNumber   = BD.GetBedNumber();
    			 LC.RoomNumber  = BD.GetRoomNumber(); 
    			 LC.Price       = SearchPrice;
    			 LC.PropertyName = SP.GetPropertyName();
    			 
    			//System.out.println("LC.BedNumber    ==" + LC.BedNumber);
				//System.out.println("LC.PropertyName ==" + LC.PropertyName );
					
    			   			 
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
	  
	  if(IsContinuous)
	    ConstructSearchResults();	 
	  else
	  {
		System.out.println("Sorry !! No contiguous Beds found !!!!");
		System.out.println("Check following availibility \n");
		
		SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
		df1.setLenient(false);
		
		SimpleSearch(City,df1.format(Start),df1.format(End),BedCount, properties);
	  
	  }
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
					SR1.price = 0;
					SR1.PropertyID = BI.get(0).PropertyName;
					SR1.SearchID   = BI.get(0).PropertyName + "#"+BI.get(i*NumberOfBeds).BedNumber;
									
				    for(int j=0;j<NumberOfBeds;j++)
				    {
					   SR1.Beds.add(BI.get(j+(NumberOfBeds)*i));
					   //System.out.println(BI.get(j+(NumberOfBeds)*i).Price);
					   SR1.price += BI.get(j+(NumberOfBeds)*i).Price;
					   SR1.Rooms.add(BI.get(j+(NumberOfBeds)*i).RoomNumber);
				    }
				    
				    				   
				    SR.add(SR1);
			   }					
				
			
		}
		
	}
	
	
 }
	
	if(!SR.isEmpty()){
		
		//System.out.println(" SR size " + SR.size());
						
		//System.out.println("\n Search Results");
		//System.out.println("\n --------------");
		String SP = null;
		String SC = null;
				
		for(SearchResults S:SR)
		{
			if(!((S.PropertyID.equals(SP)) &&(SearchCity.equals(SC))))
			{	
			//	System.out.println();
			  System.out.println(S.PropertyID+", "+ SearchCity );
			  SP =  S.PropertyID;
			  SC =  SearchCity;
			}
			System.out.print("Search_id : "+S.SearchID+" ,");
			System.out.print("$"+S.price+", Rooms ");
			
			for (Integer element : S.Rooms) {
				
			    System.out.print("#"+element.intValue()+ " ");
			    			
			}
			
			System.out.println();
			
		}
		
		System.out.println();
		
	
	}
	else
		System.out.println("\n No property found. Please do another search");
	
	
	}
}
 
