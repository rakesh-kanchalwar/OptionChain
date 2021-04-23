package com.rakeshk.optionchain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakeshk.optionchain.model.banknifty.Filtered;
import com.rakeshk.optionchain.model.banknifty.Root;


public class DataReader {
	private Logger logger = LoggerFactory.getLogger(DataReader.class);

	public Filtered getFilteredDataFromFile() {
		Root dataRoot = null;
		try {
			InputStream in = OptionchainApplication.class.getResourceAsStream("/data.txt");
			if (in == null) {
				throw new FileNotFoundException("Resource not found: " + "/data.txt");
			}
			ObjectMapper mapper = new ObjectMapper();

			dataRoot = mapper.readValue(in, Root.class);
			System.out.println(dataRoot.toString());

		} catch (IOException e) {
			logger.error("Failed to fetch data from NSE - ", e);
		}
		return dataRoot.getFiltered();
	}
}