package br.ufpe.cin.levapramim.domain.repositories

import br.ufpe.cin.levapramim.domain.models.Trip
import br.ufpe.cin.levapramim.domain.models.trip.Status
import java.io.Closeable

interface TripRepository {
    interface TripsCallback {
        fun onTrip(id : String, trip : Trip?)

        fun onError(t : Throwable)
    }

    fun findTripsByMarketIdAndStatus(marketId : String, status : Status, callback: TripsCallback)

    fun updateTripStatus(tripId : String, from : Status, to : Status, callback: TripsCallback)

    fun createTripAndSubscribeToUpdates(trip : Trip, callback: TripsCallback)

    fun subscribeToUpdates(trip : Trip, callback: TripsCallback)
}