package com.blc.endomingo.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.blc.endomingo.R;
import com.blc.endomingo.managers.ManagerRoutePoint;
import com.blc.endomingo.pojo.Route;
import com.blc.endomingo.pojo.RoutePoint;
import com.blc.endomingo.utils.Utils;
import com.blc.endomingo.views.main.ViewMain;
import com.blc.endomingo.views.routedetails.ViewRouteDetails;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Date;

/**
 * Created by Pablo on 30/12/2016.
 */

public class SportTrackerService extends Service implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    public static final String START = "com.blc.endomingo.sporttrackerservice.START";
    public static final String STOP = "com.blc.endomingo.sporttrackerservice.STOP";
    public static final String MAP = "com.blc.endomingo.sporttrackerservice.MAP";

    private static final int notifId = 1000;

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private Route route;
    private GoogleApiClient mGoogleApiClient;
    private Location location;
    private long startTime;
    private long currentTime;
    private float speed;
    private float totalDistance;
    private float weight = 75;

    public SportTrackerService(){
        Log.v("SportTrackerService", "SportTrackerService");
        startTime = System.currentTimeMillis();
        currentTime = System.currentTimeMillis();
        speed = 0;
        totalDistance = 0;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v("SportTrackerService", "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        Log.v("SportTrackerService", "onCreate");
        super.onCreate();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            System.out.println(mGoogleApiClient);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("SportTrackerService", "onStartCommand");
        if(intent.getAction().equals(START)) {
            if (intent.getExtras().getParcelable("route") != null) {
                route = intent.getExtras().getParcelable("route");
            } else {
                route = new Route(-1);
            }
            System.out.println("Received route: " + route);
            notificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

            Intent mapIntent = new Intent(this, SportTrackerService.class);
            mapIntent.setAction(MAP);
            PendingIntent piMap = PendingIntent.getService(this, 0, mapIntent, 0);
            NotificationCompat.Action mapAction = new NotificationCompat.Action.Builder(R.drawable.ic_my_location_black_24dp, "Map", piMap).build();

            Intent stopIntent = new Intent(this, SportTrackerService.class);
            stopIntent.setAction(STOP);
            PendingIntent piStop = PendingIntent.getService(this, 0, stopIntent, 0);
            NotificationCompat.Action stopAction = new NotificationCompat.Action.Builder(R.drawable.ic_stop_black_24dp, "Stop", piStop).build();

            notificationBuilder = new
                    NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_bike_white_24dp)
                    .setContentTitle("Route started " + new Date())
                    .setContentText(new Date().toString())
                    .setLights(Color.RED, 250, 2000)
                    .setVibrate(new long[]{0, 0})
                    .setOngoing(true)
                    .addAction(mapAction)
                    .addAction(stopAction);
                    //.setContentIntent(PendingIntent.getActivity(this, 0, locationIntent, 0));

            startForeground(notifId, notificationBuilder.build());
            mGoogleApiClient.connect();
        }else if(intent.getAction().equals(STOP)){
            stopForeground(true);
            stopSelf();
            Intent i = new Intent(this, ViewMain.class);
            startActivity(i);
        }else if(intent.getAction().equals(MAP)){
            Intent i = new Intent(this, ViewRouteDetails.class);
            Bundle b = new Bundle();
            b.putParcelable("route", route);
            i.putExtras(b);
            startActivity(i);
        }
        return START_STICKY;
    }

    private void updateNotification(String text, int color){
        notificationBuilder.setContentText(text);
        notificationBuilder.setLights(color, 250, 2000);
        notificationBuilder.setVibrate(new long[]{0, 0});
        notificationManager.notify(notifId, notificationBuilder.build());
    }

    @Override
    public void onDestroy() {
        Log.v("SportTrackerService", "onDestroy");
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onDestroy();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "NO FINE LOCATION", Toast.LENGTH_SHORT);
            System.out.println("no fine location");
            return;
        }

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "NO COARSED LOCATION", Toast.LENGTH_SHORT);
            System.out.println("no coarsed location");
            return;
        }

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        onLocationChanged( LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient));
    }

    @Override
    public void onConnectionSuspended(int i) {
        System.out.println("Connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        System.out.println("Connection failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        float distance = 0;
        if (this.location != null) {
            distance = this.location.distanceTo(location);
            long pastTime = System.currentTimeMillis() - currentTime;
            speed = calcSpeed(distance, (pastTime / 1000f));
        }
        this.location = location;
        currentTime = System.currentTimeMillis();
        totalDistance += distance;
        route.setDistance(totalDistance);
        route.setDuration(System.currentTimeMillis() - startTime);

        String text = String.format("Sp: %.2fKm/H, Dst: %.2fm, Cls: %.2f, Dur: %s", speed, totalDistance,route.getBurnedCalories(weight), Utils.formatTime(route.getDuration()));
        int color = Color.BLACK;
        if(speed >= 1 && speed <= 5){
            color = Color.BLUE;
        }else if(speed <= 10){
            color = Color.CYAN;
        }else if(speed <= 15){
            color = Color.GREEN;
        }else if(speed <= 20){
            color = Color.YELLOW;
        }else if(speed > 20){
            color = Color.RED;
        }
        updateNotification(text, color);
        RoutePoint rp = new RoutePoint(this.location, speed);
        route.addRoutePoint(rp);
        rp.setRouteId(route.getId());
        ManagerRoutePoint mrp = new ManagerRoutePoint(this);
        mrp.insert(rp);
    }

    private float calcSpeed(float distance, float time){
        return ((distance / time) * 3600f) / 1000f;
    }
}
