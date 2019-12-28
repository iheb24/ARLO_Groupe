package com.example.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class Logger {
	
	private java.util.logging.Logger log;
	private FileHandler fh;
	
	public Logger (String name) {
		
		this.log = java.util.logging.Logger.getLogger("MyLog");
		try {
			fh = new FileHandler("./"+name+".txt");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        log.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);  

		
		
	}
	public java.util.logging.Logger getLogger() {
		return this.log;
	}
	

}
