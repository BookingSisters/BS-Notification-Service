package com.bs.notification.models;

import com.bs.notification.exceptions.DeviceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import javax.inject.Inject;

@Slf4j
public class DeviceDAO {
    private final DynamoDbTable<Device> deviceTable;

    @Inject
    public DeviceDAO(DynamoDbTable<Device> deviceTable) {
        this.deviceTable = deviceTable;
    }

    public Device getDevice(String userId) {
        return deviceTable.getItem(Key.builder().partitionValue(userId).build());
    }

    public void saveDevice(Device device) {
        deviceTable.putItem(device);
    }

    public void updateDeviceToken(String userId, String newToken) {
        Device device = getDevice(userId);
        if(device == null) {
            log.error(String.format(DeviceNotFoundException.NOT_FOUND_MESSAGE_FORMAT, userId));
            throw new DeviceNotFoundException(userId);
        }
        device.setToken(newToken);
        saveDevice(device);
    }
}
