package com.rakeshk.optionchain;

import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataFetcher {
	private Logger logger = LoggerFactory.getLogger(DataFetcher.class);
	
	public String fetchData(String urlStr, String cookieStr) throws Exception{
	    try  
	    {  
	      URL url = new URL(urlStr); // creating a url object  
	      URLConnection urlConnection = url.openConnection(); // creating a urlconnection object  
	      urlConnection.addRequestProperty("Cookie", cookieStr);
	      urlConnection.addRequestProperty("accept-language", "en-US,en;q=0.9,mr-IN;q=0.8,mr;q=0.7,hi;q=0.6");
	      urlConnection.addRequestProperty("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
	      urlConnection.addRequestProperty("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
	      urlConnection.connect();
	      StringBuilder sb = new StringBuilder();
	      for (int ch; (ch = urlConnection.getInputStream().read()) != -1; ) {
	          sb.append((char) ch);
	      }
	      logger.info("Read data from URL -" + urlStr);
	      return sb.toString();
	    }  
	    catch(Exception e){  
	    	logger.error("Failed to read data from URL -" + urlStr, e);
	    	throw e;
	    }  
	}
}
