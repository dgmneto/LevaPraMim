package br.ufpe.cin.levapramim.domain.interactors.impl

import br.ufpe.cin.levapramim.domain.executors.MainThread
import br.ufpe.cin.levapramim.domain.interactors.UpdateTripStatusInteractor
import br.ufpe.cin.levapramim.domain.interactors.UpdateTripStatusInteractor.Callback
import br.ufpe.cin.levapramim.domain.interactors.base.AbstractInteractor
import br.ufpe.cin.levapramim.domain.models.Trip
import br.ufpe.cin.levapramim.domain.models.trip.Status
import br.ufpe.cin.levapramim.domain.repositories.TripRepository
import java.util.concurrent.Executor

class UpdateTripStatusInteractorImpl(
    mExecutor: Executor,
    mMainThread: MainThread,
    val tripRepository: TripRepository,
    val trip: Trip,
    val status: Status,
    val callback: Callback
) : AbstractInteractor(mExecutor, mMainThread), UpdateTripStatusInteractor, TripRepository.Callback {
    override fun run() {
        tripRepository.updateTripStatus(trip.id!!, trip.status!!, status, this)
    }

    override fun onTrip(id: String, trip: Trip?) {
        callback.onTrip(trip!!)
    }

    override fun onError(throwable: Throwable) {
        callback.onError(throwable)
    }
}