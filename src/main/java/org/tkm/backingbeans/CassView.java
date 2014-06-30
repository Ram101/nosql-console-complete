package org.tkm.backingbeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import tkm.org.crud.cassandraCRUD;

import com.cassandraclient.beans.*;

@ManagedBean
@ViewScoped
public class CassView implements Serializable {

	private LazyDataModel<Device> lazyModel;
	public  Device selectedDevice;

	@ManagedProperty("#{cassandraService}")
	private Cassandra service;

	@PostConstruct
	public void init() {
		lazyModel = new LazyCassDataModel(service.createList(100));

	}

	public LazyDataModel<Device> getLazyModel() {
		return lazyModel;
	}

	public Device getSelectedDevice() {
		return selectedDevice;
	}

	public void setSelectedDevice(Device selectedDevice) {
		this.selectedDevice = selectedDevice;
	}

	public void setService(Cassandra service) {
		this.service = service;
	}

	
	
	public void onRowSelect(SelectEvent event) {
		
		FacesMessage msg = new FacesMessage("Device Selected",
				((Device) event.getObject()).getUdid());
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public void onRowEdit(ActionEvent actionEvent) {
		if(selectedDevice != null)
		{
			cassandraCRUD c =new cassandraCRUD();
			Device temp = new Device();
    		temp.setUdid(selectedDevice.getUdid());
			temp.setFriendlyname(selectedDevice.getFriendlyname());
			temp.setSerialnum(selectedDevice.getSerialnum());
			temp.setManidate(selectedDevice.getManidate());
			c.update(selectedDevice);
			FacesMessage msg = new FacesMessage(" Update Successfull" );
	        FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}
		
		
	
	}

	public void onDelete(ActionEvent actionEvent) {
		
		if(selectedDevice != null)
		{
			cassandraCRUD c =new cassandraCRUD();
			c.delete(selectedDevice.getUdid());
			FacesMessage msg = new FacesMessage("Deleted Successfully" );
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
		public void onCreate(ActionEvent actionEvent) {
			
			if(selectedDevice != null)
			{
				cassandraCRUD c =new cassandraCRUD();
				c.update(selectedDevice);
				FacesMessage msg = new FacesMessage("New record Created Successfully" );
		        FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		
		
	}

	
}
