package br.ufpe.cin.levapramim.presentation.ui.activities

import android.location.Location
import android.util.Log
import br.ufpe.cin.levapramim.R
import br.ufpe.cin.levapramim.domain.interactors.GetClosestMarketInteractor
import br.ufpe.cin.levapramim.domain.interactors.impl.GetClosestMarketInteractorImpl
import br.ufpe.cin.levapramim.domain.models.Market
import br.ufpe.cin.levapramim.presentation.ui.activities.base.AbstractMapsActivity
import br.ufpe.cin.levapramim.presentation.ui.activities.base.AbstractMarketActivity
import br.ufpe.cin.levapramim.threading.MainThreadImpl
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class CarrierMainActivity : AbstractMarketActivity() {
    override fun showError(messageString: String) {
        Log.e("ERROR", messageString)
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_maps
    }
}