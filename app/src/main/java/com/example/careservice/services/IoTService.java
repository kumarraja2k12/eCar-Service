package com.example.careservice.services;

import android.content.Context;
import android.util.Log;

import com.example.careservice.services.requests.AddReadingRequest;
import com.example.careservice.services.requests.RegisterVehicleRequest;

public class IoTService {

    private VolleyServiceAdapter serviceAdapter;

    private static IoTService instance;
    public static IoTService getInstance(Context context) {
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
        Log.d("IotService: AddReading",  readingRequest.toString());
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
                        Log.d("IotService: AddReading",  "Reading Uploaded Successfully..");
                        callback.OnCompleted(response);
                    }

                    @Override
                    public void onError(Object error) {
                        Log.d("IotService: AddReading",  "Failed to Upload Reading ..");
                        callback.onError(error);
                    }
                }, (String)response);
            }
        });
    }
}

