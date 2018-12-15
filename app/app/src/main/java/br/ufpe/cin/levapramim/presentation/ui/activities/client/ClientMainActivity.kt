package br.ufpe.cin.levapramim.presentation.ui.activities.client

import android.content.Intent
import android.os.Bundle

import android.util.Log
import android.widget.*
import br.ufpe.cin.levapramim.R;
import br.ufpe.cin.levapramim.domain.models.Market
import br.ufpe.cin.levapramim.domain.models.Place
import br.ufpe.cin.levapramim.domain.repositories.impl.FirebasePlaceRepository
import br.ufpe.cin.levapramim.presentation.presenters.ClientMainPresenter
import br.ufpe.cin.levapramim.presentation.presenters.impl.ClientMainPresenterImpl
import br.ufpe.cin.levapramim.presentation.ui.activities.base.AbstractMarketActivity
import br.ufpe.cin.levapramim.threading.MainThreadImpl
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.Executors

class ClientMainActivity : AbstractMarketActivity(), ClientMainPresenter.View {
    private lateinit var mPresenter : ClientMainPresenter

    private lateinit var sp_pickup : Spinner
    private lateinit var sp_destiny : Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Spinners
        this.sp_pickup = findViewById(R.id.spn_Pickup) as Spinner
        this.sp_destiny = findViewById(R.id.spn_Destiny) as Spinner

        this.mPresenter = ClientMainPresenterImpl(
            MainThreadImpl.getInstance(),
            Executors.newFixedThreadPool(2),
            FirebasePlaceRepository(FirebaseFirestore.getInstance()),
            this)

        val bt_call = findViewById(R.id.btn_call) as Button
        bt_call.setOnClickListener({ callPorter(sp_pickup, sp_destiny)})
    }

    override fun onMarket(market: Market) {
        super.onMarket(market)
        this.mPresenter.resume(market)
    }

    fun callPorter(originSpinner: Spinner, destinySpinner: Spinner){
        val origin = originSpinner.selectedItem as Place
        val destiny = destinySpinner.selectedItem as Place
        val intent = Intent(this, ClientTripActivity::class.java)
        intent.putExtra(AbstractMarketActivity.MARKET_EXTRA_KEY, getMarket())
        intent.putExtra(ClientTripActivity.ORIGIN_EXTRA_KEY, origin)
        intent.putExtra(ClientTripActivity.DESTINY_EXTRA_KEY, destiny)
        startActivity(intent)
        finish()
    }

    override fun showError(messageString: String) {
        Log.e("ERROR", messageString)
    }

    override fun onPlaces(origins: List<Place>, destinies: List<Place>) {
        configureSpinner(origins, sp_pickup)
        configureSpinner(destinies, sp_destiny)
    }

    override fun getLayoutResourceId() = R.layout.activity_client_main

    private fun configureSpinner(places: List<Place>, spinner: Spinner) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, places)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }
}
