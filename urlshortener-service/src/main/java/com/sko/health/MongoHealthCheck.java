package com.sko.health;

import com.mongodb.Mongo;
import com.yammer.metrics.core.HealthCheck;

public class MongoHealthCheck extends HealthCheck {

    private Mongo mongo;

    public MongoHealthCheck(Mongo mongo) {
        super("mongo");
        this.mongo = mongo;
    }

    @Override
    protected Result check() {
        mongo.getDatabaseNames();
        return Result.healthy();
    }

}
