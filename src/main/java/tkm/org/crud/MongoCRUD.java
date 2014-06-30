package tkm.org.crud;
import  com.cassandraclient.beans.*;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

/*Implements the Mongo specific Connection and CRUD operations.
 * Selects the database 'awdb' and collection 'mycollection'
 * 
 * Mongo client from the driver is used to create an instance of the connection.
 * This is used select a database and that is stored in object of type DB
 * DB is used in selecting a collection from that database. 
 * The selected collection is used to perform the CRUD operations.
 */

public class MongoCRUD implements CRUD {

	DBCollection collection;
	
	public MongoCRUD()
	{
		createconn();
	}

	public boolean createconn(){
		try {
			DB db;
			MongoClient mongo;
			mongo = new MongoClient("localhost", 27017);
			db = mongo.getDB("awdb");
			
			collection = db.getCollectionFromString("mycollection");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		if(collection!=null)
			return true;
		else
			return false;
		
	}

	public boolean create(Device d) {
		
		BasicDBObject document = new BasicDBObject();
		document.put("UDID", d.getUdid());
		document.put("Friendlyname", d.getFriendlyname());
		document.put("Serialnum", d.getSerialnum());
		document.put("Manufacture_Date", d.getManidate());
		collection.insert(document);
		return true;
	}

	public Device read(String UDID) {
	
		DBCursor cursor;
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("UDID", UDID);
		cursor = collection.find(searchQuery);
		Device d = new Device();
		while (cursor.hasNext()) {
			DBObject buff = cursor.next();
			d.setUdid(UDID);
			d.setFriendlyname((String) buff.get("Friendlyname"));
			d.setManidate((String) buff.get("Manufacture_Date"));
			d.setSerialnum((Integer)buff.get("Serialnum"));
		}
		
		return d;
	}

	public boolean delete(String UDID) {
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("UDID", UDID);
		collection.remove(searchQuery);
		return true;
	}

	public boolean update(Device d) {
		
		BasicDBObject searchQuery = new BasicDBObject();
		BasicDBObject document = new BasicDBObject();
		searchQuery = new BasicDBObject().append("UDID", d.getUdid());
		document.append(
				"$set",
				new BasicDBObject().append("Friendlyname", d.getFriendlyname())
						.append("Serialnum", d.getSerialnum())
						.append("Manufacture_Date", d.getManidate()));
		collection.update(searchQuery, document);
		return true;
	}

}
