package com.rakeshk.optionchain;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.rakeshk.optionchain.banknifty.model.Datum;
import com.rakeshk.optionchain.banknifty.model.Filtered;

public class SheetService {
	private Logger logger = LoggerFactory.getLogger(Scheduler.class);
	
    public void updateSheet(String spreadsheetid, String sheetNameWithHeader,String sheetNameWithoutHeader, boolean includeIntermediateStrikePrice, int minRange, int maxRange, boolean insertHeader) throws IOException, GeneralSecurityException {
    	Sheets service = new AuthProvider().getService();
    	
    	Filtered filtered = new DataReader().getFilteredDataFromFile("/data.txt");
    	
    	if(insertHeader) {
			addHeaderFields(spreadsheetid, service, sheetNameWithHeader,filtered, includeIntermediateStrikePrice, minRange, maxRange);
    	}
    	appendRow(spreadsheetid, service, sheetNameWithoutHeader, filtered, includeIntermediateStrikePrice, minRange, maxRange);
        
    }

    private void appendRow(String spreadsheetid, Sheets service, String sheetName, Filtered filtered, boolean includeIntermediateStrikePrice, int minRange, int maxRange) throws IOException {
    	List<Object> rowDataList = new ArrayList<Object>();
    	rowDataList.add(LocalDateTime.now().getHour() +":"+ LocalDateTime.now().getMinute());
    	for (Datum datum :  filtered.data) {
    		extractAndCreateData(includeIntermediateStrikePrice, minRange, maxRange, rowDataList, datum.cE.strikePrice, datum.cE.openInterest);
    		extractAndCreateData(includeIntermediateStrikePrice, minRange, maxRange, rowDataList, datum.pE.strikePrice, datum.pE.openInterest);
		}
		ValueRange appendBody = new ValueRange().setValues(Arrays.asList(rowDataList));
		AppendValuesResponse appendResult = service.spreadsheets().values().append(spreadsheetid, sheetName, appendBody)
				.setValueInputOption("USER_ENTERED")
				.setInsertDataOption("INSERT_ROWS")
				.setIncludeValuesInResponse(true)
				.execute();
		logger.info(appendResult.toPrettyString());
	}

    private void addHeaderFields(String spreadsheetid, Sheets service, String sheetName, Filtered filtered, boolean includeIntermediateStrikePrice, int minRange, int maxRange) throws IOException {
    	List<Object> fieldsList = new ArrayList<>();
    	for (Datum datum :  filtered.data) {
    		if(datum.strikePrice >= minRange && datum.strikePrice <= maxRange) {
	    		if(includeIntermediateStrikePrice) {
	    			fieldsList.add(datum.strikePrice + "");
	    		}
	    		else if (excludeIntermediateStrikePrice(datum.strikePrice)) {
	    			fieldsList.add(datum.strikePrice + "");
				}
    		}
    	}
    	fieldsList.addAll(fieldsList);
    	fieldsList.add(0, "");
    	ValueRange appendBody = new ValueRange().setValues(Arrays.asList(fieldsList));
    	AppendValuesResponse appendResult = service.spreadsheets().values().append(spreadsheetid, sheetName, appendBody)
				.setValueInputOption("USER_ENTERED")
				.setInsertDataOption("INSERT_ROWS")
				.setIncludeValuesInResponse(true)
				.execute();
    	logger.info(appendResult.toPrettyString());
	}
    
	private void extractAndCreateData(boolean includeIntermediateStrikePrice, int minRange, int maxRange, List<Object> rowDataList, int strikePrice, int openInterest) {
		if(strikePrice >= minRange && strikePrice <= maxRange) {
			if(includeIntermediateStrikePrice) {
				rowDataList.add("" + openInterest);
			}
			else if (excludeIntermediateStrikePrice(strikePrice)) {
				rowDataList.add("" + openInterest);
			}
		}
	}

	private boolean excludeIntermediateStrikePrice(int strikePrice) {
		return strikePrice % 100 == 0;
	}
    
	
	
	/*
	 * private void appendRows(String spreadsheetid, Sheets service, Filtered
	 * filtered) throws IOException { List<RowData> rowDataList = new
	 * ArrayList<RowData>(); for (Datum datum : filtered.data) { RowData rowData =
	 * new RowData(); rowData.set("" + datum.cE.strikePrice, (Integer)
	 * datum.cE.openInterest()); rowDataList.add(rowData);
	 * 
	 * } UpdateCellsRequest cellsRequest = new UpdateCellsRequest();
	 * 
	 * cellsRequest.setRows(rowDataList); AppendCellsRequest appendCellsRequest =
	 * new AppendCellsRequest(); appendCellsRequest.setRows(rowDataList);
	 * 
	 * 
	 * Request request = new Request(); request.setAppendCells(appendCellsRequest);
	 * List<Request> requestList = new ArrayList<Request>();
	 * requestList.add(request);
	 * 
	 * BatchUpdateSpreadsheetRequest body = new
	 * BatchUpdateSpreadsheetRequest().setRequests(requestList);
	 * 
	 * service.spreadsheets().batchUpdate(spreadsheetid, body).execute(); }
	 */
}
