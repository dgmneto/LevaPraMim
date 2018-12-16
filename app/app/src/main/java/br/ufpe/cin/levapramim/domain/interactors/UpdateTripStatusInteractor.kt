package br.ufpe.cin.levapramim.domain.interactors

import br.ufpe.cin.levapramim.domain.interactors.base.Interactor
import br.ufpe.cin.levapramim.domain.models.Trip

interface UpdateTripStatusInteractor : Interactor {
    interface Callback {
        fun onTrip(trip: Trip)

        fun onError(throwable: Throwable)
    }
}