package com.rakeshk.optionchain;

import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataFetcher {
	private Logger logger = LoggerFactory.getLogger(DataFetcher.class);
	
	public void fetchData(String urlStr, String cookieStr, String outputFilePath){
	    try  
	    {  
	      URL url = new URL(urlStr); // creating a url object  
	      URLConnection urlConnection = url.openConnection(); // creating a urlconnection object  
	      urlConnection.addRequestProperty("Cookie", cookieStr);
	      urlConnection.addRequestProperty("accept-language", "en-US,en;q=0.9,mr-IN;q=0.8,mr;q=0.7,hi;q=0.6");
	      urlConnection.addRequestProperty("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
	      urlConnection.addRequestProperty("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
	      urlConnection.connect();
	      Files.copy(urlConnection.getInputStream(), Paths.get(outputFilePath), StandardCopyOption.REPLACE_EXISTING);
	      logger.info("Read data from URL -" + urlStr);
	    }  
	    catch(Exception e){  
	    	logger.error("Failed to read data from URL -" + outputFilePath, e);
	    }  
	}
}
