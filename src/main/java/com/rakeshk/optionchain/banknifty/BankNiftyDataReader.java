package com.rakeshk.optionchain.banknifty;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakeshk.optionchain.banknifty.model.Filtered;
import com.rakeshk.optionchain.banknifty.model.Root;
import com.rakeshk.optionchain.common.DataReader;


public class BankNiftyDataReader implements DataReader{
	private Logger logger = LoggerFactory.getLogger(BankNiftyDataReader.class);

	public Filtered getFilteredDataFromFile(String fileName) throws IOException {
		Root dataRoot = null;
		String fileContents = DataReader.readFromFile(fileName);
		try {
			ObjectMapper mapper = new ObjectMapper();

			dataRoot = mapper.readValue(fileContents, Root.class);
			logger.info("Input data read from : " + fileName);

		} catch (IOException e) {
			logger.error("Failed to read data from file - " + fileName + "", e);
		}
		return dataRoot.getFiltered();
	}
	
	public Filtered getFilteredDataFromString(String fileContents) throws IOException {
		Root dataRoot = null;
		try {
			ObjectMapper mapper = new ObjectMapper();

			dataRoot = mapper.readValue(fileContents, Root.class);

		} catch (IOException e) {
			logger.error("Failed to read data from String", e);
		}
		return dataRoot.getFiltered();
	}
}