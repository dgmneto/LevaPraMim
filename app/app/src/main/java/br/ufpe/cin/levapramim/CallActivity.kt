package br.ufpe.cin.levapramim

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.widget.ArrayAdapter
import android.widget.Button


class CallActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

//Spinner da origem
        var sp_pickup = findViewById(R.id.spn_Pickup) as Spinner
        val adapterPickup = ArrayAdapter.createFromResource(
            this,
            R.array.pickup_options, android.R.layout.simple_spinner_item
        )
        adapterPickup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_pickup.setAdapter(adapterPickup)

//Spinner do destino
        var sp_destiny = findViewById(R.id.spn_Destiny) as Spinner
        val adapterOrigin = ArrayAdapter.createFromResource(
            this,
            R.array.destiny_options, android.R.layout.simple_spinner_item
        )
        adapterOrigin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_destiny.setAdapter(adapterOrigin)

        var bt_call = findViewById(R.id.btn_call) as Button

    }

     override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }

    fun callPorter(){
        //função qdo clica no botão de chamar, dps de preencher as infos de viagem
        //precisa verificar se as informações estão completas
        //caso não esteja, não pode seguir, mostra a informação de que tá faltando algo
        //quando tudo tá completo, renderiza a tela de espera de carregador
    }

    fun hasAllInfos(){
        //função que verifica se todas as informações tão completas
    }
}
