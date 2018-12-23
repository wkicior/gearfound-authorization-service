package com.gearfound.gearfoundauthorizationservice.configuration;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;


@EnableReactiveMongoRepositories
public class MongoDBConfiguration extends AbstractMongoConfiguration {


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
        return new MongoClient("gearfound-authorization-mongo", 27017);
    }
}
