package org.tkm.backingbeans;

import java.io.Serializable;
import java.util.Random;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.json.JSONArray;
import org.json.JSONObject;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.google.gson.Gson;

import tkm.org.crud.RedisCRUD;
import tkm.org.crud.deviceSample;

@ManagedBean
@ViewScoped
public class chartController implements Serializable {

	private CartesianChartModel modelRam;
	private CartesianChartModel modelBat;
	

	public chartController() {
		createLinearModelRam();
		createLinearModelBat();
	}

	private void createLinearModelRam() {
		modelRam = new CartesianChartModel();
		modelRam.addSeries(getStockChartDataRam("Free Ram"));
	}
	private void createLinearModelBat() {
		modelBat = new CartesianChartModel();
		modelBat.addSeries(getStockChartDataBat("Battey value"));
	}
	
	
	private ChartSeries getStockChartDataRam(String label) {
		ChartSeries data = new ChartSeries();
		data.setLabel(label);
		RedisCRUD c1 = new RedisCRUD(1);
		
		Set<String> s1=c1.sampleRead(); 
		
		deviceSample[] d1=new deviceSample[10];
		Gson gs=new Gson();
		JSONArray datarec= new JSONArray(s1.toString());
    	for(int j=9,i=1;j>-1&&i<=10;--j,++i)
    	{   
		JSONObject obj =datarec.getJSONObject(j);
		d1[j]=gs.fromJson(obj.toString(), deviceSample.class);
		data.getData().put(i,Double.parseDouble(d1[j].getTotalMemory()));
		  	}
		return data;
	}
	private ChartSeries getStockChartDataBat(String label) {
		ChartSeries data = new ChartSeries();
		data.setLabel(label);
		RedisCRUD c1 = new RedisCRUD(1);
		
		Set<String> s1=c1.sampleRead(); 
		
		deviceSample[] d1=new deviceSample[10];
		Gson gs=new Gson();
		JSONArray datarec= new JSONArray(s1.toString());
    	for(int j=9,i=1;j>-1&&i<=10;--j,++i)
    	{   
		JSONObject obj =datarec.getJSONObject(j);
		d1[j]=gs.fromJson(obj.toString(), deviceSample.class);
		data.getData().put(i,Double.parseDouble(d1[j].getBatteryLevel()));
		  	}
		return data;
	}

	public CartesianChartModel getLinearModelRam() {
		
		createLinearModelRam();
		
		return modelRam;
	}
   public CartesianChartModel getLinearModelBat() {
		
		createLinearModelBat();
		
		return modelBat;
	}
	
}
