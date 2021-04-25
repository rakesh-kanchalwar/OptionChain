package com.rakeshk.optionchain;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakeshk.optionchain.banknifty.BankNiftyDataReader;
import com.rakeshk.optionchain.banknifty.BankNiftyJsonContentCreator;
import com.rakeshk.optionchain.banknifty.model.Filtered;
import com.rakeshk.optionchain.common.DataReader;
import com.rakeshk.optionchain.common.DataWriter;
import com.rakeshk.optionchain.js.model.Datum;
import com.rakeshk.optionchain.js.model.Root;
import com.rakeshk.optionchain.nifty.NiftyDataReader;
import com.rakeshk.optionchain.nifty.NiftyJsonContentCreator;

@Component
public class OptionChainHTMLCreator {
	
	private Logger logger = LoggerFactory.getLogger(OptionChainHTMLCreator.class);
	
	@Autowired
	Environment environment;
	
	public void updateHTML() {
		try {
			DataFetcher dataFetcher = new DataFetcher();
			dataFetcher.fetchData(environment.getProperty("site.banknifty.url"), 
					environment.getProperty("site.cookie"), environment.getProperty("file.input.banknifty"));
			dataFetcher.fetchData(environment.getProperty("site.nifty.url"), 
					environment.getProperty("site.cookie"), environment.getProperty("file.input.nifty"));
			
			String prefixFileName = environment.getProperty("file.prefix"); 
			String suffixFileName = environment.getProperty("file.suffix"); 
			String prefixFileContents = DataReader.readFromFile(prefixFileName); 
			String suffixFileContents = DataReader.readFromFile(suffixFileName);
			  
			createBankNiftyChart(prefixFileContents, suffixFileContents);
			createNiftyChart(prefixFileContents, suffixFileContents);
	 
			logger.info("Created updated html files");
		}
		catch (Exception e) {
			logger.error("Failed to update html", e);
		}
	}
	
	private void createNiftyChart(String prefixFileContents, String suffixFileContents) throws IOException, JsonProcessingException{
		DataReader dataReader = new NiftyDataReader();
		DataWriter dataWriter = new DataWriter();
		
		com.rakeshk.optionchain.nifty.model.Filtered filtered = (com.rakeshk.optionchain.nifty.model.Filtered) dataReader.getFilteredDataFromFile(environment.getProperty("file.input.nifty"));
		Root jsonContents =	new NiftyJsonContentCreator().createJsonData(filtered, Integer.parseInt(environment.getProperty("data.minRange.nifty")),
				Integer.parseInt(environment.getProperty("data.maxRange.nifty")));
		
		String backupFileName = environment.getProperty("file.backup.nifty");
		
		String outputFileName = environment.getProperty("file.output.nifty");
		
		createOutputFiles(dataReader, dataWriter, jsonContents, backupFileName, prefixFileContents, suffixFileContents, outputFileName);
	}

	private void createBankNiftyChart(String prefixFileContents, String suffixFileContents) throws IOException, JsonProcessingException {
		DataReader dataReader = new BankNiftyDataReader();
		DataWriter dataWriter = new DataWriter();
		
		Filtered filtered = (Filtered) dataReader.getFilteredDataFromFile(environment.getProperty("file.input.banknifty"));
		Root jsonContents =	new BankNiftyJsonContentCreator().createJsonData(filtered, Integer.parseInt(environment.getProperty("data.minRange.banknifty")),
				Integer.parseInt(environment.getProperty("data.maxRange.banknifty")));
		
		String backupFileName = environment.getProperty("file.backup.banknifty");
		String outputFileName = environment.getProperty("file.output.banknifty");
		createOutputFiles(dataReader, dataWriter, jsonContents, backupFileName, prefixFileContents, suffixFileContents, outputFileName);
	}

	private void createOutputFiles(DataReader dataReader, DataWriter dataWriter, Root jsonContents,
			String backupFileName, String prefixFileContents, String suffixFileContents, String outputFileName)
			throws IOException, JsonProcessingException {
		List<Datum> backedUpData = dataReader.getBackedUpDataFromFile(backupFileName);
		updateBackedUpData(backedUpData, jsonContents);
		dataWriter.writeDataToFile(getJsonData(backedUpData), backupFileName);
		dataWriter.writeDataToFile(prefixFileContents, sanitizeMethodNames(getJsonData(jsonContents)), suffixFileContents, outputFileName);
	}

	private String sanitizeMethodNames(String jsonData) {
		return jsonData.replace("\"toogleDataSeries\"", "toogleDataSeries").replace("\"toggleMouseover\"", "toggleMouseover").replace("\"toggleMouseout\"", "toggleMouseout");
	}

	private void updateBackedUpData(List<Datum> backedUpData, Root jsonContents) {
		List<Datum> jsonDatum = jsonContents.data;
		jsonDatum.forEach(datum -> {
			backedUpData.forEach(backed -> {
				if(backed.name.equals(datum.name)){
					backed.dataPoints.addAll(datum.dataPoints);
					datum.dataPoints.clear();
					datum.dataPoints.addAll(backed.dataPoints);
				}
			});
		});
		if(backedUpData.isEmpty()) {
			backedUpData.addAll(jsonDatum);
		}
	}

	private String getJsonData(Root rawData) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rawData);
		return json;
	}
	
	private String getJsonData(List<Datum> backedUpData) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(backedUpData);
		return json;
	}

}
