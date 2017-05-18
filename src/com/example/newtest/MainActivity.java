package com.example.newtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); 
		
		final Button bu = (Button) findViewById(R.id.convert_button);
		bu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	 convertMethode();
            }
        });

		final Button bu2 = (Button) findViewById(R.id.reset_button);
		bu2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	 resetMethode();
            }
        });
		
		final Button bu3 = (Button) findViewById(R.id.search_button);
		bu3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	searchMethode();
            }
        });
		final Button bu4 = (Button) findViewById(R.id.update_button);
		bu4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	updateCurrencyMethode();
            }
        }); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	public void convertMethode(){ 
		EditText current = (EditText) findViewById(R.id.edit_euros);
		EditText taux = (EditText) findViewById(R.id.edit_taux);
		if (!current.getText().toString().equals("") && !taux.getText().toString().equals("")){
			EditText objects = (EditText) findViewById(R.id.edit_dolars);
			Float int_cr = Float.parseFloat(current.getText().toString());
			Float int_tx = Float.parseFloat(taux.getText().toString());
			
			objects.setText(""+(int_cr*int_tx));
			
		}else {return;}
	}
	public void resetMethode(){ 
		EditText current = (EditText) findViewById(R.id.edit_euros);
		EditText taux = (EditText) findViewById(R.id.edit_taux); 
		EditText objects = (EditText) findViewById(R.id.edit_dolars);
		current.setText(""); 
		taux.setText(""); 
		objects.setText("");
		
	}

	public void searchMethode() { 
		Uri uri = Uri.parse("https://www.google.fr/search?q=1+euro+in+usd");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
	
	public void updateCurrencyMethode(){ 
		String url = "https://openexchangerates.org/api/latest.json?app_id=95dba99f7df3466db787b404e7501b81";
 
		class MyTask extends AsyncTask<String,Void,JSONObject>{

			@Override
			protected JSONObject doInBackground(String... params) {
				// TODO Auto-generated method stub
				Log.d(" MyTask ()", " doInBackground" );
				JSONObject json = loadJSONObject(params[0]);
				return json;
			}
			private JSONObject loadJSONObject(String url){
				Log.d(" MyTask ()", " loadJSONObject" );
				HttpClient httpclient = new DefaultHttpClient();
				HttpGet httpget= new HttpGet(url);

				HttpResponse response=null;
				String server_response=null;
				try {
					Log.d(" MyTask ()", " try response = httpclient.execute(httpget)" );
					response = httpclient.execute(httpget);
				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Log.d(" MyTask ()", "  passed" );
				if(response.getStatusLine().getStatusCode()==200){
					try {
						server_response= EntityUtils.toString(response.getEntity());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   //Log.i("Server response", server_response );
				} else {
				   //Log.i("Server response", "Failed to get server response" );
				}
				
				JSONObject json=null;
				try {
					json = new JSONObject(server_response);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return json;
			}
			@Override
			protected void onPostExecute(JSONObject json) { 
				Log.d(" MyTask ()", " onPostExecute" ); 

				//Log.d(" MyTask ()", " json =="+json ); 
				
				double usd_euro_crr=0;
				try { 
					JSONObject rates = json.getJSONObject("rates");
					usd_euro_crr= rates.getDouble("EUR");
					Log.d(" MyTask ()", " usd_euro_crr= json.getString(EUR);= "+usd_euro_crr);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				if (usd_euro_crr!=0){
					double euro_usd_crr = (double)(1.0/ usd_euro_crr);
					EditText taux = (EditText) findViewById(R.id.edit_taux); 
					taux.setText(""+euro_usd_crr);
				}
			}
			
		}
		Log.d(" MyTask ()", " start" );
		 new MyTask().execute(url);
	 
		
	}
	

}
