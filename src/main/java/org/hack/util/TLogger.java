package org.hack.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TLogger{
	private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static Logger reportlogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	static{
		TLogger.initialize();
	}
	public static void logInfo(String message){
		logger.info(message);
	}
	
	public static void logInfo(String message, Map<String, String> params){
		logger.info(new StringBuilder(message).append(params.toString()).toString());
	}
	
	public static void reportLog(Map<String, String> params){
		logger.info(new StringBuilder("Report:").append(params.toString()).toString());
	}
	public static void reportLog(String message){
		logger.info(new StringBuilder("Report:").append(message).toString());
	}	
	
	private static void initialize(){
		try {
			// suppress the logging output to the console
			Map<String, Logger> loggers = new HashMap<String, Logger>();
			loggers.put("logger", logger);
			loggers.put("reportlogger", reportlogger);
			for(Entry<String, Logger> entry: loggers.entrySet()){
				Logger log = entry.getValue();
				Handler[] handlers = log.getHandlers();
			    for(Handler hand: handlers){
			    	 if (hand instanceof ConsoleHandler) {
			    		 log.removeHandler(hand);
			   	    }
			    }
			    // log handler
			    Handler handler = null;
			    if(entry.getKey() == "reportlogger"){
				    handler = new FileHandler("C:\\report.log");
				    log.setLevel(Level.INFO);
			    }else{
			    	 handler = new FileHandler("C:\\info.log");
					    log.setLevel(Level.INFO);
			    }
			    handler.setFormatter(new SimpleFormatter());
			    log.addHandler(handler);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
}
