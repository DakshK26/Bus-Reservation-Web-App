package com.web.bus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/*
* @author: Daksh & Ashwin
* Date: Jan. 2023
* Description: Class to start web app
* Method List: public static void main(String[] args)
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.web.bus.services")
@EntityScan(basePackages = "com.web.bus.entities")
public class BusApplication {

	/**
	 * Build Web App
	 * @param args
	 */
	public static void main(String[] args) throws URISyntaxException, IOException {
		SpringApplication.run(BusApplication.class, args);

		Runtime rt = Runtime.getRuntime();
		String url = "http://localhost:8080/";
		rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
	}
}


