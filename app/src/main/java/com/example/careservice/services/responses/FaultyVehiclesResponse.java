package com.example.careservice.services.responses;

import com.example.careservice.models.FaultyVehicleModel;
import com.example.careservice.services.pojo.FaultyVehicle;

import java.util.ArrayList;
import java.util.List;

public class FaultyVehiclesResponse {
    public List<FaultyVehicle> vehicles = new ArrayList<>();

    public List<FaultyVehicleModel> getFaultyVehicleModels() {
        List<FaultyVehicleModel> vehicleModels = new ArrayList<>();
        for(FaultyVehicle vehicle : vehicles) {
            vehicleModels.add(vehicle.getFaultyVehicleModel());
        }
        return vehicleModels;
    }
}
