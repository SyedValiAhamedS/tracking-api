package com.teleport.tracking_api.controller;

import java.time.OffsetDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teleport.tracking_api.dto.TrackingNumberResponse;
import com.teleport.tracking_api.service.TrackingApiService;
import com.teleport.tracking_api.util.TrackingApiUtil;
import com.teleport.tracking_api.util.ValidCountryCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@RestController
public class TrackingApiController {

	private final TrackingApiService trackingApiService;

	public TrackingApiController(TrackingApiService trackingNumberService) {
		this.trackingApiService = trackingNumberService;
	}

	@GetMapping("/next-tracking-number")
	public TrackingNumberResponse getNextTrackingNumber(
			@NotBlank(message = "Origin country cannot be blank") @ValidCountryCode @RequestParam(name = "origin_country_id") String originCountryId,
			@NotBlank(message = "Destination country cannot be blank") @ValidCountryCode @RequestParam(name = "destination_country_id") String destinationCountryId,
			@NotBlank(message = "Weight cannot be blank") @Pattern(regexp = "^\\d+(\\.\\d{1,3})?$", message = "Weight must be a valid number with up to three decimal places") @RequestParam String weight,
			@RequestParam("created_at") String createdAt,
			@NotBlank(message = "Customer ID cannot be blank") @RequestParam(name = "customer_id") String customerId,
			@NotBlank(message = "Customer Name cannot be blank") @RequestParam(name = "customer_name") String customerName,
			@NotBlank(message = "Customer slug cannot be blank") @RequestParam(name = "customer_slug") String customerSlug)
			throws Exception {
		OffsetDateTime createdTimeStamp = TrackingApiUtil.retrieveOffsetDateTimeInRFC3339Format(createdAt);

		return trackingApiService.generateTrackingNumber(originCountryId, destinationCountryId, weight,
				createdTimeStamp, customerId, customerName, customerSlug);
	}
}
