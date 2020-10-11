package com.example.careservice.services.responses;

import com.google.gson.annotations.SerializedName;

public class RegisterVehicleResponse {

    @SerializedName("AssetId")
    public String assetId;

    @SerializedName("RegistrationStatus")
    public String registrationStatus;

    //@SerializedName("Thresholds")
    //public List<Threshold> thresholds = new ArrayList<>();
}
