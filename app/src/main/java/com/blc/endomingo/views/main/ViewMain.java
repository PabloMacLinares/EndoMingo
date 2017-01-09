package com.blc.endomingo.views.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.blc.endomingo.R;
import com.blc.endomingo.adapters.AdapterRoute;
import com.blc.endomingo.contracts.ContractMain;
import com.blc.endomingo.managers.ManagerRoute;
import com.blc.endomingo.pojo.Route;
import com.blc.endomingo.services.SportTrackerService;

import java.util.List;

public class ViewMain extends AppCompatActivity
        implements ContractMain.View , NavigationView.OnNavigationItemSelectedListener{

    private ContractMain.Presenter presenter;
    private ManagerRoute mRoute;

    private FloatingActionButton fabStart;
    private RecyclerView rvRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        presenter = new PresenterMain(this);

        fabStart = (FloatingActionButton) findViewById(R.id.fabStart);

        mRoute = new ManagerRoute(this);

        rvRoute = (RecyclerView) findViewById(R.id.rvRoute);
        rvRoute.setLayoutManager(new LinearLayoutManager(this));
        rvRoute.setAdapter(new AdapterRoute(null));

        initEvents();
    }

    private void initEvents(){
        fabStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                presenter.onStartSportTracker();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showSportTracker(Route route) {
        Log.v("ViewMain", "Starting sport tracker with route: " + route.getId());
        Intent intent = new Intent(this, SportTrackerService.class);
        intent.setAction(SportTrackerService.START);
        Bundle bundle = new Bundle();
        bundle.putParcelable("route", route);
        intent.putExtras(bundle);
        startService(intent);
        finish();
    }

    @Override
    public void showRoutes(List<Route> routes) {
        ((AdapterRoute)rvRoute.getAdapter()).setRoutes(routes);
    }
}
