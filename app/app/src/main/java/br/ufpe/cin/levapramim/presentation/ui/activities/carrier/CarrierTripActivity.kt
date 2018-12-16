package br.ufpe.cin.levapramim.presentation.ui.activities.carrier

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import br.ufpe.cin.levapramim.R
import br.ufpe.cin.levapramim.domain.models.Place
import br.ufpe.cin.levapramim.domain.models.Trip
import br.ufpe.cin.levapramim.domain.models.trip.Status
import br.ufpe.cin.levapramim.domain.repositories.impl.FirebaseTripRepository
import br.ufpe.cin.levapramim.presentation.presenters.CarrierTripPresenter
import br.ufpe.cin.levapramim.presentation.presenters.ClientTripPresenter
import br.ufpe.cin.levapramim.presentation.presenters.impl.CarrierMainPresenterImpl
import br.ufpe.cin.levapramim.presentation.presenters.impl.CarrierTripPresenterImpl
import br.ufpe.cin.levapramim.presentation.ui.activities.base.AbstractMarketActivity
import br.ufpe.cin.levapramim.threading.MainThreadImpl
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.Executors

class CarrierTripActivity : AbstractMarketActivity(), View.OnClickListener, CarrierTripPresenter.View {
    companion object {
        val TRIP_EXTRA_KEY = "trip"
        val ORIGIN_EXTRA_KEY = "origin"
        val DESTINY_EXTRA_KEY = "destiny"

        val PICKED_TEXTS = Pair("Cheguei", "Dirija-se a origem")
        val ARRIVED_TEXTS = Pair("Começar", "Aguarde o cliente")
        val STARTED_TEXTS = Pair("Encerrar", "Leve até o destino")
        val DEFAULT_TRIP_PRICE = 15.00
    }

    private lateinit var button : Button
    private lateinit var text : TextView
    private lateinit var trip : Trip
    private lateinit var origin : Place
    private lateinit var destiny : Place
    private lateinit var presenter : CarrierTripPresenter

    private var marker : Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        button = findViewById(R.id.btn_path)
        button.setOnClickListener(this)

        text = findViewById(R.id.text_view)

        trip = intent.extras!![TRIP_EXTRA_KEY] as Trip
        updateButtonText(trip)
        origin = intent.extras!![ORIGIN_EXTRA_KEY] as Place
        destiny = intent.extras!![DESTINY_EXTRA_KEY] as Place

        val tripRepository = FirebaseTripRepository(FirebaseFirestore.getInstance(), this)
        presenter = CarrierTripPresenterImpl(
            Executors.newFixedThreadPool(2),
            MainThreadImpl.getInstance(),
            tripRepository,
            this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        super.onMapReady(googleMap)
        updateMarkerPosition(trip)
    }

    override fun onClick(v: View?) {
        val newStatus = when (trip.status!!) {
            Status.PICKED  -> Status.ARRIVED
            Status.ARRIVED -> Status.STARTED
            Status.STARTED -> Status.DONE
            else           -> throw RuntimeException("Unreachable")
        }
        presenter.updatedStatus(trip, newStatus)
    }

    override fun onTripUpdated(trip: Trip) {
        this.trip = trip
        if (trip.status == Status.DONE) {
            onTripDone()
        } else {
            updateButtonText(trip)
            updateMarkerPosition(trip)
        }
    }

    private fun updateButtonText(trip: Trip) {
        val (buttonText, viewText) = when(trip.status!!) {
            Status.PICKED  -> PICKED_TEXTS
            Status.ARRIVED -> ARRIVED_TEXTS
            Status.STARTED -> STARTED_TEXTS
            else           -> throw RuntimeException("Unreachable")
        }
        button.text = buttonText
        text.text = viewText
    }

    private fun updateMarkerPosition(trip: Trip) {
        val map = getMap()!!
        val place = when (trip.status!!) {
            Status.PICKED, Status.ARRIVED -> origin
            Status.STARTED                -> destiny
            else                          -> throw RuntimeException("Unreachable")
        }
        val latLng = LatLng(place.coordinates?.latitude!!, place.coordinates.longitude!!)
        if (marker == null) {
            marker = map.addMarker(MarkerOptions().position(latLng))
        } else {
            marker!!.position = latLng
        }
    }

    private fun onTripDone() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Final da corrida")
        alertDialog.setMessage("O valor da corrida foi R$ $DEFAULT_TRIP_PRICE?")
        alertDialog.setPositiveButton("Finalizar") { _, _ ->
            val intent = Intent(this, CarrierMainActivity::class.java)
            intent.putExtra(AbstractMarketActivity.MARKET_EXTRA_KEY, getMarket())
            startActivity(intent)
        }
        alertDialog.show()
    }

    override fun getLayoutResourceId() = R.layout.activity_carrier_trip

    override fun showError(messageString: String) {
        Log.e("ERROR", messageString)
    }

    override fun getTitleToSet() : String = String.format("%s -> %s", origin.name, destiny.name)

    override fun shouldShowMarker() = false
}