import java.io.Serializable;
import java.util.*;

public class User implements Serializable{

    public int     UserId;
    public String  FirstName;
    public String  LastName;
    public String  EmailID;
    public Date    DateCreation;
    public String  CCNumber;
    public Date    ExpiryDate;
    public String  SecurityCode;
    public String  PhoneNumber;
    
    static final long serialVersionUID = 49L;

}
