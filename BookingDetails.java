import java.io.Serializable;
import java.util.*;

public class BookingDetails implements Serializable{

	String City;
	String Property;
	Date   CheckIn;
	Date   CheckOut;
	int    BookingId;
	int    TotalBeds;
	int    Price;
	String Name;
	int UserId;
	
	public int cancellation_penalty;
	public int cancellation_deadline;
	
	
	static final long serialVersionUID = 20L;
	
}
