package com.web.bus;

import com.web.bus.entities.Customer;
import com.web.bus.services.CustomerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

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
	public static void main(String[] args) {
		SpringApplication.run(BusApplication.class, args);
	}

}
