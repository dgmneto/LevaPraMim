package br.ufpe.cin.levapramim.domain.interactors.impl

import br.ufpe.cin.levapramim.domain.executors.MainThread
import br.ufpe.cin.levapramim.domain.interactors.UpdateTripStatusInteractor
import br.ufpe.cin.levapramim.domain.interactors.UpdateTripStatusInteractor.Callback
import br.ufpe.cin.levapramim.domain.interactors.base.AbstractInteractor
import br.ufpe.cin.levapramim.domain.models.Trip
import br.ufpe.cin.levapramim.domain.models.User
import br.ufpe.cin.levapramim.domain.models.trip.Status
import br.ufpe.cin.levapramim.domain.models.user.UserType
import br.ufpe.cin.levapramim.domain.repositories.TripRepository
import br.ufpe.cin.levapramim.domain.repositories.UserRepository
import java.util.concurrent.Executor

class UpdateTripStatusInteractorImpl(
    mExecutor: Executor,
    mMainThread: MainThread,
    val tripRepository: TripRepository,
    val userRepository: UserRepository,
    val trip: Trip,
    val status: Status,
    val callback: Callback
) : AbstractInteractor(mExecutor, mMainThread), UpdateTripStatusInteractor, UserRepository.Callback, TripRepository.Callback {
    override fun run() {
        if (status == Status.PICKED) {
            userRepository.getLoggedUser(this)
        } else {
            val updatedTrip = trip.copy(status = status)
            tripRepository.updateTrip(trip, updatedTrip, this)
        }
    }

    override fun onUser(id: String, type: UserType?, user: User?) {
        val updatedTrip = trip.copy(status = status, carrierId = id)
        tripRepository.updateTrip(trip, updatedTrip, this)
    }

    override fun onTrip(id: String, trip: Trip?) {
        callback.onTrip(trip!!)
    }

    override fun onError(throwable: Throwable) {
        callback.onError(throwable)
    }
}