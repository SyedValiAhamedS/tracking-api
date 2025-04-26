package com.teleport.tracking_api.dto;

public class TrackingNumberResponse {
    private String trackingNumber;
    private String createdAt;

    public TrackingNumberResponse(String trackingNumber, String createdAt) {
        this.trackingNumber = trackingNumber;
        this.createdAt = createdAt;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}


/* Install the Heroku CLI.

Run heroku create your-app-name.

If using environment variables for worker IDs, set them using heroku config:set WORKER_ID=1 (or the unique ID for this instance).

Deploy with git push heroku master.

*/