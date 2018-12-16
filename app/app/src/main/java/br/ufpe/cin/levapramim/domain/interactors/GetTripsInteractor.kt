package br.ufpe.cin.levapramim.domain.interactors

import br.ufpe.cin.levapramim.domain.interactors.base.Interactor
import br.ufpe.cin.levapramim.domain.models.Trip
import java.util.*

interface GetTripsInteractor : Interactor {
    interface Callback {
        fun onSuccess(trips: LinkedList<Trip>)
        fun onError(throwable: Throwable)
    }
}