package br.ufpe.cin.levapramim.domain.interactors.impl

import br.ufpe.cin.levapramim.domain.executors.MainThread
import br.ufpe.cin.levapramim.domain.interactors.CreateTripAndSubscribeToUpdatesInteractor
import br.ufpe.cin.levapramim.domain.interactors.base.AbstractInteractor
import br.ufpe.cin.levapramim.domain.models.Market
import br.ufpe.cin.levapramim.domain.models.Place
import br.ufpe.cin.levapramim.domain.models.Trip
import br.ufpe.cin.levapramim.domain.models.User
import br.ufpe.cin.levapramim.domain.models.trip.Status
import br.ufpe.cin.levapramim.domain.models.user.UserType
import br.ufpe.cin.levapramim.domain.repositories.TripRepository
import br.ufpe.cin.levapramim.domain.repositories.UserRepository
import java.util.concurrent.Executor

class CreateTripAndSubscribeToUpdatesInteractorImpl(
    mExecutor : Executor,
    mMainThread : MainThread,
    val userRepository: UserRepository,
    val tripRepository: TripRepository,
    val origin: Place,
    val destiny: Place,
    val market: Market,
    val callback : CreateTripAndSubscribeToUpdatesInteractor.Callback)
: AbstractInteractor(mExecutor, mMainThread), CreateTripAndSubscribeToUpdatesInteractor, TripRepository.Callback, UserRepository.Callback{
    override fun onUser(id: String, type: UserType?, user: User?) {
        val status = Status.PENDING
        val fromId = origin.id
        val toId = destiny.id
        val marketId = market.id
        val trip = Trip(
            marketId = marketId,
            fromId = fromId,
            toId = toId,
            status = status,
            clientId = id,
            id = null,
            carrierId = null)
        tripRepository.createTripAndSubscribeToUpdates(trip, this)
    }

    override fun onTrip(id: String, trip: Trip?) {
        callback.onTrip(trip!!)
    }

    override fun onError(throwable: Throwable) {
        callback.onError(throwable)
    }

    override fun run() {
        userRepository.getLoggedUser(this)
    }
}