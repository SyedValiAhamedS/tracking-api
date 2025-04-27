package com.teleport.tracking_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teleport.tracking_api.dto.TrackingNumberResponse;
import com.teleport.tracking_api.util.TrackingNumberGenerator;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TrackingApiService {

	private final TrackingNumberGenerator trackingNumGen;
	
	Logger logger = LoggerFactory.getLogger(TrackingApiService.class);

	public TrackingApiService(TrackingNumberGenerator trackingNumGen) {
		this.trackingNumGen = trackingNumGen;
	}

	public TrackingNumberResponse generateTrackingNumber(String originCountryId, String destinationCountryId,
			String weight, OffsetDateTime createdTime, String customerId, String customerName, String customerSlug) {
		String nextTrackNum = trackingNumGen.nextTrackingNumber();

		// Create a prefix using the default constant and destination country codes.
		// For example, destination 'ID' results in the prefix "TPID".
		String prefix = ("TP" + destinationCountryId).toUpperCase();

		// Combine the prefix and the unique id.
		String trackingNumber = prefix + nextTrackNum;

		// Ensure the tracking number meets the regex constraint by not exceeding 16
		// characters.
		if (trackingNumber.length() > 16) {
			// Calculate how many characters are allowed for the id part.
			int allowedIdPartLength = 16 - prefix.length();
			// Trim the id part from the beginning (keeping the rightmost characters, which
			// change more rapidly)
			nextTrackNum = nextTrackNum.substring(nextTrackNum.length() - allowedIdPartLength);
			trackingNumber = prefix + nextTrackNum;
		}
		
		logger.info("Generated tracking number for customerId {} is {} ", customerId, trackingNumber);

		// Capture the creation timestamp in RFC 3339 format.
		String generatedAt = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

		return new TrackingNumberResponse(trackingNumber, generatedAt);
	}
}
