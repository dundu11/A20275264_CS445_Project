import java.io.Serializable;
import java.util.*;

public class SearchResults implements Serializable{

public String SearchID;
public String PropertyID;
public int price;

public TreeSet<Integer> Rooms = new TreeSet<Integer>();

List<BedInfo>  Beds = new ArrayList<BedInfo>();
static final long serialVersionUID = 56L;

} 


