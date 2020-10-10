package com.example.blegattclient.services.pojo;

import com.google.gson.annotations.SerializedName;

public class Threshold {

    public static Threshold GetThreshold(String thresholdString) {

        if(thresholdString == null || thresholdString.isEmpty()) {
            return null;
        }

        String[] split = thresholdString.split(",");
        Threshold threshold = new Threshold();
        threshold.type = split[0];
        threshold.operator = split[1];
        threshold.warningThreshold = Double.parseDouble(split[2]);
        threshold.criticalThreshold = Double.parseDouble(split[3]);

        return threshold;
    }

    @SerializedName("Type")
    public String type;

    @SerializedName("WarningThreshold")
    public double warningThreshold;

    @SerializedName("criticalThreshold")
    public double criticalThreshold;

    @SerializedName("Operator")
    public String operator;

    @Override
    public String toString() {
        return type +
                "," + operator +
                "," + warningThreshold +
                "," + criticalThreshold;
    }
}
