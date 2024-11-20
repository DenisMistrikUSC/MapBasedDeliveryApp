package com.example.uscdoordrinkteam65;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
//import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback, PermissionsListener, MapboxMap.OnMarkerClickListener {

    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    private MapView mapView;
    private ArrayList<Location> locations = new ArrayList<>();
    private Point originalLoc;
    private Point selectedLoc;
    private Button nbutton;
    private Button viewbutton;
    private Button historybutton;
    private Marker currMarker;
    private Button cartbutton;

    private NavigationMapRoute navigationMapRoute;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.logEvent("customerLoggedIn", bundle);

        Mapbox.getInstance(this, getString(R.string.access_token));

        setContentView(R.layout.activity_main);
//        GlobalVars.LoadTestUser();
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        nbutton = findViewById(R.id.buttonnav);
        viewbutton = findViewById(R.id.buttonopen);
        historybutton = findViewById(R.id.buttonHistory);
        cartbutton = findViewById(R.id.buttoncart);
        cartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedLoc != null) getRoute(originalLoc,selectedLoc, currMarker);
                Intent i = new Intent(MainActivity.this, CartActivity.class);
                startActivity(i);
            }
        });
        nbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRoute(originalLoc,selectedLoc, currMarker);
            }
        });
        viewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRoute(originalLoc,selectedLoc, currMarker);
                Intent i = new Intent(MainActivity.this, MenuActivity.class);
                Location lc = null;
                for(Location l: locations){
                    if(l.getName().equals(currMarker.getTitle())){
                        lc = l;
                    }
                }
                GlobalVars.selectedLocation = lc;
                startActivity(i);
            }
        });
        historybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        getStores();
        MainActivity.this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.OUTDOORS,
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);
                    }
                });


        mapboxMap.setOnMarkerClickListener(this);
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

            // Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
            android.location.Location lopc = locationComponent.getLastKnownLocation();
            originalLoc = Point.fromLngLat(lopc.getLongitude(),lopc.getLatitude());
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        //Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            // Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }
    public void getRoute(@NonNull Point origin, @NonNull Point destination,@NonNull Marker selected){
        NavigationRoute.builder(this).accessToken(Mapbox.getAccessToken())
                .origin(origin).destination(destination).build().getRoute(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                if(response.body() == null){
                    return;
                } else if (response.body().routes().size() == 0){
                    return;
                }
                DirectionsRoute currentRoute = response.body().routes().get(0);
                if(navigationMapRoute != null){
                    navigationMapRoute.removeRoute();
                } else{
                    navigationMapRoute = new NavigationMapRoute(null,mapView,mapboxMap);
                }
                navigationMapRoute.addRoute(currentRoute);
                int duration = (int)Math.round(currentRoute.duration()/60);
                int durationBike = (int)Math.round(duration * 1.5);
                int durationWalk = (int)Math.round(duration * 3.5);
                GlobalVars.currentEta = duration;
                selected.setSnippet("ETA with car " + duration + " minutes" + "\n"
                +"ETA with bike " + durationBike + " minutes"+ "\nETA with walking " + durationWalk + " minutes");
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {

            }
        });

    }
    @Override
    @SuppressWarnings( {"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    public void getStores(){
        FirebaseApp.initializeApp(this);
       // FirebaseAuth mAuth = FirebaseAuth.getInstance();
       // mAuth.signInAnonymously();
        DatabaseReference reference = FirebaseDatabase.getInstance("https://usc-doordrink-default-rtdb.firebaseio.com").getReference().child("Stores");
        reference.addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String storeName = GlobalVars.currSeller.getStoreName();
               // System.out.println(snapshot.getChildrenCount());
                for(DataSnapshot stores: snapshot.getChildren()){
//                    double lat = (double) stores.child("latitude").getValue();
//                    double lng = (double) stores.child("longitude").getValue();
//                    ArrayList<Drink> drinks = new ArrayList<>();
//                    for(DataSnapshot dddd: stores.child("Menu").getChildren()){
//                        Drink newDrink = dddd.getValue(Drink.class);
//                        drinks.add(newDrink);
//                    }
//                    Location l = new Location((String)stores.getKey(), lat,lng,drinks);
//                    String storeName = (String) stores.getKey();
                    Location s = FirebaseFuncs.getStore(stores);
//                    double lat = (double) snapshot.child(storeName).child("latitude").getValue();
//                    double lng = (double) snapshot.child(storeName).child("longitude").getValue();
////                    String name = (String) snapshot.child(storeName).child("name").getValue();
//                    List<Drink> drinks = new ArrayList<>();
//                    for (DataSnapshot drinkSnap : snapshot.child(storeName).child("menu").getChildren()) {
//                        Drink dr = (Drink) drinkSnap.getValue(Drink.class);
//                        drinks.add(dr);
//                    }
//                    Location l = new Location(storeName, lat, lng, drinks);
                    locations.add(s);

                }
                for(Location l: locations){
                    MarkerOptions options = new MarkerOptions();
                    options.title(l.getName());
                    options.position(new LatLng(l.getLatitude(),l.getLongitude()));
                    mapboxMap.addMarker(options);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        nbutton.setEnabled(true);
        viewbutton.setEnabled(true);
        LatLng llt = marker.getPosition();
        selectedLoc = Point.fromLngLat(llt.getLongitude(),llt.getLatitude());
        currMarker = marker;
        return false;
    }
}
