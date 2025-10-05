package com.example.TPO_Progra_III;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.QueryConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.concurrent.TimeUnit;

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

            var result = driver.executableQuery("""
                            CREATE (a:Person {name: $name})
                            CREATE (b:Person {name: $friendName})
                            CREATE (a)-[:KNOWS]->(b)
                            """)
                    .withParameters(Map.of("name", "Alice", "friendName", "David"))
                    .withConfig(QueryConfig.builder().withDatabase("neo4j").build())
                    .execute();

            var summary = result.summary();
            System.out.printf("Created %d records in %d ms.%n",
                    summary.counters().nodesCreated(),
                    summary.resultAvailableAfter(TimeUnit.MILLISECONDS));

            var result2 = driver.executableQuery("""
                            MATCH (p:Person)-[:KNOWS]->(:Person)
                            RETURN p.name AS name
                            """)
                    .withConfig(QueryConfig.builder().withDatabase("neo4j").build())
                    .execute();

            var records2 = result2.records();
            records2.forEach(r -> {
                System.out.println(r.get("name").asString());  // or r.get("name").asString()
            });

            var summary2 = result2.summary();
            System.out.printf("The query %s returned %d records in %d ms.%n",
                    summary2.query(), records2.size(),
                    summary2.resultAvailableAfter(TimeUnit.MILLISECONDS));


        }
    }

}
