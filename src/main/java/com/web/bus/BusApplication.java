package com.web.bus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
* @author: Daksh & Ashwin
* Date: Jan. 2023
* Description: Class to start web app
* Method List: public static void main(String[] args)
 */
@SpringBootApplication
public class BusApplication {
	/**
	 * Build Web App
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(BusApplication.class, args);
	}

}
