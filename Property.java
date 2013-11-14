import java.io.Serializable;
import java.util.*;

public class Property implements Serializable {
	
private String PropretyName;
private String City;
private String Street,State,PostalCode;
private String Country,Phone,EmailId,Web;
private String Check_In,Check_Out,Facebook;
private String Smoking,Alcohol;
static Date LoadStartDate = new Date();
static Date LoadEndDate   = new Date();

//static final long serialVersionUID = 56L;

List<Bed> Beds = new ArrayList<Bed>();

public void SetFaceBookID(String name)
{
	Facebook = name;
}

public String GetFaceBookID()
{
	return Facebook;
}


public void SetCountry(String name)
{
	Country = name;
}

public String GetCountry()
{
	return Country;
}

public void SetAlcohol(String number)
{
	Alcohol = number;
}

public String GetAlcohol()
{
	return Alcohol;
}

public void SetSmoking(String number)
{
	Smoking = number;
}

public String GetSmoking()
{
	return Smoking;
}


public void SetCheck_Out(String Name)
{
	Check_Out = Name;
}

public String GetCheck_Out()
{
	return Check_Out;
}


public void SetCheck_In(String Name)
{
	Check_In = Name;
}

public String GetCheck_In()
{
	return Check_In;
}


public void SetWebSite(String Name)
{
	Web = Name;
}

public String GetWebSite()
{
	return Web;
}


public void SetEmailId(String Name)
{
	EmailId = Name;
}

public String GetEmailId()
{
	return EmailId;
}


public void SetPhoneNumber(String Name)
{
	Phone = Name;
}

public String GetPhoneNumber()
{
	return Phone;
}


public void SetPostalCode(String Name)
{
	PostalCode = Name;
}

public String GetPostalCode()
{
	return PostalCode;
}


public void SetState(String Name)
{
	State = Name;
}

public String GetState()
{
	return State;
}


public void SetPropertyName(String Name)
{
	PropretyName = Name;
}

public String GetPropertyName()
{
	return PropretyName;
}

public void SetCityName(String Name)
{
	City = Name;
}

public String GetCityName()
{
	return City;
}

public void SetStreetName(String Name)
{
	Street = Name;
}

public String GetStreetName()
{
	return Street;
} 



public void DisplayPropertyDetails()
{
 System.out.println("City Name      : "+City);
 System.out.println("PropretyName   : "+PropretyName);
  
 System.out.println("------------------------------");
 System.out.println("BedNumber   Room Number    Date     Price   Booked");
 
 for(Bed b : Beds)
 {
     b.BedDetails();
//	 System.out.println(b.GetBedNumber() + "   "+b.GetRoomNumber());
 }
}

}


