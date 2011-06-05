package com.ttg.hellonode;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HelloNode extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ((Button)findViewById(R.id.buttonRefresh)).setOnClickListener(this);
    }

	public void onClick(View v) {
		getNodeReply();
	}

	private void getNodeReply() {
		HttpHelper http = new HttpHelper();
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("type", "json"));
        String reply;
        try {
			JSONObject result = http.getJSON("http://10.0.2.2:3000", params);
			reply = result.getString("time");
		} 
		catch(JSONException e) {
			reply = e.getMessage();
		}
		if(reply != null) {
			TextView t = (TextView) findViewById(R.id.textResult);
			t.setText(reply);
		}
	}
}