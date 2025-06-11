package com.Tracking_API.ServiceImpl;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.Tracking_API.DTO.TrackingRequest;
import com.Tracking_API.DTO.TrackingResponse;
import com.Tracking_API.Exception.TrackerException;
import com.Tracking_API.Service.TrackingNumService;

@Service
public class TrackingNumberServiceImpl implements TrackingNumService {
	 private static final Logger log = LoggerFactory.getLogger(TrackingNumberServiceImpl.class);
    @Override
    public synchronized TrackingResponse generateTrackingNumber(TrackingRequest request)  {
    	try {
    	log.info("tracking request info {}:",request);
        String trackingNumber = generateUniqueTrackingNumber(request);
        log.info("Generated tracking number: {}", trackingNumber);
        return new TrackingResponse(trackingNumber, OffsetDateTime.now());
    	}
    	catch(Exception ex) {
    		log.error("tracking request error info {}:",ex.getMessage());
    		throw new TrackerException("Please try after sometime!");
    	}
    }

    public String generateUniqueTrackingNumber(TrackingRequest request) {
        String base = (request.origin_country_id().toUpperCase()
                + request.destination_country_id().toUpperCase()
                + request.customer_slug().replace("-", "").toUpperCase()
                + UUID.randomUUID().toString().replace("-", "").substring(0, 6));
        String trackingNumber = base.substring(0, Math.min(base.length(), 16));
        return trackingNumber;
    	 
    }
}
