package br.ufpe.cin.levapramim.presentation.ui.activities.base

import android.os.Bundle
import br.ufpe.cin.levapramim.domain.models.Market
import br.ufpe.cin.levapramim.presentation.presenters.MarketPresenter
import br.ufpe.cin.levapramim.presentation.presenters.base.Presenter
import br.ufpe.cin.levapramim.presentation.presenters.impl.MarketPresenterImpl
import br.ufpe.cin.levapramim.threading.MainThreadImpl
import java.util.concurrent.Executors

abstract class AbstractMarketActivity : AbstractMapsActivity(), MarketPresenter.View {
    companion object {
        val MARKET_EXTRA_KEY = "market_extra"
    }
    private lateinit var mPresenter : MarketPresenter
    private lateinit var mMarket : Market

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mPresenter = MarketPresenterImpl(
            MainThreadImpl.getInstance(),
            Executors.newFixedThreadPool(2),
            this
        )

        val market = intent.extras?.get(MARKET_EXTRA_KEY) as Market?
        if (market != null) {
            mMarket = market
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.resume()
    }

    override fun onMarket(market: Market) {
        this.mMarket = market
        this.title = getTitleToSet() ?: mMarket.name
    }

    protected fun getMarket() = mMarket

    open fun getTitleToSet(): String? = null

}