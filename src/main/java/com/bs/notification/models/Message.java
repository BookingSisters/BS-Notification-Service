package com.bs.notification.models;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Data
@DynamoDbBean
public class Message {
    private String id;
    private String type;
    private String title;
    private String content;
    private Boolean readYn;
    private String createdAt;
}
