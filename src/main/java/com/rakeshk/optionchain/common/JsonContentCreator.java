package com.rakeshk.optionchain.common;

import java.util.ArrayList;

import com.rakeshk.optionchain.js.model.AxisX;
import com.rakeshk.optionchain.js.model.AxisY;
import com.rakeshk.optionchain.js.model.DataPoint;
import com.rakeshk.optionchain.js.model.Datum;
import com.rakeshk.optionchain.js.model.Legend;
import com.rakeshk.optionchain.js.model.ToolTip;

public class JsonContentCreator {
	
	protected void extracted(int minRange, int maxRange, String time, ArrayList<Datum> datumList, int strikePrice, int openInterest, String optionType) {
		Datum datum = new Datum();
		datum.type = "line";
		datum.visible = includeInData(strikePrice, minRange, maxRange);
		datum.name = strikePrice + optionType;
		datum.showInLegend = true;
		datum.markerSize = 0;
		datum.yValueFormatString = "";
		ArrayList<DataPoint> dataPoints = new ArrayList<DataPoint>();
		DataPoint dataPoint = getDataPoints(time, openInterest);
		dataPoints.add(dataPoint);
		datum.dataPoints = dataPoints;
		datumList.add(datum);
	}
	
	protected boolean includeInData(int strikePrice, int minRange, int maxRange) {
		if((minRange == 0 || maxRange == 0) || (strikePrice >= minRange && strikePrice <= maxRange) ) {
			return true;
		}
		return false;
	}

	protected boolean excludeIntermediateStrikePrice(int strikePrice) {
		return strikePrice % 100 == 0;
	}
	
	protected DataPoint getDataPoints(String time, int openInterest) {
		DataPoint dataPoint = new DataPoint();
		dataPoint.label = time;
		dataPoint.y = openInterest;
		return dataPoint;
	}

	protected Legend createLegend() {
		Legend legend = new Legend();
		legend.cursor = "pointer";
		legend.verticalAlign = "bottom";
		legend.horizontalAlign ="center";
		legend.dockInsidePlotArea = false;
		legend.itemclick = "toogleDataSeries";
		legend.itemmouseover = "toggleMouseover";
		legend.itemmouseout = "toggleMouseout";
		legend.fontSize = 10;
		return legend;
	}

	protected ToolTip createToolTip() {
		ToolTip toolTip = new ToolTip();
		toolTip.shared = true;
		return toolTip;
	}

	protected AxisX createXaxis() {
		AxisX axisX = new AxisX();
		axisX.valueFormatString = "hh MM";
		return axisX;
	}
	
	protected AxisY createYaxis() {
		AxisY axisY = new AxisY();
		axisY.title = "Volume";
		axisY.prefix = "";
		axisY.suffix= "";
		return axisY;
	}
	
}
