package com.bs.notification;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.bs.notification.configs.DynamoDbConfig;
import com.bs.notification.models.Device;
import com.bs.notification.models.DeviceDAO;
import com.google.inject.Guice;
import com.google.inject.Injector;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class SQSHandler implements RequestHandler<SQSEvent, Void> {
    private static final Injector injector = Guice.createInjector(new DynamoDbConfig());
    private static final DynamoDbClient dynamoDbClient = injector.getInstance(DynamoDbClient.class);;
    private static final DynamoDbEnhancedClient dynamoDbEnhancedClient = injector.getInstance(DynamoDbEnhancedClient.class);
    private static final DynamoDbTable<Device> deviceTable = injector.getInstance(DynamoDbTable.class);
    private static final DeviceDAO deviceDAO = new DeviceDAO(deviceTable);

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        for(SQSEvent.SQSMessage msg:event.getRecords()) {
            System.out.println(msg.getBody());
        }
        return null;
    }
}
