package com.example.blegattclient.ble;

import java.util.HashMap;

public class GattUtils {
    private static HashMap<String, String> attributes = new HashMap();
    public static String SERVICE_UUID = "01001805-0000-1000-8000-00805F9B34FB".toLowerCase();
    public static String FLUID_LEVEL_UUID = "01002A2B-0000-1000-8000-00805F9B34FB".toLowerCase();
    public static String CO_UUID = "01002B2C-0000-1000-8000-00805F9B34FB".toLowerCase();

    static {
        // Services.
        attributes.put(SERVICE_UUID, "Service");
        // Characteristics.
        attributes.put(FLUID_LEVEL_UUID, "Fluid level");
        attributes.put(CO_UUID, "CO level");
    }

    public static boolean contains(String uuid) {
        return attributes.containsKey(uuid.toLowerCase());
    }

    public static String lookup(String uuid) {
        return attributes.get(uuid.toLowerCase());
    }
}