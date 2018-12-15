package br.ufpe.cin.levapramim.presentation.presenters.impl

import br.ufpe.cin.levapramim.domain.executors.MainThread
import br.ufpe.cin.levapramim.domain.interactors.GetPlacesByMarketInteractor
import br.ufpe.cin.levapramim.domain.interactors.impl.GetPlacesByMarketInteractorImpl
import br.ufpe.cin.levapramim.domain.models.Market
import br.ufpe.cin.levapramim.domain.models.Place
import br.ufpe.cin.levapramim.domain.repositories.PlaceRepository
import br.ufpe.cin.levapramim.presentation.presenters.ClientMainPresenter
import java.util.concurrent.Executor

class ClientMainPresenterImpl(
    val mMainThead : MainThread,
    val mExecutor: Executor,
    val repository: PlaceRepository,
    val mView: ClientMainPresenter.View)
: ClientMainPresenter, GetPlacesByMarketInteractor.Callback {
    override fun onError(throwable: Throwable) {
        throwable.printStackTrace()
        mView.showError(throwable.message ?: "Unkown error")
    }

    override fun onSuccess(origins: List<Place>, destinies: List<Place>) {
        mView.onPlaces(origins, destinies)
    }

    override fun resume(market: Market) {
        val interactor = GetPlacesByMarketInteractorImpl(mExecutor, mMainThead, market, this, repository)
        interactor.execute()
    }
}