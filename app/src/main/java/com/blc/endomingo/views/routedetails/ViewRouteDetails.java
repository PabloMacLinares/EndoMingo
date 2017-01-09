package com.blc.endomingo.views.routedetails;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.blc.endomingo.R;
import com.blc.endomingo.custom_views.Graph;
import com.blc.endomingo.pojo.Route;
import com.blc.endomingo.pojo.RoutePoint;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pablo on 30/12/2016.
 */

public class ViewRouteDetails extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private Route route;
    private Graph graphAltitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (savedInstanceState != null) {
            route = savedInstanceState.getParcelable("route");
            System.out.println("Saved: " + savedInstanceState.toString());
        } else {
            Bundle b = getIntent().getExtras();
            if(b != null ) {
                route = b.getParcelable("route");
                System.out.println("Bundle: " + b.toString());
            }else{
                System.out.println("bundle null");
            }
        }

        graphAltitude = (Graph) findViewById(R.id.graphAltitude);
        List<Double> points = new ArrayList<>();
        for (RoutePoint rp : route.getRoutePoints()) {
            points.add(new Double(rp.getAltitude()));
        }
        graphAltitude.setPoints(points);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        drawRoute();
    }

    private void drawRoute(){
        List<RoutePoint> rps = route.getRoutePoints();
        PolylineOptions options = new PolylineOptions();
        options.color(Color.BLUE );
        options.width(7);
        options.visible( true );
        for (int i = 0; i < rps.size(); i++) {
            LatLng pos = new LatLng(rps.get(i).getLatitude(), rps.get(i).getLongitude());
            options.add(pos);
            if(i == rps.size() - 1){
                MarkerOptions mo = new MarkerOptions();
                mo.title("Current position");
                mo.position(pos);
                gMap.addMarker(mo);
                gMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
                gMap.animateCamera(CameraUpdateFactory.zoomTo(19));
            }else if(i % 5 == 0){
                MarkerOptions mo = new MarkerOptions();
                mo.title("Point " + i);
                mo.position(pos);
                gMap.addMarker(mo);
                gMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
                gMap.animateCamera(CameraUpdateFactory.zoomTo(19));
            }
        }
        gMap.addPolyline(options);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("route", route);
    }
}
