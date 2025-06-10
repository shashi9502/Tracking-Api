package com.Tracking_API.Service;

import com.Tracking_API.DTO.TrackingRequest;
import com.Tracking_API.DTO.TrackingResponse;

public interface TrackingNumService {

    TrackingResponse generateTrackingNumber(TrackingRequest request);
}
