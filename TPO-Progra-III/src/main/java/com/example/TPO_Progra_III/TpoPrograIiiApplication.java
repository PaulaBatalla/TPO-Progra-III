package com.example.TPO_Progra_III;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TpoPrograIiiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TpoPrograIiiApplication.class, args);

		final String dbUri = "neo4j+s://6f05a98a.databases.neo4j.io";
		final String dbUser = "neo4j";
		final String dbPassword = "9udAN6IwIGmZj7r8uNtAtz__DPf61SPJHJz0LwrXfn8";

		try (var driver = GraphDatabase.driver(dbUri, AuthTokens.basic(dbUser, dbPassword))) {
			driver.verifyConnectivity();
			System.out.println("Connection established.");
		}
	}

}
