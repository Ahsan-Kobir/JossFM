package com.akapps.jossfm4u;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class RequestNetwork {
private HashMap<String, Object> params = new HashMap<>();
private HashMap<String, Object> headers = new HashMap<>();

private AppCompatActivity activity;

private int requestType = 0;

public RequestNetwork(AppCompatActivity activity) {
this.activity = activity;
}

public void setHeaders(HashMap<String, Object> headers) {
this.headers = headers;
}

public void setParams(HashMap<String, Object> params, int requestType) {
this.params = params;
this.requestType = requestType;
}

public HashMap<String, Object> getParams() {
return params;
}

public HashMap<String, Object> getHeaders() {
return headers;
}

public AppCompatActivity getActivity() {
return activity;
}

public int getRequestType() {
return requestType;
}

public void startRequestNetwork(String method, String url, String tag, RequestListener requestListener) {
RequestNetworkController.getInstance().execute(this, method, url, tag, requestListener);
}

public interface RequestListener {
public void onResponse(String tag, String response);
public void onErrorResponse(String tag, String message);
}
}
