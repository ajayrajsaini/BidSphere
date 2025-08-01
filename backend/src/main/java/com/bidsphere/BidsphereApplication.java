package com.bidsphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BidsphereApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidsphereApplication.class, args);

//		while(true){
			System.out.println("Working");
//		}
	}

}
