package br.ufpe.cin.levapramim

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class PathActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_path)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        var bt = findViewById(R.id.finalizar) as Button
        bt.setOnClickListener({ finalDialog() })
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    fun finalDialog() {
        //esse dialog vai ser aberto quando clicar no botão de finalizar corrida.
        val valor = "15,00"
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Final da corrida") // O Titulo da notificação
        alertDialog.setMessage("O valor da corrida foi $valor?") // a mensagem ou alerta
        //depois do dinheiro recebido, volta pra tela inicial
        alertDialog.setPositiveButton("Sim") { _, _ ->
            startActivity( Intent(this, MapsActivity::class.java) )

        }
        alertDialog.setNegativeButton("Não recebido") { _, _ -> Toast.makeText(this, "Deu ruim", Toast.LENGTH_LONG).show() }

        alertDialog.show()
    }

}
