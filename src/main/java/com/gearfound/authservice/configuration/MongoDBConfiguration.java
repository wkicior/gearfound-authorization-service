package com.gearfound.authservice.configuration;


import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
public class MongoDBConfiguration extends AbstractReactiveMongoConfiguration {

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(reactiveMongoClient(), "gearfound-authorization");
    }

    @Override
    protected String getDatabaseName() {
        return "gearfound-authorization";
    }

    @Override
    public MongoClient reactiveMongoClient() {
        return MongoClients.create();
    }
}


