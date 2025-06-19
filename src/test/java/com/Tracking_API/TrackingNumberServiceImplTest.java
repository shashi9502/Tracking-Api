package com.Tracking_API;

import com.Tracking_API.DTO.TrackingRequest;
import com.Tracking_API.DTO.TrackingResponse;
import com.Tracking_API.Exception.TrackerException;
import com.Tracking_API.ServiceImpl.TrackingNumberServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrackingNumberServiceImplTest {

    private TrackingNumberServiceImpl trackingNumberService;
    private TrackingRequest trackingRequest;

    private final String originCountryId = "US";
    private final String destinationCountryId = "IN";
    private final BigDecimal weight = BigDecimal.valueOf(5.0);
    private final OffsetDateTime createdAt = OffsetDateTime.now();
    private final UUID customerId = UUID.randomUUID();
    private final String customerName = "John Doe";
    private final String customerSlug = "john-doe";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        trackingNumberService = new TrackingNumberServiceImpl();

        trackingRequest = mock(TrackingRequest.class);
        when(trackingRequest.originCountryId()).thenReturn(originCountryId);
        when(trackingRequest.destinationCountryId()).thenReturn(destinationCountryId);
        when(trackingRequest.weight()).thenReturn(weight);
        when(trackingRequest.createdAt()).thenReturn(createdAt);
        when(trackingRequest.customerId()).thenReturn(customerId);
        when(trackingRequest.customerName()).thenReturn(customerName);
        when(trackingRequest.customerSlug()).thenReturn(customerSlug);
    }

    @Test
    public void testGenerateTrackingNumber_SuccessfulGeneration() {
        TrackingResponse response = trackingNumberService.generateTrackingNumber(trackingRequest);

        assertNotNull(response, "Response should not be null");
        assertNotNull(response.getTracking_number(), "Tracking number should not be null");
        assertEquals(16, response.getTracking_number().length(), "Tracking number should be 16 characters long");
        assertNotNull(response.getCreatedAt(), "CreatedAt should not be null");
    }

    @Test
    public void testGenerateTrackingNumber_ThrowsTrackerException() {
        TrackingNumberServiceImpl spyService = spy(trackingNumberService);
        doThrow(new RuntimeException("Simulated failure"))
                .when(spyService).generateUniqueTrackingNumber(any());

        TrackerException thrown = assertThrows(
            TrackerException.class,
            () -> spyService.generateTrackingNumber(trackingRequest),
            "Expected a TrackerException to be thrown"
        );

        assertEquals("Please try after sometime!", thrown.getMessage());
    }

    @Test
    public void testGenerateTrackingNumber_WithNullRequest_ShouldThrowNullPointerException() {
    	TrackerException exception = assertThrows(TrackerException.class, () -> {
            trackingNumberService.generateTrackingNumber(null);
        });
        assertEquals("Invalid tracking request.", exception.getMessage());
    }

    @Test
    public void testGenerateTrackingNumber_WithEmptyOriginCountryId_ShouldFail() {
        TrackingRequest invalidRequest = new TrackingRequest(
            "", // Empty origin_country_id
            "IN",
            BigDecimal.ONE,
            OffsetDateTime.now(),
            UUID.randomUUID(),
            "Jane Doe",
            "jane-doe"
        );

        TrackerException exception = assertThrows(TrackerException.class, () -> {
            trackingNumberService.generateTrackingNumber(invalidRequest);
        });

        assertEquals("Invalid tracking request.", exception.getMessage());
    }

    @Test
    public void testGenerateUniqueTrackingNumber_FormatCheck() {
        String trackingNumber = trackingNumberService.generateUniqueTrackingNumber(trackingRequest);

        assertTrue(trackingNumber.length() <= 16, "Tracking number should not exceed 16 characters");
        //assertTrue(trackingNumber.matches("[A-Z0-9]+"), "Tracking number should contain only uppercase alphanumerics");
    }
}