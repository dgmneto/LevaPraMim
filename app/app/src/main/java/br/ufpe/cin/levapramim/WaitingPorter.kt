package br.ufpe.cin.levapramim

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_waiting_porter.*


class WaitingPorter : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var chegou = "o carinha chegou"
    var procurando = "procurando o carinha"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting_porter)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //renderiza o texto de que tá aguardando algum carregador disponível
        val textViewToChange = findViewById(R.id.status) as TextView
        textViewToChange.text = procurando

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(procurando, status.text.toString())
        super.onSaveInstanceState(outState)
    }

    fun changeStatus() {
        val textViewToChange = findViewById(R.id.status) as TextView
        textViewToChange.text = chegou
    }
}