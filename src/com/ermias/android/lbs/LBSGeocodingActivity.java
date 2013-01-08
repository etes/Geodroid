package com.ermias.android.lbs;

import com.ermias.android.lbs.About;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;



public class LBSGeocodingActivity extends Activity {
    
	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
	private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
	
	protected LocationManager locationManager;
	
	protected Button getLocationButton;
	
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        getLocationButton = (Button) findViewById(R.id.retrieve_location_button);
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        locationManager.requestLocationUpdates(
        		LocationManager.GPS_PROVIDER,
        		MINIMUM_TIME_BETWEEN_UPDATES,
        		MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
        		new MyLocationListener()
        		);
   getLocationButton.setOnClickListener(new OnClickListener(){

		public void onClick(View v) {
			showCurrentLocation();
		}
    	
    });
   
    }
    
    protected void showCurrentLocation(){
    	
    	Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    	
    	if (location != null) {
    		String message = String.format(
    				"Current Location \n Longitude: %1$s \n Latitude: %2$s",
    				location.getLongitude(), location.getLatitude()
    		);
    		Toast.makeText(LBSGeocodingActivity.this, message,
    				Toast.LENGTH_LONG).show();
    	}
    }
    
    public boolean onCreateOptionsMenu(Menu menu){
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater=getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    	case R.id.about:
    		startActivity(new Intent(this, About.class));
    		return true;
    	}	
    	return false;
    }
    
    private class MyLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {
			String message = String.format(
					"New Location \n Longitude: %1$s \n Latitude: %2$s",
					location.getLongitude(), location.getLatitude()
			);
			Toast.makeText(LBSGeocodingActivity.this, message,
					Toast.LENGTH_LONG).show();
		}

		public void onProviderDisabled(String provider) {
			Toast.makeText(LBSGeocodingActivity.this,
					"Provider disabled by user. GPS turned off",
					Toast.LENGTH_LONG).show();
		}

		public void onProviderEnabled(String provider) {
			Toast.makeText(LBSGeocodingActivity.this,
					"Provider enabled by user, GPS turned on",
					Toast.LENGTH_LONG).show();	
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			Toast.makeText(LBSGeocodingActivity.this, "Provider status changed",
					Toast.LENGTH_LONG).show();
		}
    	
    }
}