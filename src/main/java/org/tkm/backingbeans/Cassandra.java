package org.tkm.backingbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import tkm.org.crud.CRUD;
import tkm.org.crud.DBClient;
import tkm.org.crud.cassandraCRUD;

import com.cassandraclient.beans.*;

@ManagedBean(name = "cassandraService")
@ApplicationScoped
	public class Cassandra {
		private String udid;
		private String friendlyname;
		private int serialnum;
		private String date;
		
		
		
	//		static int i=0;
		
		public List<Device> createList(int pageSize,int first) {
			cassandraCRUD db =new cassandraCRUD();
			Device obj=new Device();
			//int save=i;
	        List<Device> list = new ArrayList<Device>();
	        for(int i=0 ; i < pageSize; i++) {
	        	obj=db.read(Integer.toString(i+first));
	        
	        	list.add(obj);
	        }
	        return list;
		}
		
		public List<Device> createList(int pageSize) {
			cassandraCRUD db =new cassandraCRUD();
			Device obj=new Device();
			//int save=i;
	        List<Device> list = new ArrayList<Device>();
	        for(int i=0 ; i < pageSize; i++) {
	        	obj=db.read(Integer.toString(i));
	            list.add(obj);
	        }
	        return list;
		}
		
		
		public String getUdid() {
			return udid;
		}
		public void setUdid(String udid ) {
			this.udid = udid;
		}
		public String getFriendlyname() {
			return friendlyname;
		}
		public void setFriendlyname(String friendlyname) {
			this.friendlyname = friendlyname;
		}
		public int getSerialnum() {
			return serialnum;
		}
		public void setSerialnum(int serialnum) {
			this.serialnum = serialnum;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		

		
}
