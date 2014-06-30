package tkm.org.crud;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cassandraclient.beans.*;
import com.google.gson.Gson;




import redis.clients.jedis.Jedis;
public class RedisCRUD implements CRUD {

	private Jedis jedis;
	private Gson gs;
	private Device temp;

	public RedisCRUD()
	{
		createconn();
	}
	
	public RedisCRUD(int a)
	{
		jedis = new Jedis("192.168.43.239");
        jedis.connect();	
	}
	
	
	public boolean create(Device d) {
		createconn();
		try {
			jedis.hset("Device", d.getUdid(), gs.toJson(d));
				return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Device read(String UDID) {
		
		String tmp = null;
		// byte[] tmp = null;
		gs = new Gson();
		try {
			tmp = jedis.hget("Device", UDID);
			temp = gs.fromJson(tmp, Device.class);
			
		} catch (Exception e) {
			return null;
		}
		//System.out.print(temp.getUdid());
		return temp;

	}

	public boolean delete(String UDID) {
		
		try{
		jedis.hdel("Device", UDID);
		return true;
		}
		catch(Exception O)
		{
			System.out.println("Record not found!!!");
			return false;
		}
	}

	public boolean update(Device d) {
		
		jedis.hset("Device", d.getUdid(), gs.toJson(d));
		return true;

	}

	public boolean createconn() {

		if (jedis == null) {

			jedis = new Jedis("localhost");
			gs = new Gson();
			temp = new Device();
			jedis.select(0);
			
			jedis.connect();
			
		}
		if (jedis==null)
			return false;
		else
			return true;

	}

	public boolean sampleAdd(deviceSample d) {
		try {
			jedis.zadd(d.getUdid().replaceAll(":", ""),
					Double.parseDouble(jedis.time().get(0)), gs.toJson(d));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
 	
	
	
	
	public Set<String> sampleRead() {
		
		
		
	    Set<String> s1= jedis.zrevrange("806c1b205737",0,9);
		
		return s1;
	}
	
}
