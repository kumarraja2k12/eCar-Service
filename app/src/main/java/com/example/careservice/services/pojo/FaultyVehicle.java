package com.example.careservice.services.pojo;

import com.example.careservice.models.FaultyVehicleModel;
import com.google.gson.annotations.SerializedName;

public class FaultyVehicle {
    @SerializedName("RegistrationNumber")
    public String registrationNumber;

    @SerializedName("CustomerName")
    public String customerName;

    @SerializedName("Phone")
    public String phone;

    @SerializedName("ActiveWorkOrders")
    public String activeWorkOrders;

    public FaultyVehicleModel getFaultyVehicleModel() {

        FaultyVehicleModel faultyVehicleModel = new FaultyVehicleModel();
        faultyVehicleModel.customerName = customerName;
        faultyVehicleModel.registrationNumber = registrationNumber;
        faultyVehicleModel.activeWorkOrders = activeWorkOrders;
        faultyVehicleModel.phone = phone;

        return faultyVehicleModel;
    }
}
