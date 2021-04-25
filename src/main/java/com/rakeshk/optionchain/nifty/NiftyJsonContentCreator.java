package com.rakeshk.optionchain.nifty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.rakeshk.optionchain.common.JsonContentCreator;
import com.rakeshk.optionchain.js.model.Datum;
import com.rakeshk.optionchain.js.model.Root;
import com.rakeshk.optionchain.js.model.Title;
import com.rakeshk.optionchain.nifty.model.Filtered;

public class NiftyJsonContentCreator extends JsonContentCreator{
	public Root createJsonData(Filtered filtered, int minRange, int maxRange) {
		Root root = new Root();
		root.animationEnabled = true;
		root.title = createTitle();
		root.axisX = createXaxis();
		root.axisY = createYaxis();
		root.toolTip = createToolTip();
		root.legend = createLegend();
		root.data = createData(filtered, minRange, maxRange);
		return root;
	}

	private List<Datum> createData(Filtered filtered, int minRange, int maxRange) {
		String time = LocalDateTime.now().getHour() +":"+ LocalDateTime.now().getMinute();
		ArrayList<Datum> datumList = new ArrayList<Datum>();
    	for (com.rakeshk.optionchain.nifty.model.Datum externalDatum :  filtered.data) {
    		extracted(minRange, maxRange, time, datumList, externalDatum.cE.strikePrice,externalDatum.cE.openInterest, "CE");
    		extracted(minRange, maxRange, time, datumList, externalDatum.pE.strikePrice,externalDatum.pE.openInterest, "PE");
		}

		return datumList;
	}

	private Title createTitle() {
		Title title = new Title();
		title.text = "Nifty";
		return title;
	}
}
