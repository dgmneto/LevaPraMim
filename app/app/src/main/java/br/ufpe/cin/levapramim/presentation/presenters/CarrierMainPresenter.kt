package br.ufpe.cin.levapramim.presentation.presenters

import br.ufpe.cin.levapramim.domain.models.Market
import br.ufpe.cin.levapramim.domain.models.Place
import br.ufpe.cin.levapramim.domain.models.Trip
import br.ufpe.cin.levapramim.presentation.presenters.base.Presenter
import br.ufpe.cin.levapramim.presentation.ui.BaseView

interface CarrierMainPresenter : Presenter {
    interface View : BaseView {
        fun onTrip(id: String, trip: Trip?, origin: Place?, destiny: Place?)

        fun onTripPicked(trip: Trip)
    }

    fun resume(market: Market)

    fun pickTrip(trip: Trip)
}