package com.Tracking_API.Repository;

import com.Tracking_API.Entity.TrackingNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingNumberRepository extends JpaRepository<TrackingNumber, Long> {
    boolean existsByTrackingNumber(String trackingNumber);
}
