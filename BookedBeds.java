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

public class BookedBeds implements Serializable{

private static transient final long serialVersionUID    = 2000L;

static List<Booking> BookingDetails = new ArrayList<Booking>();  

static List<Booking> GetBookingList() throws IOException, ClassNotFoundException 
{
	ReadBooking();
	return BookingDetails;
}


public class Booking implements Serializable
{
  String CityName; 
  String PropertyName;
  int NumberOfBeds;
  int Beds[];
  int Booking_id;
  int price;
  Date StartDate;
  Date EndDate;
  private static transient final long serialVersionUID    = 21000L;
  
}

public void AddBooking(Booking bk)
{
	BookingDetails.add(bk);
}

public void DeleteBooking(int id)
{
   boolean BookingFound=false;
	Booking bk=null;
	
	for ( Booking b:BookingDetails)
	{
		if ( id == b.Booking_id)
		{
	      bk = b;
	      BookingFound =true;
	      break;
		}
				
		}
	if(BookingFound)
	   BookingDetails.remove(bk);
 }
     
public void DisplayBooking(Booking b) throws IOException, ClassNotFoundException
{
	System.out.println("Booking_id City_Name Property_Name  Number_of_Beds Start_Date End_Date ");
	
	System.out.println(b.Booking_id + "  " + b.CityName + "  "+b.PropertyName +"  "+b.NumberOfBeds+"  "+b.StartDate+"  "+b.EndDate);
	
	
}
public void BookRoom(String S_id,Date Start,Date End,int NumBeds) throws IOException, ClassNotFoundException 
{
	ReadBooking();
	
	String[] TempNames = S_id.split("_");
	
	Booking BB = new Booking();
	Random rand = new Random();
	int temp = rand.nextInt(100000);
	
	BB.CityName       = TempNames[0];
	BB.PropertyName   = TempNames[1];
	BB.price          = Integer.parseInt(TempNames[2]);
	BB.StartDate      = Start;
	BB.EndDate        = End;
	BB.NumberOfBeds   = NumBeds;
	BB.Booking_id     = temp;
	
	
	ReadBooking();
	
	AddBooking(BB);
	DisplayBooking(BB);
	
	StoreBooking();
	
	
}

private static void StoreBooking() throws IOException, ClassNotFoundException 
{
	final String dataFile = "booking.txt";
	
			
	ObjectOutputStream BookingOut = null;
    
	try {
		BookingOut = new ObjectOutputStream(new
                BufferedOutputStream(new FileOutputStream(dataFile)));

		BookingOut.flush(); 
		BookingOut.writeObject(BookingDetails);
		BookingDetails.clear();
         
    }
	catch (Exception e) {
        e.printStackTrace();
    }
	finally {
    	if(BookingOut != null){
    		BookingOut.close();
    	}
   }

}

public static void ReadBooking() throws IOException, ClassNotFoundException 
{
	final String dFile = "booking.txt";
			
	ObjectInputStream BookingIn = null;
    
	try {
		BookingIn = new ObjectInputStream(new
                BufferedInputStream(new FileInputStream(dFile)));
					
		BookingDetails.clear();
		BookingDetails = (List<Booking>) BookingIn.readObject();
		
								         
    }catch(FileNotFoundException e) {
       System.out.println("File Not Found" + dFile);
       
   }finally {
    	if(BookingIn != null){
    		BookingIn.close();
    	}
   }

}



}
