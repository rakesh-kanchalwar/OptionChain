package com.rakeshk.optionchain.nifty;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakeshk.optionchain.common.DataReader;
import com.rakeshk.optionchain.nifty.model.Filtered;
import com.rakeshk.optionchain.nifty.model.Root;


public class NiftyDataReader implements DataReader{
	private Logger logger = LoggerFactory.getLogger(NiftyDataReader.class);

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
}