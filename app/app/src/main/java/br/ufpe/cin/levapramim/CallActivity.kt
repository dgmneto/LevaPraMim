package br.ufpe.cin.levapramim

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.content.SharedPreferences
import android.os.Looper
import android.preference.PreferenceManager
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.*

class CallActivity : AppCompatActivity(), OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private lateinit var mMap: GoogleMap
    lateinit var prefs: SharedPreferences

    var origem = ""
    var destino = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        //Spinners
        val sp_pickup = findViewById(R.id.spn_Pickup) as Spinner
        val sp_destiny = findViewById(R.id.spn_Destiny) as Spinner

        val adapterPickup = ArrayAdapter.createFromResource(this,R.array.pickup_options, android.R.layout.simple_spinner_item)
        val adapterOrigin = ArrayAdapter.createFromResource(this, R.array.destiny_options, android.R.layout.simple_spinner_item)

        adapterPickup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterOrigin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        sp_pickup.setAdapter(adapterPickup)
        sp_destiny.setAdapter(adapterOrigin)


        val bt_call = findViewById(R.id.btn_call) as Button
        bt_call.setOnClickListener({ callPorter(sp_pickup, sp_destiny)})
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {  }

    override fun onNothingSelected(parent: AdapterView<*>?) { }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }

    fun callPorter(origem: Spinner, destino: Spinner){
        this.origem = origem.getSelectedItem().toString()
        this.destino = destino.getSelectedItem().toString()
        startActivity(Intent(this, WaitingPorter::class.java))
        //envia origem e destino pro firebase pra mostrar pros carregadores
    }

}
