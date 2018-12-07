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
import android.preference.PreferenceManager
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.*

class CallActivity : AppCompatActivity(), OnMapReadyCallback{

    private lateinit var mMap: GoogleMap
    lateinit var prefs: SharedPreferences
    var origens = arrayOf("o1", "o2")
    var destinos = arrayOf("d1", "d2")
    var origem = ""
    var destino = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        //Spinners
        var sp_pickup = findViewById(R.id.spn_Pickup) as Spinner
        var sp_destiny = findViewById(R.id.spn_Destiny) as Spinner

        val adapterPickup = ArrayAdapter.createFromResource(this,R.array.pickup_options, android.R.layout.simple_spinner_item)
        val adapterOrigin = ArrayAdapter.createFromResource(this, R.array.destiny_options, android.R.layout.simple_spinner_item)

        adapterPickup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterOrigin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        sp_pickup.setAdapter(adapterPickup)
        sp_destiny.setAdapter(adapterOrigin)



        var bt_call = findViewById(R.id.btn_call) as Button
        bt_call.setOnClickListener({ callPorter(origem, destino)})

    }


     override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }

    fun callPorter(origem: String, destino: String){
        //função qdo clica no botão de chamar, dps de preencher as infos de viagem
        //não precisa verificar se tem informação faltando pq vai ter um valor default
        Log.d("belezinha", origem + "  >  " + destino)
//        if(origem == "" || destino == "") {
//            ifDialog()
//        } else {
//            elseDialog()
//        }

        startActivity(Intent(this, WaitingPorter::class.java))

    }

    fun ifDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setNegativeButton("if") { _, _ -> Toast.makeText(this, "if", Toast.LENGTH_LONG).show() }

        alertDialog.show()

    }
    fun elseDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setNegativeButton("else") { _, _ -> Toast.makeText(this, "else", Toast.LENGTH_LONG).show() }

        alertDialog.show()

    }
}
