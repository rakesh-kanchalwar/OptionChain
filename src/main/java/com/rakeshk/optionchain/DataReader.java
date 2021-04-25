package com.rakeshk.optionchain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakeshk.optionchain.banknifty.model.Filtered;
import com.rakeshk.optionchain.banknifty.model.Root;
import com.rakeshk.optionchain.js.model.Datum;


public class DataReader {
	private Logger logger = LoggerFactory.getLogger(DataReader.class);

	public Filtered getFilteredDataFromFile(String fileName) throws IOException {
		Root dataRoot = null;
		String fileContents = readFromFile(fileName);
		try {
			ObjectMapper mapper = new ObjectMapper();

			dataRoot = mapper.readValue(fileContents, Root.class);
			logger.info("Input data read from : " + fileName);

		} catch (IOException e) {
			logger.error("Failed to read data from file - " + fileName + "", e);
		}
		return dataRoot.getFiltered();
	}
	
	public String readFromFile(String fileName) throws IOException {
		String content = new String(Files.readAllBytes(Paths.get(fileName)));
		return content;
	}

	public List<Datum> getBackedUpDataFromFile(String fileName) throws IOException {
		List<Datum> datum = new ArrayList<>();
		String fileContents = readFromFile(fileName);
		try {
			ObjectMapper mapper = new ObjectMapper();

			datum  = mapper.readValue(fileContents, new TypeReference<List<Datum>>(){});
			logger.info("BackedUp data read from : " + fileName);

		} catch (IOException e) {
			logger.error("Failed to read data from file - " + fileName + "", e);
		}
		return datum;
	}
	
}