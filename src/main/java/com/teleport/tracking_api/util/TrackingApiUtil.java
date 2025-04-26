package com.teleport.tracking_api.util;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.OffsetDateTime;

public class TrackingApiUtil {
	public static OffsetDateTime retrieveOffsetDateTimeInRFC3339Format(String dateTimeString) throws Exception {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
			return OffsetDateTime.parse(dateTimeString, formatter); // The string matches the RFC 3339 format
		} catch (DateTimeParseException e) {
			throw new Exception ("Invalid created At Offset Date time stamp format"); // The string does not match the RFC 3339 format
		}
	}

}
