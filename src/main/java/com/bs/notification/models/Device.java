package com.bs.notification.models;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.time.Instant;
import java.util.List;

@Data
@DynamoDbBean
public class Device {
    private String userId;
    private String token;
    private List<Message> messages;
    private Instant createdAt;

    @DynamoDbPartitionKey
    public String getUserId() {return userId;}

    @DynamoDbSortKey
    public String getToken() {return token;}

    @DynamoDbAttribute("Messages")
    public List<Message> getMessages() {
        return messages;
    }

    @DynamoDbAttribute("CreatedAt")
    public Instant getCreatedAt() {
        return createdAt;
    }
}
