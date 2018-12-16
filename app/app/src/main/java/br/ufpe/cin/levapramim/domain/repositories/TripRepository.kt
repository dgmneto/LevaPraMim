package br.ufpe.cin.levapramim.domain.repositories

import br.ufpe.cin.levapramim.domain.models.Trip
import br.ufpe.cin.levapramim.domain.models.trip.Status
import java.util.*

interface TripRepository {
    interface Callback {
        fun onTrip(id : String, trip : Trip?) {}

        fun onTrips(trips: LinkedList<Trip>) {}

        fun onError(t : Throwable)
    }

    fun findTripsByMarketIdAndStatus(marketId : String, status : Status, callback: Callback)

    fun findTripsByUserId(userId : String, callback: Callback)

    fun updateTripStatus(tripId : String, from : Status, to : Status, callback: Callback)

    fun createTripAndSubscribeToUpdates(trip : Trip, callback: Callback)

    fun subscribeToUpdates(trip : Trip, callback: Callback)
}