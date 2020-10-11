package com.example.careservice.models;

import com.example.careservice.services.requests.AddReadingRequest;

public class ReadingModel {
    public int id;
    public String vehicleNumber;
    public String valueType;
    public String value;
    public String latitude = "";
    public String longitude = "";
    public String timestamp;
    public int cloudUpdate = 0;

    public AddReadingRequest getReadingRequest() {
        AddReadingRequest readingRequest = new AddReadingRequest();
        readingRequest.value = value;
        readingRequest.valueType = valueType;
        readingRequest.vehicleNumber = vehicleNumber;
        readingRequest.latitude = latitude;
        readingRequest.longitude = longitude;
        return readingRequest;
    }
}
