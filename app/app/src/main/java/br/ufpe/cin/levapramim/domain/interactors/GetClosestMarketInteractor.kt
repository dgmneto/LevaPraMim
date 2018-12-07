package br.ufpe.cin.levapramim.domain.interactors

import br.ufpe.cin.levapramim.domain.interactors.base.Interactor
import br.ufpe.cin.levapramim.domain.models.Market

interface GetClosestMarketInteractor : Interactor {
    interface Callback {
        fun onSuccess(market: Market)
        fun onError()
    }

}