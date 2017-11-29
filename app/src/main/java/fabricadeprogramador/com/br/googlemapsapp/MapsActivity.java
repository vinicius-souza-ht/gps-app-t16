package fabricadeprogramador.com.br.googlemapsapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int READ_GPS_REQUEST_CODE = 123;
    LatLng ht = new LatLng(-20.50552093, -54.59747225);

    Marker marker;
    List<LatLng> locais = new ArrayList<>();

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Localizador localizador = new Localizador(MapsActivity.this);
        LatLng end1 = localizador.buscarCoordenada("Av. Noroeste 10624, Campo Grande, MS");
        LatLng end2 = localizador.buscarCoordenada("João Rosa Pires 1001, Campo Grande, MS");
        LatLng end3 = localizador.buscarCoordenada("Av. Afonso Pena 1000, Campo Grande, MS");

        locais.add(end1);
        locais.add(end2);
        locais.add(end3);


        if (Build.VERSION.SDK_INT >= 23) {
            if (getApplicationContext().checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        READ_GPS_REQUEST_CODE);
            }
        }


    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ht));


        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            Marker marker;
            @Override
            public void onMyLocationChange(Location location) {
                LatLng local = new LatLng(location.getLatitude(), location.getLongitude());
                if(marker != null){
                    marker.remove();
                }
                marker = googleMap.addMarker(new MarkerOptions().position(local).title("Minha Localização").draggable(true));

            }
        });

        for(LatLng local : locais){
            if(local != null) {
                googleMap.addMarker(new MarkerOptions().position(local));
            }
        }
    }
}
