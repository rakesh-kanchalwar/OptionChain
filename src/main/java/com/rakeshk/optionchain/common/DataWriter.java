package com.rakeshk.optionchain.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataWriter {
	private Logger logger = LoggerFactory.getLogger(DataWriter.class);
	
	public void writeDataToFile(String prefix, String json, String suffix, String filePath) throws IOException {
		logger.info("Writing to " + filePath);
		Files.write(Paths.get(filePath), (prefix + json + suffix).getBytes(), StandardOpenOption.TRUNCATE_EXISTING);		
	}
	
	public void writeDataToFile(String json, String filePath) throws IOException {
		logger.info("Writing to " + filePath);
		Files.write(Paths.get(filePath), json.getBytes(), StandardOpenOption.CREATE);		
	}
}

