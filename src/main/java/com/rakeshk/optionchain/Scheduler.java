package com.rakeshk.optionchain;

import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Scheduler extends TimerTask {
	
	@Autowired
	OptionChainHTMLCreator htmlCreator;

	@Override
	public void run() {
		htmlCreator.updateHTML();
	}

}
