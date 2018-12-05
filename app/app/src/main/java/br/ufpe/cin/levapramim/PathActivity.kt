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
        bt.setOnClickListener({ porterDestiny() })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }


    fun porterPickup() {
        //essa função é responsável pelo que acontece quando o carregador chega na na origem
        //ela vai renderizar o botão de iniciar a corrida ao invés do botão de "cheguei"
    }

    fun porterGoTo() {
        //essa função é responsável pelo que acontece quando o carregador inicia a viagem
        //ela altera o botão de "iniciar" para "finalizar e faz aparecer no mapa o caminho entre destino e origem
    }

    fun porterDestiny() {
        //essa função é responsável pelo que acontece quando o carregador chega ao destino e finaliza a viagem.
        //ele abre o dialog passando a informação de preço
        val valor = "15,00"
        finalDialog(valor)
    }

    fun finalDialog(valor: String) {
        //esse dialog vai ser aberto quando clicar no botão de finalizar corrida.
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Final da corrida")
        alertDialog.setMessage("O valor da corrida foi $valor?")
        //depois do dinheiro recebido, volta pra tela inicial
        alertDialog.setPositiveButton("Sim") { _, _ ->
            startActivity( Intent(this, MapsActivity::class.java) )

        }
        alertDialog.setNegativeButton("Não recebido") { _, _ -> Toast.makeText(this, "Deu ruim", Toast.LENGTH_LONG).show() }
        alertDialog.show()
    }



}
