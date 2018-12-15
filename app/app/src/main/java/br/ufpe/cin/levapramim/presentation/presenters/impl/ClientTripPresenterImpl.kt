package br.ufpe.cin.levapramim.presentation.presenters.impl

import br.ufpe.cin.levapramim.domain.executors.MainThread
import br.ufpe.cin.levapramim.domain.interactors.CreateTripAndSubscribeToUpdatesInteractor
import br.ufpe.cin.levapramim.domain.interactors.impl.CreateTripAndSubscribeToUpdatesInteractorImpl
import br.ufpe.cin.levapramim.domain.models.Market
import br.ufpe.cin.levapramim.domain.models.Place
import br.ufpe.cin.levapramim.domain.models.Trip
import br.ufpe.cin.levapramim.domain.repositories.TripRepository
import br.ufpe.cin.levapramim.domain.repositories.UserRepository
import br.ufpe.cin.levapramim.presentation.presenters.ClientTripPresenter
import br.ufpe.cin.levapramim.presentation.presenters.base.AbstractPresenter
import java.util.concurrent.Executor

class ClientTripPresenterImpl(
    mExecutor: Executor,
    mMainThread: MainThread,
    val mUserRepository: UserRepository,
    val mTripRepository: TripRepository,
    val mView: ClientTripPresenter.View)
: AbstractPresenter(mMainThread, mExecutor), ClientTripPresenter, CreateTripAndSubscribeToUpdatesInteractor.Callback {
    override fun onTrip(trip: Trip) {
        mView.onTrip(trip)
    }

    override fun onError(throwable: Throwable) {
        throwable.printStackTrace()
        mView.showError(throwable.message ?: "Unkown error")
    }

    override fun create(market: Market, origin: Place, destiny: Place) {
        val interactor = CreateTripAndSubscribeToUpdatesInteractorImpl(
            mExecutor,
            mMainThread,
            mUserRepository,
            mTripRepository,
            origin,
            destiny,
            market,
            this)
        interactor.execute()
    }
}