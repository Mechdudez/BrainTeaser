package com.kenzie.appserver.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.util.UUID;

public class UUIDConverter implements DynamoDBTypeConverter<String,
        UUID> {
    @Override
    public String convert(final UUID uuid) {
        return uuid.toString();
    }
    @Override
    public UUID unconvert(final String stringValue) {
        return UUID.fromString(stringValue);
    }
}
