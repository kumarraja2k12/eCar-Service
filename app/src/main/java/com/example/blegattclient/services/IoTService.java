package com.example.blegattclient.services;

import android.content.Context;

import com.example.blegattclient.services.requests.AddReadingRequest;
import com.example.blegattclient.services.requests.RegisterVehicleRequest;

public class IoTService {

    private VolleyServiceAdapter serviceAdapter;

    private static IoTService instance;
    public static IoTService GetIoTService(Context context) {
        if(instance == null) {
            instance = new IoTService(context);
        }
        return instance;
    }

    private IoTService(Context context)
    {
        serviceAdapter = new VolleyServiceAdapter(context);
    }

    public void RegisterVehicle(final RegisterVehicleRequest vehicleRequest, final IServiceCallback callback)
    {
        serviceAdapter.GetToken(new IServiceCallback() {

            @Override
            public void onError(Object error) {
                //Do nothing
            }

            @Override
            public void OnCompleted(Object response) {
                serviceAdapter.RegisterVehicle(vehicleRequest, new IServiceCallback() {
                    @Override
                    public void OnCompleted(Object response) {
                        callback.OnCompleted(response);
                    }

                    @Override
                    public void onError(Object error) {
                        callback.onError(error);
                    }
                }, (String)response);
            }
        });
    }

    public void AddReading(final AddReadingRequest readingRequest, final IServiceCallback callback)
    {
        serviceAdapter.GetToken(new IServiceCallback() {

            @Override
            public void onError(Object error) {
                //Do nothing
            }

            @Override
            public void OnCompleted(Object response) {
                serviceAdapter.AddReading(readingRequest, new IServiceCallback() {
                    @Override
                    public void OnCompleted(Object response) {
                        callback.OnCompleted(response);
                    }

                    @Override
                    public void onError(Object error) {
                        callback.onError(error);
                    }
                }, (String)response);
            }
        });
    }
}

