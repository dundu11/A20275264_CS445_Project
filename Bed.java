import java.io.Serializable;
import java.util.*;


public class Bed implements Serializable
{
 private int RoomNumber;
 private int BedNumber;
 
 public Date StartingDate = new Date();
 public Date EndingDate   = new Date();
 
 static final long serialVersionUID = 72L;
 
   
 public class BedInformation implements Serializable
 {
	 int     Price;
	 boolean Booked; 
	 int  BookingId;
	 
	 static final long serialVersionUID = 81L;
 }
 
 public  Map<Date,BedInformation> BedInfo= new HashMap<Date,BedInformation>(); 
 
 /*
 public void BedDetails()
 {
		
	 for (Map.Entry<Date,BedInformation> entry : BedInfo.entrySet())
	  {
		Date key =  entry.getKey();
	    System.out.print(BedNumber + "   " +RoomNumber+"   "+key+ "   ");
	    System.out.println(BedInfo.get(key).Price+"   "+BedInfo.get(key).Booked);

	 }
	 
 }
 */
 
 public void AddRoomInformation(Date date,int rate){
	 
	 BedInformation BI = new BedInformation();
	 BI.Price     = rate;
	 BI.Booked    = false;
	 BI.BookingId = 0;
		 
	 BedInfo.put(date, BI);
		 
  
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
