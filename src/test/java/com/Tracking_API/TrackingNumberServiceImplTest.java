package com.Tracking_API;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.Tracking_API.DTO.TrackingRequest;
import com.Tracking_API.DTO.TrackingResponse;
import com.Tracking_API.Exception.TrackerException;
import com.Tracking_API.ServiceImpl.TrackingNumberServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TrackingNumberServiceImplTest {

    @InjectMocks
    private TrackingNumberServiceImpl trackingNumberService;

    @Mock
    private TrackingRequest trackingRequest;

    private String originCountryId = "US";
    private String destinationCountryId = "IN";
    private BigDecimal weight = BigDecimal.valueOf(5.0);
    private OffsetDateTime createdAt = OffsetDateTime.now();
    private UUID customerId = UUID.randomUUID();
    private String customerName = "John Doe";
    private String customerSlug = "john-doe";

    @BeforeEach
    public void setUp() {
        // Mock the TrackingRequest
        trackingRequest = mock(TrackingRequest.class);
        when(trackingRequest.origin_country_id()).thenReturn(originCountryId);
        when(trackingRequest.destination_country_id()).thenReturn(destinationCountryId);
        when(trackingRequest.weight()).thenReturn(weight);
        when(trackingRequest.created_at()).thenReturn(createdAt);
        when(trackingRequest.customer_id()).thenReturn(customerId);
        when(trackingRequest.customer_name()).thenReturn(customerName);
        when(trackingRequest.customer_slug()).thenReturn(customerSlug);
    }

    @Test
    public void testGenerateTrackingNumber_success() {
        // Execute the method to test
        TrackingResponse response = trackingNumberService.generateTrackingNumber(trackingRequest);

        // Validate the result
        assertNotNull(response);
        assertNotNull(response.getTracking_number());
        assertEquals(16, response.getTracking_number().length(), "Tracking number should be 16 characters long");
        assertNotNull(response.getCreatedAt());
    }

    @Test
    public void testGenerateTrackingNumber_exceptionHandling() {
        // Simulating an exception in the logic (you can force an exception for testing)
        doThrow(new RuntimeException("Internal error")).when(trackingNumberService).generateUniqueTrackingNumber(any());

        // Validate that TrackerException is thrown
        TrackerException thrown = assertThrows(TrackerException.class, () -> {
            trackingNumberService.generateTrackingNumber(trackingRequest);
        });

        assertEquals("Please try after sometime!", thrown.getMessage());
    }

    @Test
    public void testGenerateTrackingNumber_nullRequest() {
        // Passing a null TrackingRequest to check for null safety
        assertThrows(NullPointerException.class, () -> {
            trackingNumberService.generateTrackingNumber(null);
        });
    }

    @Test
    public void testGenerateTrackingNumber_edgeCases() {
        // Test with different edge cases, e.g. very small country IDs, empty customer slug etc.

        TrackingRequest edgeRequest = new TrackingRequest(
                "",  // Empty origin_country_id
                "IN",
                BigDecimal.ONE,
                OffsetDateTime.now(),
                UUID.randomUUID(),
                "Jane Doe",
                "jane-doe"
        );

        // Validate that the method handles empty origin_country_id properly
        assertThrows(NullPointerException.class, () -> {
            trackingNumberService.generateTrackingNumber(edgeRequest);
        });
    }

    @Test
    public void testGenerateUniqueTrackingNumber_format() {
        // Generate a unique tracking number from a valid request
        String trackingNumber = trackingNumberService.generateUniqueTrackingNumber(trackingRequest);

        // Check the length and that the tracking number meets the expected pattern
        assertTrue(trackingNumber.length() <= 16);
        assertTrue(trackingNumber.matches("[A-Z0-9]{16}"));
    }
}
