package com.springboot.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}

	//MongoDatabaseFactory dbFactory: Automatically created by Spring Boot, it provides access to the MongoDB database connection.

	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}

}
//The @EnableTransactionManagement annotation is used to enable transaction management in Spring.
//- **`PlatformTransactionManager`**: A Spring interface for managing transactions across various data access technologies (e.g., JDBC, JPA).
//		- **`MongoTransactionManager`**: A specific implementation of `PlatformTransactionManager` for managing transactions in MongoDB.