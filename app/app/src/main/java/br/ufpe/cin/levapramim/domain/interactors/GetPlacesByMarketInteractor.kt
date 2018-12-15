package br.ufpe.cin.levapramim.domain.interactors

import br.ufpe.cin.levapramim.domain.interactors.base.Interactor
import br.ufpe.cin.levapramim.domain.models.Place

interface GetPlacesByMarketInteractor : Interactor {
    interface Callback {
        fun onSuccess(origins: List<Place>, destinies: List<Place>)
        fun onError(throwable: Throwable)
    }
}