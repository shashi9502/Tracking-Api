package com.Tracking_API.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class TrackingResponse {
    private String tracking_number;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull private OffsetDateTime createdAt;
}
