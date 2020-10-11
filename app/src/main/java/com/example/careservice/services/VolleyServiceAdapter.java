package com.example.careservice.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.careservice.services.requests.AddReadingRequest;
import com.example.careservice.services.requests.CreateWorkOrderRequest;
import com.example.careservice.services.requests.RegisterVehicleRequest;
import com.example.careservice.services.responses.AddReadingResponse;
import com.example.careservice.services.responses.ErrorResponse;
import com.example.careservice.services.pojo.FaultyVehicle;
import com.example.careservice.services.responses.FaultyVehiclesResponse;
import com.example.careservice.services.responses.RegisterVehicleResponse;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VolleyServiceAdapter {

    //Resource in Postman
    private final String baseUrl = "https://pe-dev-ska16ed8ff91e599e60devaos.cloudax.dynamics.com";

    private RequestQueue queue;

    public VolleyServiceAdapter(Context context)
    {
        queue = Volley.newRequestQueue(context);
    }

    public void GetToken(final IServiceCallback callback) {

        String url = "https://login.microsoftonline.com/79d6758d-5a05-4ad0-8904-6552d3fd6230/oauth2/token";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            String accessToken = new JSONObject(response).getString("access_token");
                            Log.d("Access token: ", accessToken);
                            callback.OnCompleted(accessToken);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> obj = new HashMap<String, String>();
                obj.put("grant_type", "client_credentials");
                obj.put("client_id", "6a370b5b-dbd0-4d3a-9471-262e6373dc0e");
                obj.put("client_secret", "M8.3I8hV3O3.IoA0rwTA3Y~EA~_W6YlXQQ");
                obj.put("resource", "https://pe-dev-ska16ed8ff91e599e60devaos.cloudax.dynamics.com");

                return obj;
            }
        };
        queue.add(postRequest);
    }

    public void RegisterVehicle(final RegisterVehicleRequest vehicleRequest, final IServiceCallback callback, final String accessToken) {

        String url = baseUrl + "/api/services/SKAIoTServiceGroup/SKAIoTAssetService/VehicleRegistration";

        JSONObject obj = new JSONObject();
        try {
            obj.put("_registrationNumber", vehicleRequest.vehicleNumber);
            obj.put("_model", vehicleRequest.model);
            obj.put("_phoneNumber", vehicleRequest.phoneNumber);
            obj.put("_customerName", vehicleRequest.customerName);
        } catch (Exception e) {
            callback.onError(ErrorResponse.UnknownError());
        }

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                RegisterVehicleResponse vehicleResponse = null;
                try {
                    String assetId = response.getString("AssetId");
                    String registrationStatus = response.getString("RegistrationStatus");
                    JSONArray thresholdsJsonArray = response.optJSONArray("Thresholds");
                    if(thresholdsJsonArray != null) {
                        for(int counter = 0; counter < thresholdsJsonArray.length(); counter++) {
                            JSONObject jsonObject = thresholdsJsonArray.getJSONObject(counter);
                            String type = jsonObject.getString("Type");
                        }
                    }
                    vehicleResponse = new RegisterVehicleResponse();
                    vehicleResponse.assetId = assetId;
                    vehicleResponse.registrationStatus = registrationStatus;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // =  new Gson().fromJson(new Gson().toJson(response), RegisterVehicleResponse.class);
                callback.OnCompleted(vehicleResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.d("Error", new String(error.networkResponse.data));
                    Log.d("Error.Response", Objects.requireNonNull(error.getMessage()));
                    callback.onError(new Gson().fromJson(new String(error.networkResponse.data), ErrorResponse.class));
                } catch (Exception ex) {
                    try {
                        callback.onError(new Gson().fromJson(new String(error.networkResponse.data), ErrorResponse.class));
                    } catch (Exception ex2) {
                        callback.onError(ErrorResponse.UnknownError());
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String> obj = new HashMap<String, String>();
                obj.put("Authorization", "Bearer " + accessToken);
                return obj;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(postRequest);
    }

    public void AddReading(final AddReadingRequest readingRequest, final IServiceCallback callback, final String accessToken) {

        String url = baseUrl + "/api/services/SKAIoTServiceGroup/SKAIoTAssetService/AddReading";

        JSONObject obj = new JSONObject();
        try {
            obj.put("_id", readingRequest.vehicleNumber);
            obj.put("_valuetype", readingRequest.valueType);
            obj.put("_value", readingRequest.value);
            obj.put("_latitude", readingRequest.latitude);
            obj.put("_longitude", readingRequest.longitude);
        } catch (Exception e) {
            callback.onError(ErrorResponse.UnknownError());
        }

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                AddReadingResponse vehicleResponse = null;
                try {
                    String Status = response.getString("RequestStatus");

                    vehicleResponse = new AddReadingResponse();
                    vehicleResponse.Status = Status;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                callback.OnCompleted(vehicleResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.d("Error", new String(error.networkResponse.data));
                    Log.d("Error.Response", Objects.requireNonNull(error.getMessage()));
                    callback.onError(new Gson().fromJson(new String(error.networkResponse.data), ErrorResponse.class));
                } catch (Exception ex) {
                    try {
                        callback.onError(new Gson().fromJson(new String(error.networkResponse.data), ErrorResponse.class));
                    } catch (Exception ex2) {
                        callback.onError(ErrorResponse.UnknownError());
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String> obj = new HashMap<String, String>();
                obj.put("Authorization", "Bearer " + accessToken);
                return obj;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(postRequest);
    }

    public void GetFaultyVehicles(final IServiceCallback callback, final String accessToken) {

        String url = baseUrl + "/api/services/SKAIoTServiceGroup/SKAIoTAssetService/getFaultyVehicles";

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                FaultyVehiclesResponse vehiclesResponse = new FaultyVehiclesResponse();
                try {
                    JSONArray vehiclesArray = response.getJSONArray("Vehicles");
                    for(int counter = 0; counter < vehiclesArray.length(); counter++) {
                        FaultyVehicle vehicle = new FaultyVehicle();
                        JSONObject vehicleObject = vehiclesArray.getJSONObject(counter);
                        vehicle.activeWorkOrders = vehicleObject.getString("ActiveWorkOrders");
                        vehicle.phone = vehicleObject.getString("Phone");
                        vehicle.customerName = vehicleObject.getString("CustomerName");
                        vehicle.registrationNumber = vehicleObject.getString("RegistrationNumber");
                        vehiclesResponse.vehicles.add(vehicle);
                    }
                    callback.OnCompleted(vehiclesResponse);
                } catch (Exception e) {
                    callback.onError(ErrorResponse.UnknownError());
                    return;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.d("Error", new String(error.networkResponse.data));
                    Log.d("Error.Response", Objects.requireNonNull(error.getMessage()));
                    callback.onError(new Gson().fromJson(new String(error.networkResponse.data), ErrorResponse.class));
                } catch (Exception ex) {
                    try {
                        callback.onError(new Gson().fromJson(new String(error.networkResponse.data), ErrorResponse.class));
                    } catch (Exception ex2) {
                        callback.onError(ErrorResponse.UnknownError());
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String> obj = new HashMap<String, String>();
                obj.put("Authorization", "Bearer " + accessToken);
                return obj;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(postRequest);
    }

    public void CreateWorkOrder(final CreateWorkOrderRequest readingRequest, final IServiceCallback callback, final String accessToken) {

        String url = baseUrl + "/api/services/SKAIoTServiceGroup/SKAIoTAssetService/createWorkOrder";

        JSONObject obj = new JSONObject();
        try {
            obj.put("_registrationNumber", readingRequest.vehicleNumber);
        } catch (Exception e) {
            callback.onError(ErrorResponse.UnknownError());
        }

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                AddReadingResponse vehicleResponse = null;
                try {
                    String Status = response.getString("RequestStatus");

                    vehicleResponse = new AddReadingResponse();
                    vehicleResponse.Status = Status;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                callback.OnCompleted(vehicleResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.d("Error", new String(error.networkResponse.data));
                    Log.d("Error.Response", Objects.requireNonNull(error.getMessage()));
                    callback.onError(new Gson().fromJson(new String(error.networkResponse.data), ErrorResponse.class));
                } catch (Exception ex) {
                    try {
                        callback.onError(new Gson().fromJson(new String(error.networkResponse.data), ErrorResponse.class));
                    } catch (Exception ex2) {
                        callback.onError(ErrorResponse.UnknownError());
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String> obj = new HashMap<String, String>();
                obj.put("Authorization", "Bearer " + accessToken);
                return obj;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(postRequest);
    }
}
