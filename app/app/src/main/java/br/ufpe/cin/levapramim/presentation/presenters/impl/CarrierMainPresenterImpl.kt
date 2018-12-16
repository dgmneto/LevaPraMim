package br.ufpe.cin.levapramim.presentation.presenters.impl

import br.ufpe.cin.levapramim.domain.executors.MainThread
import br.ufpe.cin.levapramim.domain.interactors.UpdateTripStatusInteractor
import br.ufpe.cin.levapramim.domain.interactors.WatchTripsInMarketInteractor
import br.ufpe.cin.levapramim.domain.interactors.impl.UpdateTripStatusInteractorImpl
import br.ufpe.cin.levapramim.domain.interactors.impl.WatchTripsInMarketInteractorImpl
import br.ufpe.cin.levapramim.domain.models.Market
import br.ufpe.cin.levapramim.domain.models.Place
import br.ufpe.cin.levapramim.domain.models.Trip
import br.ufpe.cin.levapramim.domain.models.trip.Status
import br.ufpe.cin.levapramim.domain.repositories.PlaceRepository
import br.ufpe.cin.levapramim.domain.repositories.TripRepository
import br.ufpe.cin.levapramim.domain.repositories.UserRepository
import br.ufpe.cin.levapramim.presentation.presenters.CarrierMainPresenter
import br.ufpe.cin.levapramim.presentation.presenters.base.AbstractPresenter
import java.util.concurrent.Executor

class CarrierMainPresenterImpl(
    mExecutor: MainThread,
    mMainThread: Executor,
    val tripRepository: TripRepository,
    val placeRepository: PlaceRepository,
    val userRepository: UserRepository,
    val view: CarrierMainPresenter.View
) : AbstractPresenter(mExecutor, mMainThread),  CarrierMainPresenter, WatchTripsInMarketInteractor.Callback, UpdateTripStatusInteractor.Callback {
    override fun resume(market: Market) {
        val interactor = WatchTripsInMarketInteractorImpl(
            mExecutor,
            mMainThread,
            placeRepository,
            tripRepository,
            market,
            this)
        interactor.execute()
    }

    override fun pickTrip(trip: Trip) {
        val interactor = UpdateTripStatusInteractorImpl(
            mExecutor,
            mMainThread,
            tripRepository,
            userRepository,
            trip,
            Status.PICKED,
            this)
        interactor.execute()
    }

    override fun onTrip(trip: Trip) {
        view.onTripPicked(trip)
    }

    override fun onError(throwable: Throwable) {
        view.showError(throwable.message ?: "Unknown error")
    }

    override fun onTrip(id: String, trip: Trip?, origin: Place?, destiny: Place?) {
        view.onTrip(id, trip, origin, destiny)
    }

}