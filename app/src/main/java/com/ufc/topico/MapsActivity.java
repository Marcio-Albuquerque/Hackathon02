package com.ufc.topico;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    // Ordem: Sobral, Ubajara, Jeriocoacoara, Fortaleza, Juazeiro do Norte

    double x[] = {-3.685385, -3.865621, -2.796275, -3.721403, -7.227156};
    double y[] = {-40.344244, -40.918180, -40.514460, -38.478586, -39.345119};
    private String local1, local2;
    private int i;
    LatLng sbl; // Origem
    LatLng ubj; // Destino

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();             // Pegar as informações da primeira activity
        local1 = intent.getStringExtra("Citie1"); // set os locais da activity anterior
        local2 = intent.getStringExtra("Citie2");

        //Definição do primeiro local Cidade de Origem
        switch (local1) {
            case "Sobral":
                sbl = new LatLng(x[0], y[0]);
                break;
            case "Ubajara":
                sbl = new LatLng(x[1], y[1]);
                break;
            case "Jericoacoara":
                sbl = new LatLng(x[2], y[2]);
                break;
            case "Fortaleza":
                sbl = new LatLng(x[3], y[3]);;
                break;
            case "Juazeiro do Norte":
                sbl = new LatLng(x[4], y[4]);
                break;
        }

        //Defini sengundo Local Cidade de Destino
        switch (local2) {
            case "Sobral":
                ubj = new LatLng(x[0], y[0]);
                break;
            case "Ubajara":
                ubj = new LatLng(x[1], y[1]);
                break;
            case "Jericoacoara":
                ubj = new LatLng(x[2], y[2]);
                break;
            case "Fortaleza":
                ubj = new LatLng(x[3], y[3]);;
                break;
            case "Juazeiro do Norte":
                ubj = new LatLng(x[4], y[4]);
                break;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-3.721403, -38.478586), 6));
        // Linhas de polígono são úteis para marcar caminhos e rotas no mapa.
        googleMap.addPolyline(new PolylineOptions().geodesic(true)
                .add(sbl)
                .add(ubj)
        );

        googleMap.addMarker(new MarkerOptions().title(local1)
                .snippet("Cidade de Origem").position(sbl));
        googleMap.addMarker(new MarkerOptions().title(local2)
                .snippet("Cidade de Destino").position(ubj));
    }


}