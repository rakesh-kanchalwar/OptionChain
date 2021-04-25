package com.rakeshk.optionchain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakeshk.optionchain.nifty.model.Root;

public class Scheduler extends TimerTask {
	private Logger logger = LoggerFactory.getLogger(Scheduler.class);

	@Override
	public void run() {
		try {
		InputStream in = OptionchainApplication.class.getResourceAsStream("/data.txt");
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + "/data.txt");
        }
		  ObjectMapper mapper = new ObjectMapper(); 
		
		  Root dataRoot = mapper.readValue(in, Root.class);
		  System.out.println(dataRoot.toString());
		  logger.info(dataRoot.toString());
				 
			 
		} catch (IOException e) {
			logger.error("Failed to fetch data from NSE - ", e);
		}

	}

	public static void main(String[] args) {
		Scheduler te1 = new Scheduler();

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(te1, 0, 1000);
	}

}
