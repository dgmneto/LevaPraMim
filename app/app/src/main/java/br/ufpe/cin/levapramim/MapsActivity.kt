package br.ufpe.cin.levapramim

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        var bt = findViewById(R.id.receber) as Button
        bt.setOnClickListener({ receiveDialog() })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    fun receiveDialog() {
        //esse dialog vai abrir qdo receber a solicitação do usuário. a requisição vai conter o de/para para ser printado
        val de = "lugar A"
        val para = "lugar B"
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Nova corrida") // O Titulo da notificação
        alertDialog.setMessage("deseja aceitar a corrida de $de para $para?") // a mensagem ou alerta

        //quando o botão de aceitar for clicado, vai redirecionar pra tela dizendo a localização a qual ele tem que se dirigir.
        alertDialog.setPositiveButton("Sim") { _, _ ->
                startActivity( Intent(this, PickupActivity::class.java) )

        }
        alertDialog.setNegativeButton("Não") { _, _ -> Toast.makeText(this, "Não", Toast.LENGTH_LONG).show() }

        alertDialog.show()

    }

}
