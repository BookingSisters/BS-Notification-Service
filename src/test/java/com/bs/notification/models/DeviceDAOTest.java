package com.bs.notification.models;

import com.bs.notification.exceptions.DeviceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceDAOTest {

    @InjectMocks
    private DeviceDAO deviceDAO;

    @Mock
    private DynamoDbTable<Device> mockTable;

    private Device dbDevice;
    private String expectUserId;
    private String expectToken;

    @BeforeEach
    void setUp() {
        dbDevice = new Device();
        expectUserId = "testUser";
        expectToken = "testToken";

        dbDevice.setUserId(expectUserId);
        dbDevice.setToken(expectToken);
    }

    @DisplayName("userId로 device정보 조회")
    @Test
    void getDevice() {
        doReturn(dbDevice).when(mockTable).getItem(Key.builder().partitionValue(expectUserId).build());

        Device result = deviceDAO.getDevice(expectUserId);

        assertThat(result.getUserId()).isEqualTo(expectUserId);
        assertThat(result.getToken()).isEqualTo(expectToken);
    }

    @DisplayName("device정보 저장")
    @Test
    void saveDevice() {
        Device device = new Device();
        doNothing().when(mockTable).putItem(device);

        deviceDAO.saveDevice(device);

        verify(mockTable, times(1)).putItem(device);
    }

    @DisplayName("기기 토큰 값 변경 시 device 없음 예외 핸들링")
    @Test
    void updateDeviceToken_whenGetDeviceReturnsNull_throwsProperException() {
        doReturn(null).when(mockTable).getItem(Key.builder().partitionValue(expectUserId).build());

        assertThrows(DeviceNotFoundException.class,
                () -> deviceDAO.updateDeviceToken(expectUserId, expectToken));
    }
}