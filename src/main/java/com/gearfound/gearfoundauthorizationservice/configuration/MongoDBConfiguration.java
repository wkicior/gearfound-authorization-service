package com.gearfound.gearfoundauthorizationservice.configuration;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import javax.validation.constraints.NotNull;

@EnableReactiveMongoRepositories
public class MongoDBConfiguration extends AbstractMongoConfiguration {

    @NotNull
    @Value("${spring.data.mongodb.host}")
    private String databaseHost;

    @NotNull
    @Value("${spring.data.mongodb.port}")
    private int databasePort;

    @Bean
    public MongoTemplate reactiveMongoTemplate() {
        return new MongoTemplate(mongoClient(), "gearfound-authorization");
    }

    @Override
    protected String getDatabaseName() {
        return "gearfound-authorization";
    }

    @Override
    public MongoClient mongoClient() {
        return new MongoClient(databaseHost, databasePort);
    }
}
