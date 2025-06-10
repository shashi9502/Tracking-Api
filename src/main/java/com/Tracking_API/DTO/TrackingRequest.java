package com.Tracking_API.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;




public record TrackingRequest(
        @NotBlank String origin_country_id,
        @NotBlank String destination_country_id,
        @NotNull BigDecimal weight,
        @NotNull OffsetDateTime created_at,
        @NotNull UUID customer_id,
        @NotBlank String customer_name,
        @NotBlank String customer_slug
) {
	
}
