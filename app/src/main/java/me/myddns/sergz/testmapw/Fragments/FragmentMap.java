package me.myddns.sergz.testmapw.Fragments;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.TextView;


import me.myddns.sergz.testmapw.DataStore;
import me.myddns.sergz.testmapw.MainActivity;
import me.myddns.sergz.testmapw.R;

import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMap.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMap extends Fragment implements OnMapReadyCallback{

    public GoogleMap mMap;
    public float curZoom = 10;
    public LatLng currLoc;
    public DataStore dataStore;

    ////
    private boolean isLocated  = false;
    Button btnLocateMe, btnZoomIn, btnZoomOut;
    TextView teLocateStatus;

    private OnFragmentInteractionListener mListener;

    public FragmentMap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMap.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMap newInstance(String param1, String param2) {
        FragmentMap fragment = new FragmentMap();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        //Setting button
        btnLocateMe = (Button)view.findViewById(R.id.btnLocateMe);
        btnLocateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateMe();
            }
        });


        btnZoomIn = (Button)view.findViewById(R.id.btnZoomIn);
        btnZoomIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mMap.getMaxZoomLevel() >= +1) {
                    curZoom++;
                    addMarker(currLoc, curZoom);
                    if (curZoom >= 15) {
                        mMap.setBuildingsEnabled(true);
                    }
                }
            }
        });

        btnZoomIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mMap.getMinZoomLevel() <=curZoom - 1){
                    curZoom--;
                    addMarker(currLoc, curZoom);
                    if (curZoom < 15)
                    {
                        mMap.setBuildingsEnabled(false);
                    }
                }
            }
        });

        teLocateStatus = (TextView)view.findViewById(R.id.teLocateStatus);

        //++draw the map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        //--draw the map

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    ///MAPS NAVIGATOR FUNCTIONS
    public void addMarker(LatLng latLng, float curZoom) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).title("Current location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,curZoom));
        //CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(latLng,1));
        //mMap.animateCamera();
    }

    public void GetLocation() {
        teLocateStatus.setText(getString(R.string.status_locinprogress));
        /* Acquire a reference to the system Location Manager */
        LocationManager locationManager = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                LatLng newLoc = new LatLng(location.getLatitude(), location.getLongitude());
                if (!currLoc.equals(newLoc)) {
                    addMarker(currLoc, curZoom);
                    teLocateStatus.setText(getString(R.string.status_locdone));
                    Log.i("Fragment map", "updated coordinates:   " + currLoc.toString());
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

// Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    /////////--MAPS and NAVIGATION/////////
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    ///Caling to getLocation
    public void locateMe(){
        GetLocation();
    }
}
