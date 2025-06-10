package com.Tracking_API;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.Tracking_API.DTO.TrackingRequest;
import com.Tracking_API.DTO.TrackingResponse;
import com.Tracking_API.Entity.TrackingNumber;
import com.Tracking_API.Exception.TrackerException;
import com.Tracking_API.Repository.TrackingNumberRepository;
import com.Tracking_API.ServiceImpl.TrackingNumberServiceImpl;
@SpringBootTest
class TrackingNumberServiceImplTest {

    private TrackingNumberRepository repository;
    private TrackingNumberServiceImpl service;

    @BeforeEach
    void setup() {
        repository = mock(TrackingNumberRepository.class);
        service = new TrackingNumberServiceImpl(repository);
    }

    private TrackingRequest sampleRequest() {
        return new TrackingRequest(
                "MY",
                "ID",
                new BigDecimal("1.234"),
                OffsetDateTime.now(),
                UUID.fromString("de619854-b59b-425e-9db4-943979e1bd49"),
                "RedBox Logistics",
                "redbox-logistics"
        );
    }

    @Test
    void testGenerateTrackingNumber_success() {
        // Arrange
        TrackingRequest request = sampleRequest();
        when(repository.existsByTrackingNumber(anyString())).thenReturn(false);

        // Act
        TrackingResponse response = service.generateTrackingNumber(request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getTracking_number());
        assertNotNull(response.getCreatedAt());
        assertTrue(response.getTracking_number().matches("^[A-Z0-9]{1,16}$"));
        verify(repository, times(1)).save(any(TrackingNumber.class));
    }

    @Test
    void testGenerateTrackingNumber_withCollision() {
        // Arrange
        TrackingRequest request = sampleRequest();

        // First call returns true (collision), second returns false (unique)
        when(repository.existsByTrackingNumber(anyString()))
                .thenReturn(true)
                .thenReturn(false);

        // Act
        TrackingResponse response = service.generateTrackingNumber(request);

        // Assert
        assertNotNull(response);
        verify(repository, atLeast(2)).existsByTrackingNumber(anyString());
        verify(repository, times(1)).save(any(TrackingNumber.class));
    }

    @Test
    void testGenerateTrackingNumber_exception() {
        // Arrange
        TrackingRequest request = sampleRequest();

        when(repository.save(any())).thenThrow(new RuntimeException("DB Error"));

        // Act & Assert
        TrackerException ex = assertThrows(TrackerException.class,
                () -> service.generateTrackingNumber(request));
        assertEquals("Please try after sometime!", ex.getMessage());
    }
}
