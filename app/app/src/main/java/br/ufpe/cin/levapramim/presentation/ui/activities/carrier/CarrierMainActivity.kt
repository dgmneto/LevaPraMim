package br.ufpe.cin.levapramim.presentation.ui.activities.carrier

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import br.ufpe.cin.levapramim.R
import br.ufpe.cin.levapramim.domain.models.Market
import br.ufpe.cin.levapramim.domain.models.Place
import br.ufpe.cin.levapramim.domain.models.Trip
import br.ufpe.cin.levapramim.domain.models.trip.Status
import br.ufpe.cin.levapramim.domain.repositories.impl.FirebasePlaceRepository
import br.ufpe.cin.levapramim.domain.repositories.impl.FirebaseTripRepository
import br.ufpe.cin.levapramim.domain.repositories.impl.FirebaseUserRepository
import br.ufpe.cin.levapramim.presentation.presenters.CarrierMainPresenter
import br.ufpe.cin.levapramim.presentation.presenters.impl.CarrierMainPresenterImpl
import br.ufpe.cin.levapramim.presentation.ui.activities.base.AbstractMarketActivity
import br.ufpe.cin.levapramim.threading.MainThreadImpl

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import java.util.concurrent.Executors


class CarrierMainActivity : AbstractMarketActivity(), GoogleMap.OnMarkerClickListener, CarrierMainPresenter.View {
    private val markers = HashMap<String, Marker>()
    private val trips = HashMap<Marker, Trip>()
    private val placesByTrip = HashMap<String, Pair<Place, Place>>()
    private var pickedPlaces : Pair<Place, Place>? = null

    private lateinit var presenter: CarrierMainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tripRepository = FirebaseTripRepository(FirebaseFirestore.getInstance(), this)
        val placeRepository = FirebasePlaceRepository(FirebaseFirestore.getInstance())
        val userRepository = FirebaseUserRepository(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance())
        presenter = CarrierMainPresenterImpl(
            MainThreadImpl.getInstance(),
            Executors.newFixedThreadPool(2),
            tripRepository,
            placeRepository,
            userRepository,
            this)
    }

    override fun onMarket(market: Market) {
        super.onMarket(market)
        presenter.resume(market)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        super.onMapReady(googleMap)
        googleMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        val marker = p0!!
        val trip = trips[marker]!!
        val (origin, destiny) = placesByTrip[trip.id]!!
        receiveDialog(trip, origin, destiny)
        return true
    }

    fun receiveDialog(trip: Trip, origin: Place, destiny: Place) {
        val from = origin.name!!
        val to = destiny.name!!
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Nova corrida")
        alertDialog.setMessage("deseja aceitar a corrida de $from para $to?")

        alertDialog.setPositiveButton("Sim") { _, _ ->
            pickedPlaces = Pair(origin, destiny)
            presenter.pickTrip(trip)
        }
        alertDialog.setNegativeButton("Não") { _, _ -> }

        alertDialog.show()

    }

    override fun onTrip(id: String, trip: Trip?, origin: Place?, destiny: Place?) {
        if (trip == null) {
            val marker = markers[id]
            if (marker != null) {
                trips.remove(marker)
                marker.remove()
                markers.remove(id)
                placesByTrip.remove(id)
            }
        } else {
            when (trip.status) {
                Status.PENDING -> {
                    if (markers.containsKey(id)) {
                        val marker = markers[id]!!
                        marker.remove()
                    }
                    val marker = addTripToMap(trip, origin!!, destiny!!)
                    markers.set(id, marker)
                    trips.set(marker, trip)
                    placesByTrip.set(id, Pair(origin, destiny))
                }
                else -> {
                    val marker = markers[id]
                    if (marker != null) {
                        marker.remove()
                        markers.remove(id)
                    }
                }
            }
        }
    }

    override fun onTripPicked(trip: Trip) {
        val (origin, destiny) = pickedPlaces!!
        val intent = Intent(this, CarrierTripActivity::class.java)
        intent.putExtra(CarrierTripActivity.TRIP_EXTRA_KEY, trip)
        intent.putExtra(CarrierTripActivity.ORIGIN_EXTRA_KEY, origin)
        intent.putExtra(CarrierTripActivity.DESTINY_EXTRA_KEY, destiny)
        intent.putExtra(AbstractMarketActivity.MARKET_EXTRA_KEY, getMarket())
        startActivity(intent)
        finish()
    }

    fun addTripToMap(trip: Trip, origin: Place, destiny: Place) : Marker {
        val map = getMap()
        val position = LatLng(
            origin.coordinates?.latitude!!,
            origin.coordinates.longitude!!)
        val options = MarkerOptions()
            .position(position)
            .title(origin.name)
        return map!!.addMarker(options)
    }

    override fun showError(messageString: String) {
        Log.e("ERROR", messageString)
    }

    override fun getLayoutResourceId() = R.layout.activity_carrier_main

    override fun shouldShowMarker() = false
}