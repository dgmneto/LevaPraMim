package br.ufpe.cin.levapramim.domain.interactors

import br.ufpe.cin.levapramim.domain.interactors.base.Interactor
import br.ufpe.cin.levapramim.domain.models.Place
import br.ufpe.cin.levapramim.domain.models.Trip

interface WatchTripsInMarketInteractor : Interactor {
    interface Callback {
        fun onTrip(id: String, trip: Trip?, origin: Place?, destiny: Place?)

        fun onError(throwable: Throwable)
    }
}