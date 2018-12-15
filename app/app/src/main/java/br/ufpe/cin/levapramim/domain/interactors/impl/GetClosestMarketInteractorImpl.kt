package br.ufpe.cin.levapramim.domain.interactors.impl

import br.ufpe.cin.levapramim.domain.executors.MainThread
import br.ufpe.cin.levapramim.domain.interactors.GetClosestMarketInteractor.Callback
import br.ufpe.cin.levapramim.domain.interactors.base.AbstractInteractor
import br.ufpe.cin.levapramim.domain.models.market.BoundingBox
import br.ufpe.cin.levapramim.domain.models.Coordinates
import br.ufpe.cin.levapramim.domain.models.Market
import java.util.concurrent.Executor

class GetClosestMarketInteractorImpl(mExecutor : Executor, mMainThread : MainThread, val callback : Callback) : AbstractInteractor(mExecutor, mMainThread) {
    fun postResult(market: Market) {
        mMainThread.post(Runnable {
            callback.onSuccess(market)
        })
    }

    override fun run() {
        val market = buildMarketUFPE()
        postResult(market)
    }

    fun buildMarketUFPE() : Market {
        val upperLeft = Coordinates(-8.044960, -34.955703)
        val lowerRight = Coordinates(-8.060091, -34.945648)
        val boundingBox = BoundingBox(upperLeft, lowerRight)
        val market = Market("0", "Feira UFPE", boundingBox)
        return market
    }
}