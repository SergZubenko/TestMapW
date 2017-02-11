package me.myddns.sergz.testmapw;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import me.myddns.sergz.testmapw.Fragments.FragmentMap;
import me.myddns.sergz.testmapw.Fragments.FragmentWelcome;


public class MainActivity extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener {
    FragmentMap fMap;
    FragmentWelcome fWelcome;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fMap = new FragmentMap();
        fWelcome = new FragmentWelcome();
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            fTrans.replace(R.id.content_main, fMap);

        } else if (id == R.id.nav_gallery) {
            fTrans.replace(R.id.content_main, fWelcome);

        } else if (id == R.id.nav_slideshow) {
            //   if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //          return true;
            //}
            //  mMap.setMyLocationEnabled(!mMap.isMyLocationEnabled());

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            if (fMap.mMap.getMaxZoomLevel() >=fMap.curZoom + 1) {
                fMap.curZoom++;
                fMap.addMarker(fMap.currLoc, fMap.curZoom);
                if (fMap.curZoom >= 15)
                {
                    fMap.mMap.setBuildingsEnabled(true);
                }
            }

        } else if (id == R.id.nav_send) {
            if (fMap.mMap.getMinZoomLevel() <=fMap.curZoom - 1){
                fMap.curZoom--;
                fMap.addMarker(fMap.currLoc, fMap.curZoom);
                if (fMap.curZoom < 15)
                {
                    fMap.mMap.setBuildingsEnabled(false);
                }
            }
        }
        fTrans.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





}
