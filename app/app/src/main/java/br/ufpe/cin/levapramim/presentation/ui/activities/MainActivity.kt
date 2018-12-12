package br.ufpe.cin.levapramim.presentation.ui.activities

import android.location.Location
import br.ufpe.cin.levapramim.domain.interactors.GetClosestMarketInteractor
import br.ufpe.cin.levapramim.domain.interactors.impl.GetClosestMarketInteractorImpl
import br.ufpe.cin.levapramim.domain.models.Market
import br.ufpe.cin.levapramim.presentation.ui.activities.base.AbstractMapsActivity
import br.ufpe.cin.levapramim.threading.MainThreadImpl
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainActivity : AbstractMapsActivity(), GetClosestMarketInteractor.Callback {
    private val SHUTDOWN_TIMEOUT_SEC = 30L
    private val MAIN_THREAD = MainThreadImpl.getInstance()
    private val THREAD_EXECUTOR = Executors.newSingleThreadExecutor()
    private lateinit var mMarket : Market
    private var wasInitialized = false

    override fun onLocation(location: Location) {
        super.onLocation(location)
        if (!wasInitialized) {
            val interactor = GetClosestMarketInteractorImpl(
                THREAD_EXECUTOR,
                MAIN_THREAD,
                this
            )
            interactor.execute()
            wasInitialized = true
        }
    }

    override fun onSuccess(market: Market) {
        mMarket = market
        title = mMarket.name
    }

    override fun onError() {
        // NOT IMPLEMENTED
    }

    override fun onStop() {
        super.onStop()
        THREAD_EXECUTOR.shutdown()
        THREAD_EXECUTOR.awaitTermination(SHUTDOWN_TIMEOUT_SEC, TimeUnit.SECONDS)
    }
}