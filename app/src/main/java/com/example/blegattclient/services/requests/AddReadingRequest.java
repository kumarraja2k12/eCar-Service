package com.example.blegattclient.services.requests;

public class AddReadingRequest {

    public String vehicleNumber;
    public String valueType;
    public String value;
    public String latitude;
    public String longitude;

    @Override
    public String toString() {
        return "AddReadingRequest{" +
                "vehicleNumber='" + vehicleNumber + '\'' +
                ", valueType='" + valueType + '\'' +
                ", value='" + value + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
