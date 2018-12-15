package br.ufpe.cin.levapramim.presentation.presenters

import br.ufpe.cin.levapramim.domain.models.Market
import br.ufpe.cin.levapramim.domain.models.Place
import br.ufpe.cin.levapramim.domain.models.Trip
import br.ufpe.cin.levapramim.presentation.presenters.base.Presenter
import br.ufpe.cin.levapramim.presentation.ui.BaseView

interface ClientTripPresenter : Presenter {
    interface View : BaseView {
        fun onTrip(trip: Trip)
    }

    fun create(market: Market, origin: Place, destiny: Place)
}