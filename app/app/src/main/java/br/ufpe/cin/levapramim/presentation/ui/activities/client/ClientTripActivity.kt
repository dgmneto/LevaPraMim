package br.ufpe.cin.levapramim.presentation.ui.activities.client

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.TextView
import br.ufpe.cin.levapramim.R
import br.ufpe.cin.levapramim.domain.models.Place
import br.ufpe.cin.levapramim.domain.models.Trip
import br.ufpe.cin.levapramim.domain.models.trip.Status
import br.ufpe.cin.levapramim.domain.repositories.impl.FirebaseTripRepository
import br.ufpe.cin.levapramim.domain.repositories.impl.FirebaseUserRepository
import br.ufpe.cin.levapramim.presentation.presenters.ClientTripPresenter
import br.ufpe.cin.levapramim.presentation.presenters.impl.ClientTripPresenterImpl
import br.ufpe.cin.levapramim.presentation.ui.activities.base.AbstractMarketActivity
import br.ufpe.cin.levapramim.presentation.ui.activities.carrier.CarrierMainActivity
import br.ufpe.cin.levapramim.presentation.ui.activities.carrier.CarrierTripActivity
import br.ufpe.cin.levapramim.threading.MainThreadImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.RuntimeException
import java.util.concurrent.Executors


class ClientTripActivity : AbstractMarketActivity(), ClientTripPresenter.View {
    companion object {
        const val ORIGIN_EXTRA_KEY = "ORIGIN_PLACE"
        const val DESTINY_EXTRA_KEY = "DESTINY_PLACE"
        const val PENDING_TRIP_MESSAGE = "Aguardando carregador"
        const val PICKED_TRIP_MESSAGE = "O carregador está a caminho"
        const val ARRIVED_TRIP_MESSAGE = "O carregador chegou"
        const val STARTED_TRIP_MESSAGE = "A corrida está acontecendo"
    }

    private lateinit var presenter : ClientTripPresenter

    private lateinit var textViewToChange : TextView

    private lateinit var mOrigin: Place
    private lateinit var mDestiny: Place
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.mOrigin = intent.extras!![ORIGIN_EXTRA_KEY] as Place
        this.mDestiny = intent.extras!![DESTINY_EXTRA_KEY] as Place

        this.textViewToChange = findViewById(R.id.status)

        val userRepository = FirebaseUserRepository(
            FirebaseAuth.getInstance(),
            FirebaseFirestore.getInstance())
        val tripRepository = FirebaseTripRepository(
            FirebaseFirestore.getInstance(),
            this)
        this.presenter = ClientTripPresenterImpl(
            Executors.newFixedThreadPool(2),
            MainThreadImpl.getInstance(),
            userRepository,
            tripRepository,
            this)

        presenter.create(getMarket(), mOrigin, mDestiny)
    }

    override fun onTrip(trip: Trip) {
        this.textViewToChange.text = when (trip.status!!) {
            Status.PENDING -> PENDING_TRIP_MESSAGE
            Status.PICKED  -> PICKED_TRIP_MESSAGE
            Status.ARRIVED -> ARRIVED_TRIP_MESSAGE
            Status.STARTED -> STARTED_TRIP_MESSAGE
            Status.DONE    -> {
                onTripDone(trip)
                return
            }
        }
    }

    private fun onTripDone(trips: Trip) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("A corrida acabou")
        alertDialog.setMessage("O valor da corrida foi R$ ${CarrierTripActivity.DEFAULT_TRIP_PRICE}.")
        alertDialog.setPositiveButton("Finalizar") { _, _ ->
            val intent = Intent(this, ClientMainActivity::class.java)
            intent.putExtra(AbstractMarketActivity.MARKET_EXTRA_KEY, getMarket())
            startActivity(intent)
        }
        alertDialog.show()
    }

    override fun showError(messageString: String) {
        Log.e("ERROR", messageString)
    }

    override fun getTitleToSet() : String = String.format("%s -> %s", mOrigin.name, mDestiny.name)

    override fun getLayoutResourceId(): Int = R.layout.activity_client_trip
}