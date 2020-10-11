package com.example.careservice.services.requests;

public class CreateWorkOrderRequest {

    public CreateWorkOrderRequest(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String vehicleNumber;
}
