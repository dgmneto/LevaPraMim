package br.ufpe.cin.levapramim.presentation.presenters.impl

import br.ufpe.cin.levapramim.domain.executors.MainThread
import br.ufpe.cin.levapramim.domain.interactors.UpdateTripStatusInteractor
import br.ufpe.cin.levapramim.domain.interactors.impl.UpdateTripStatusInteractorImpl
import br.ufpe.cin.levapramim.domain.models.Trip
import br.ufpe.cin.levapramim.domain.models.trip.Status
import br.ufpe.cin.levapramim.domain.repositories.impl.FirebaseTripRepository
import br.ufpe.cin.levapramim.domain.repositories.impl.FirebaseUserRepository
import br.ufpe.cin.levapramim.presentation.presenters.CarrierTripPresenter
import br.ufpe.cin.levapramim.presentation.presenters.base.AbstractPresenter
import java.util.concurrent.Executor

class CarrierTripPresenterImpl(
    mExecutor: Executor,
    mMainThread: MainThread,
    val tripRepository: FirebaseTripRepository,
    val userRepository: FirebaseUserRepository,
    val view : CarrierTripPresenter.View
): AbstractPresenter(mMainThread, mExecutor), CarrierTripPresenter, UpdateTripStatusInteractor.Callback {
    override fun updatedStatus(trip: Trip, newStatus: Status) {
        val interactor = UpdateTripStatusInteractorImpl(
            mExecutor,
            mMainThread,
            tripRepository,
            userRepository,
            trip,
            newStatus,
            this)
        interactor.execute()
    }

    override fun onTrip(trip: Trip) {
        view.onTripUpdated(trip)
    }

    override fun onError(throwable: Throwable) {
        view.showError(throwable.message ?: "Unkown error")
    }
}