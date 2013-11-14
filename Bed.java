import java.io.Serializable;
import java.util.*;


public class Bed implements Serializable
{
 private int RoomNumber;
 private int BedNumber;
   
 public class BedInformation implements Serializable
 {
	 int     Price;
	 boolean Booked; 
	 int  BookingId;
 }
 
 public Map<Date,BedInformation> BedInfo= new HashMap<Date,BedInformation>(); 
 
 public void BedDetails()
 {
		
	 for (Map.Entry<Date,BedInformation> entry : BedInfo.entrySet())
	  {
		Date key =  entry.getKey();
	    System.out.print(BedNumber + "   " +RoomNumber+"   "+key+ "   ");
	    System.out.println(BedInfo.get(key).Price+"   "+BedInfo.get(key).Booked);

	 }
	 
 }
 
 public void AddRoomInformation(Date low,Date high,int rate){
	 
	 BedInformation BI = new BedInformation();
	
	 if (low.equals(high))
	 {
		 BI.Price     = rate;
		 BI.Booked    = false;
		 BI.BookingId = 0;
		 
		 BedInfo.put(low, BI);
		 return;
	 }
		 
	 Calendar start = Calendar.getInstance();
	 start.setTime(low);
	 Calendar end = Calendar.getInstance();
	 end.setTime(high);
	 
	 
	 for (Date date = start.getTime(); !start.after(end); 
			  start.add(Calendar.DATE, 1), date = start.getTime())
	 {
		 BI.Price     = rate;
		 BI.Booked    = false;
		 BI.BookingId = 0;
		 
		 BedInfo.put(date, BI);
		 
	 }
		 
		 
	  
 }
 
  
 public int GetBedNumber()
 {
 	return BedNumber;
 }
 
 public void SetBedNumber(int Number)
 {
	 BedNumber = Number;
 }
 
 public int GetRoomNumber()
 {
 	return RoomNumber;
 }
 
 public void SetRoomNumber(int Number)
 {
	 RoomNumber = Number;
 }
 
}
