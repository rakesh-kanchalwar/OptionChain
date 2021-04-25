package com.rakeshk.optionchain;

import java.io.IOException;
import java.util.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication(scanBasePackages = {"com.rakeshk.optionchain"})
public class JSBasedOptionChain implements CommandLineRunner{
	
	@Autowired
	Scheduler scheduler;
	
	@Autowired
	Environment environment;
	
	public static void main(String[] args) throws IOException {
		SpringApplication.run(JSBasedOptionChain.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(scheduler, 0, (Integer.parseInt(environment.getProperty("site.data.delay")) * 1000 * 60));
	}
}











