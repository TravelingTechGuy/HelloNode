package com.ttg.hellonode;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HelloNode extends Activity implements OnClickListener {
	private TextView resultText;
	private String url = "http://10.0.2.2:3000";
	private ProgressDialog dialog;
	private Handler handler;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ((Button)findViewById(R.id.buttonRefresh)).setOnClickListener(this);
        resultText = (TextView) findViewById(R.id.textResult);
        getNodeReply();
    }

	public void onClick(View v) {
		getNodeReply();
	}

	private void getNodeReply() {
		dialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
		handler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	        	dialog.dismiss();
	            resultText.setText(msg.obj.toString());
	        }
		};
		new Thread() {
			public void run() {
				Message msg = new Message();
				msg.obj = makeHttpRequest();
				SystemClock.sleep(500);
				handler.sendMessage(msg);
			}
		}.start();
	}
	
	private String makeHttpRequest() {
		HttpHelper http = new HttpHelper();
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("type", "json"));
        String reply = null;
        try {
			JSONObject result = http.getJSON(this.url , params);
			reply = result.getString("time");
		} 
		catch(JSONException e) {
			e.printStackTrace();
		}
		if(reply == null) {
			reply = "Error occured";
		}
		return reply;
	}
}