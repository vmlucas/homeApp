package com.home.mongocloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching 
public class MongocloudApplication {  
     
	public static void main(String[] args) {
		//Class[] sources = {MongocloudApplication.class, appConfig.class};
		SpringApplication.run(MongocloudApplication.class, args);
	}

}
