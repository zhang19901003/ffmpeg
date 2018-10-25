package com.adasplus.proadas.common.util.gps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;


public class AdasGps {
    private Context mContext;
    private float speed = -1;
    private LocationManager locManager;
    private LocationListener myLocListener;


    public AdasGps(Context context) {
        mContext = context;
        locManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        myLocListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
         //   return TODO;
          return;
        }
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, myLocListener);
    }



    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e("setting", "close");
            if (gpsListener != null)
                gpsListener.gpsCallBack(1);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e("setting", "open");
            if (gpsListener != null)
                gpsListener.gpsCallBack(0);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }

    public void release() {
        if (locManager != null) {
            locManager.removeUpdates(myLocListener);
        }
    }

    public interface GpsListener {
        void gpsCallBack(int flag);
    }

    private GpsListener gpsListener;

    public void setGpsListener(GpsListener gpsListener) {
        this.gpsListener = gpsListener;
    }

}