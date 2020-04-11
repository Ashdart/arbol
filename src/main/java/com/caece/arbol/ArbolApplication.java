package com.caece.arbol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableMongoRepositories
@EnableSwagger2
@PropertySource({"classpath:application.properties"})
public class ArbolApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArbolApplication.class, args);
	}

}
