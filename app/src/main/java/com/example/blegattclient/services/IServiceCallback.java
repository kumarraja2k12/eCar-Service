package com.example.blegattclient.services;

public interface IServiceCallback {
    public void OnCompleted(Object response);

    public void onError(Object error);
}
