package org.tkm.backingbeans;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import com.cassandraclient.beans.*;

@ManagedBean(name = "dtBasicView")
@ViewScoped
public class BasicView implements Serializable
{
	private List<Device> mylist;
	@ManagedProperty("#{cassandraService}")
	private Cassandra service;

	@PostConstruct
    public void init()
	{
      mylist = service.createList(10);
      }

	public List<Device> getMylist() 
	{
       return mylist;
}

	public void setMylist() 
	{
      mylist = service.createList(10);
    }
	public void setService(Cassandra service) 
	{
      this.service=service;
	}
	public void goToNext()
	{
		init();
	}
}

