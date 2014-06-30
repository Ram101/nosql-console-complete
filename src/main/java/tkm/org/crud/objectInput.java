package tkm.org.crud;
import java.util.*;
import com.cassandraclient.beans.*;
public class objectInput {
 Device d= new Device();
 Scanner s = new Scanner(System.in);
 String input;
 public Device  inputU()
	{ 
	    
		System.out.println("Enter Updation values:");
		System.out.println("Udid:");
		d.setUdid(s.next());
		System.out.println("Friendlyname:");
		d.setFriendlyname(s.next());
		System.out.println("Serialnum:");
		d.setSerialnum(s.nextInt());
		System.out.println("Manufacture date:");
		d.setManidate(s.next());
		return d;
	}
 public String inputR()
 {
	 System.out.println("Enter Udid of object to be read:");
	 return input=s.next();
	 
 }
 
 public String inputD()
 {
	 System.out.println("Enter Udid of object to be Deleted:");
	 return input=s.next();
	 
 }
	
	
}
