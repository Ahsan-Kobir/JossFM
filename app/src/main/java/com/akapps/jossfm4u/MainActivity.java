package com.akapps.jossfm4u;

import android.os.*;
import android.util.Log;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

	private RequestNetwork net;
	private RequestNetwork.RequestListener _net_request_listener;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		new Timer().schedule(new TimerTask(){
			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						//startActivity(new Intent(MainActivity.this, HomeActivity.class));
						net.startRequestNetwork(RequestNetworkController.POST, "http://clients3.google.com/generate_204", "", _net_request_listener);
					}
				});
			}
		}, 2000);
	}

	private void initialize(Bundle _savedInstanceState) {

		net = new RequestNetwork(this);

		_net_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2) {
				Log.d("Test", _param2);
				startActivity(new Intent(MainActivity.this, HomeActivity.class));
				finish();
			}

			@Override
			public void onErrorResponse(String _param1, String _param2) {
				Log.d("Test", _param2);
				Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
				}
		};
	}

}