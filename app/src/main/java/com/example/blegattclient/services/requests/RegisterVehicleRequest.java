package com.example.blegattclient.services.requests;

public class RegisterVehicleRequest {

    public RegisterVehicleRequest() {}

    public RegisterVehicleRequest(String customerName, String phoneNumber, String vehicleNumber, String model) {
        this.phoneNumber = phoneNumber;
        this.model = model;
        this.customerName = customerName;
        this.vehicleNumber = vehicleNumber;
    }

    public String vehicleNumber;
    public String model;
    public String phoneNumber;
    public String customerName;
}
