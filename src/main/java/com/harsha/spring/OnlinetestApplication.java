/*
 * @Author: Harsha
 */


package com.harsha.spring;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlinetestApplication {
	
	private final static Logger LOGGER = LoggerFactory.logger(OnlinetestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(OnlinetestApplication.class, args);
		LOGGER.info("application started");
	}

}
