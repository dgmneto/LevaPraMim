package br.ufpe.cin.levapramim.domain.interactors.impl

import br.ufpe.cin.levapramim.domain.executors.MainThread
import br.ufpe.cin.levapramim.domain.interactors.WatchTripsInMarketInteractor
import br.ufpe.cin.levapramim.domain.interactors.WatchTripsInMarketInteractor.Callback
import br.ufpe.cin.levapramim.domain.interactors.base.AbstractInteractor
import br.ufpe.cin.levapramim.domain.models.Market
import br.ufpe.cin.levapramim.domain.models.Place
import br.ufpe.cin.levapramim.domain.models.Trip
import br.ufpe.cin.levapramim.domain.models.trip.Status
import br.ufpe.cin.levapramim.domain.repositories.PlaceRepository
import br.ufpe.cin.levapramim.domain.repositories.TripRepository
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executor

class WatchTripsInMarketInteractorImpl(
    mExecutor: Executor,
    mMainThread: MainThread,
    val placeRepository: PlaceRepository,
    val tripRepository: TripRepository,
    var market: Market,
    val callback: Callback
) : AbstractInteractor(mExecutor, mMainThread), WatchTripsInMarketInteractor, TripRepository.Callback {
    override fun onTrip(id: String, trip: Trip?) {
        if (trip == null) {
            callback.onTrip(id, null, null, null)
            return
        }
        val placesIds = LinkedList<String>()
        placesIds.add(trip.fromId!!)
        placesIds.add(trip.toId!!)
        val callback = PlaceCalback(trip)
        placeRepository.findPlacesByIds(placesIds, callback)
    }

    override fun onError(throwable: Throwable) {
        callback.onError(throwable)
    }

    override fun run() {
        tripRepository.findTripsByMarketIdAndStatus(market.id, Status.PENDING, this)
    }

    inner class PlaceCalback(val trip: Trip) : PlaceRepository.Callback {
        override fun onPlaces(places: List<Place>) {
            lateinit var origin: Place
            lateinit var destiny: Place
            for (place in places) {
                if (trip.fromId == place.id) origin = place
                else if (trip.toId == place.id) destiny = place
            }
            this@WatchTripsInMarketInteractorImpl.callback.onTrip(trip.id!!, trip, origin, destiny)
        }

        override fun onError(throwable: Throwable) {
            this@WatchTripsInMarketInteractorImpl.onError(throwable)
        }
    }
}