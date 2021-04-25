package com.rakeshk.optionchain.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakeshk.optionchain.js.model.Datum;

public interface DataReader {
	Logger logger = LoggerFactory.getLogger(DataReader.class);
	
	public Object getFilteredDataFromFile(String fileName) throws IOException;
	
	static String readFromFile(String fileName) throws IOException {
		String content = new String(Files.readAllBytes(Paths.get(fileName)));
		return content;
	}

	default List<Datum> getBackedUpDataFromFile(String fileName) throws IOException {
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
