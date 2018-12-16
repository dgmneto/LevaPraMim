package br.ufpe.cin.levapramim.domain.interactors.impl

import br.ufpe.cin.levapramim.domain.executors.MainThread
import br.ufpe.cin.levapramim.domain.interactors.GetTripsInteractor
import br.ufpe.cin.levapramim.domain.interactors.base.AbstractInteractor
import br.ufpe.cin.levapramim.domain.models.Place
import br.ufpe.cin.levapramim.domain.models.Trip
import br.ufpe.cin.levapramim.domain.models.User
import br.ufpe.cin.levapramim.domain.models.place.PlaceType
import br.ufpe.cin.levapramim.domain.models.user.UserType
import br.ufpe.cin.levapramim.domain.repositories.TripRepository
import java.util.*
import java.util.concurrent.Executor

class GetTripsInteractorImpl(
    mExecutor : Executor,
    mMainThread : MainThread,
    val callback : GetTripsInteractor.Callback,
    val tripRepository: TripRepository
) :
    AbstractInteractor(mExecutor, mMainThread),
    GetTripsInteractor,
    TripRepository.Callback {

    override fun onTrips(trips: LinkedList<Trip>) {
        callback.onSuccess(trips)
    }

    override fun onError(throwable: Throwable) {
        mMainThread.post(Runnable {
            callback.onError(throwable)
        })
    }

    override fun run() {
        tripRepository
    }
}

