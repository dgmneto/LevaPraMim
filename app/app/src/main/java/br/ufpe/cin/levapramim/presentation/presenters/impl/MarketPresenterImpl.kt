package br.ufpe.cin.levapramim.presentation.presenters.impl

import br.ufpe.cin.levapramim.domain.executors.MainThread
import br.ufpe.cin.levapramim.domain.interactors.GetClosestMarketInteractor
import br.ufpe.cin.levapramim.domain.interactors.impl.GetClosestMarketInteractorImpl
import br.ufpe.cin.levapramim.domain.interactors.impl.GetLoggedUserInteractorImpl
import br.ufpe.cin.levapramim.domain.models.Market
import br.ufpe.cin.levapramim.presentation.presenters.LoggedPresenter
import br.ufpe.cin.levapramim.presentation.presenters.MarketPresenter
import br.ufpe.cin.levapramim.presentation.presenters.base.AbstractPresenter
import java.util.concurrent.Executor

class MarketPresenterImpl(mMainThread: MainThread,
                          mExecutor: Executor,
                          val mView: MarketPresenter.View
) : AbstractPresenter(mMainThread, mExecutor), MarketPresenter, GetClosestMarketInteractor.Callback {
    override fun onSuccess(market: Market) {
        mView.onMarket(market)
    }

    override fun onError(throwable: Throwable) {
        throwable.printStackTrace()
        mView.showError(throwable.message ?: "Unkown error")
    }

    override fun resume() {
        val interactor = GetClosestMarketInteractorImpl(mExecutor, mMainThread, this)
        interactor.execute()
    }
}