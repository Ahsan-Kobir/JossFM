package com.akapps.jossfm4u;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.models.ClientRoleOptions;

public class HomeActivity extends AppCompatActivity {


	private String appId = "a322bad7ba27413a89efc583babc9977";

	public RtcEngine mRtcEngine;

	private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
		@Override
		public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
			super.onJoinChannelSuccess(channel, uid, elapsed);
			isCon = true;
			connected();

		}
	};

	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String img1 = "";
	private String img2 = "";
	private String img3 = "";
	private String img4 = "";
	
	private ArrayList<HashMap<String, Object>> lst = new ArrayList<>();

	private boolean isCon;
	
	private ScrollView vscroll1;
	private LinearLayout linearm;
	private LinearLayout linear3;
	private LinearLayout linear1;
	private LinearLayout linear4;
	private LinearLayout menu_layout;
	private ImageView imageview1;
	private LinearLayout linear6;
	private LinearLayout linear8;
	private ImageView stream_status_icon;
	private LinearLayout linear7;
	private ImageView play_pause;
	private TextView textview2;
	private TextView status;
	private TextView program;
	private TextView updates;
	private LinearLayout linear9;
	private LinearLayout linear10;
	private LinearLayout linear11;
	private ImageView upcoming;
	private TextView textview4;
	private ImageView query;
	private TextView textview5;
	private ImageView about;
	private TextView textview6;
	
	private RequestNetwork net;
	private RequestNetwork.RequestListener _net_request_listener;
	private Intent in = new Intent();
	private DatabaseReference db = _firebase.getReference("datas");
	private ChildEventListener _db_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.layout_home);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {



		status = (TextView) findViewById(R.id.textView3);
		program = (TextView) findViewById(R.id.program);
		updates = (TextView) findViewById(R.id.updates);



		upcoming = (ImageView) findViewById(R.id.eventsIv);
		query = (ImageView) findViewById(R.id.queryIv);
		about = (ImageView) findViewById(R.id.aboutIv);
		play_pause = findViewById(R.id.play_pause);
		linear1 = findViewById(R.id.linear1);

		net = new RequestNetwork(this);

		play_pause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(isCon){
					status.setText("Disconnected");
					mRtcEngine.leaveChannel();
					play_pause.setImageResource(R.drawable.ic_play_arrow_black);
					isCon = false;
				} else {
					mRtcEngine.joinChannel(null, "ak", "", 0);
					play_pause.setEnabled(false);

				}
			}
		});
		
		upcoming.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				in.setClass(getApplicationContext(), EventsActivity.class);
				startActivity(in);
			}
		});
		
		query.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				in.setClass(getApplicationContext(), QueryActivity.class);
				startActivity(in);
			}
		});
		
		about.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				in.setClass(getApplicationContext(), AboutActivity.class);
				startActivity(in);
			}
		});
		
		_net_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _response = _param2;
				
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_db_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		db.addChildEventListener(_db_child_listener);
	}
	private void initializeLogic() {
		_startSlide();
		db.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				lst = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						lst.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				if (lst.get((int)0).get("updates").toString().equals("hidden")) {
					updates.setVisibility(View.GONE);
				}
				else {
					updates.setText(lst.get((int)0).get("updates").toString());
				}
				program.setText(lst.get((int)0).get("program").toString());
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});

		startRadio();
	}

	private void startRadio() {

		try {
			mRtcEngine = RtcEngine.create(getBaseContext(), appId, mRtcEventHandler);
		} catch (Exception e) {
			throw new RuntimeException("Check the error.");
		}


		mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING);
			// Set the client role as AUDIENCE and the latency level as low latency.
			ClientRoleOptions clientRoleOptions = new ClientRoleOptions();
			clientRoleOptions.audienceLatencyLevel = Constants.AUDIENCE_LATENCY_LEVEL_LOW_LATENCY;
			mRtcEngine.setClientRole(Constants.CLIENT_ROLE_AUDIENCE, clientRoleOptions);

			// Join the channel with a token.
			mRtcEngine.joinChannel(null, "ak", "", 0);
		}

	private void connected() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				status.setText("Connected");
				play_pause.setImageResource(R.drawable.ic_stop_black);
				play_pause.setEnabled(true);
			}
		});

	}


	private void _startSlide () {

		img1 = "https://icms-image.slatic.net/images/ims-web/b8bc7369-14cc-41dd-84ea-f53e2855b2d4.jpg_1200x1200.jpg";
		img2 = "https://icms-image.slatic.net/images/ims-web/095c2f32-0f38-4005-bf8c-cf14a2295f21.jpg";
		img3 = "https://icms-image.slatic.net/images/ims-web/1e55fbbc-55fb-4dbb-a13f-07cc95b5fe3a.jpg";
		img4 = "https://icms-image.slatic.net/images/ims-web/ec33d2c1-449e-497a-bb24-38ae1db60658.jpg";
		
		View v1 = (View) getLayoutInflater().inflate(R.layout.bnr1, null);
		
		final ImageView image1 = (ImageView) v1.findViewById(R.id.imageview1);
		
		Glide.with(getApplicationContext()).load(Uri.parse(img1)).into(image1);
		
		
		View v2 = (View) getLayoutInflater().inflate(R.layout.bnr2, null);
		
		final ImageView image2 = (ImageView) v2.findViewById(R.id.imageview1);
		
		Glide.with(getApplicationContext()).load(Uri.parse(img2)).into(image2);
		
		
		View v3 = (View) getLayoutInflater().inflate(R.layout.bnr3, null);
		
		final ImageView image3 = (ImageView) v3.findViewById(R.id.imageview1);
		
		Glide.with(getApplicationContext()).load(Uri.parse(img3)).into(image3);
		
		
		View v4 = (View) getLayoutInflater().inflate(R.layout.bnr4, null);
		
		final ImageView image4 = (ImageView) v4.findViewById(R.id.imageview1);
		
		Glide.with(getApplicationContext()).load(Uri.parse(img4)).into(image4);
		
		
		ViewGroup.LayoutParams lp =new ViewGroup.LayoutParams(getDisplayWidthPixels(), LinearLayout.LayoutParams.WRAP_CONTENT);
		ViewFlipper vfl = new ViewFlipper(this);
		
		vfl.addView(v1, 0, lp);
		vfl.addView(v2, 1, lp);
		vfl.addView(v3, 2, lp);
		vfl.addView(v4, 3, lp);
		
		
		vfl.setAutoStart(true);
		linear1.addView(vfl);
	}
	public static Animation inFromRight(long ms){
		Animation right = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, +1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
		
		right.setDuration(ms);
		return right;
	}
	
	
	public static Animation outToLeft(long ms) {
		Animation left = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);

		left.setDuration(ms);
		return left;
	}

	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
}
