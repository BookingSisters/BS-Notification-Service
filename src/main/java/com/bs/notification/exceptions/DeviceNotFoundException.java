package com.bs.notification.exceptions;

public class DeviceNotFoundException extends IllegalArgumentException{
    public static final String NOT_FOUND_MESSAGE_FORMAT = "Device 정보를 찾을 수 없습니다 : %s";

    public DeviceNotFoundException(String id) {
        super(String.format(NOT_FOUND_MESSAGE_FORMAT, id));
    }
}
