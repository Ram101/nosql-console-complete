package org.tkm.backingbeans;


import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import tkm.org.crud.Benchmark;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class Charts implements Serializable {
	private BarChartModel animatedModel;

	private boolean renderChart = false;
	
	//@PostConstruct
	public void init() {
		createAnimatedModels();
	}

	public BarChartModel getAnimatedModel() {
		return animatedModel;
	}

	private void createAnimatedModels() {
		animatedModel = initBarModel();
		animatedModel.setTitle("Bench Marking");
		animatedModel.setAnimate(true);
		animatedModel.setLegendPosition("ne");
		Axis yAxis = animatedModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(10);
	}

	private Benchmark benchmark =new Benchmark();

	private BarChartModel initBarModel() {
		BarChartModel model = new BarChartModel();
		benchmark.benchget();
		ChartSeries cassandraChart = new ChartSeries();
		cassandraChart.setLabel("Cassandra");
		cassandraChart.set("time in seconds",benchmark.getTcass());

		ChartSeries mongoChart = new ChartSeries();
		mongoChart.setLabel("Mongo");
		mongoChart.set("time in seconds", benchmark.getTmongo());

		ChartSeries redisChart = new ChartSeries();
		redisChart.setLabel("Redis");
		redisChart.set("time in seconds", benchmark.getTredis());

		model.addSeries(cassandraChart);
		model.addSeries(mongoChart);
		model.addSeries(redisChart);

		return model;
	}

	public int getProgress() {
		System.out.println("In Get Progress value: " + benchmark.getProgress());
		System.out.println("In Get Progress return: " + (int)(((double)(benchmark.getProgress() / (3 * benchmark.getThreshold() * 10))) * 100));
		
		double fraction = (double) benchmark.getProgress() / (double) (benchmark.getThreshold());
		fraction = fraction / 30;
		fraction *= 100;
		System.out.println("Fraction: " + fraction); 
		return (int)(fraction);
	}

	public void startBenchMark() {
		System.out.println("Starting off bench mark");
		init();
		renderChart = true;
		System.out.println("Done with bench mark");
	}

	public boolean isRenderChart() {
		return renderChart;
	}

	public void setRenderChart(boolean renderChart) {
		this.renderChart = renderChart;
	}
}
