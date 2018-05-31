package kz.terah.projectfarakzm;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);


        LatLng farakz = new LatLng(51.1821511, 71.3841919);
        mMap.addMarker(new MarkerOptions().position(farakz).title("Фара.kz  Астыкжан"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(farakz, 17.0f));

       final Button buttonmap = findViewById(R.id.button3);
       buttonmap.setOnClickListener(v -> {
           Uri gmmIntentUri = Uri.parse("google.navigation:q=51.1821511,71.3841919");
           Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
           mapIntent.setPackage("com.google.android.apps.maps");
           startActivity(mapIntent);
       });

    }
}
