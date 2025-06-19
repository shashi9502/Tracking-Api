package com.Tracking_API.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;




public record TrackingRequest(
        @NotBlank String originCountryId,
        @NotBlank String destinationCountryId,
        @NotNull BigDecimal weight,
        @NotNull OffsetDateTime createdAt,
        @NotNull UUID customerId,
        @NotBlank String customerName,
        @NotBlank String customerSlug
) {
	
}
