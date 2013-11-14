import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchResults implements Serializable{

public String SearchID;
public String PropertyID;
List<BedInfo>  Beds = new ArrayList<BedInfo>();

}
