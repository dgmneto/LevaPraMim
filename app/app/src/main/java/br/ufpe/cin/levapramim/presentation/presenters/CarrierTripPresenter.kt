package br.ufpe.cin.levapramim.presentation.presenters

import br.ufpe.cin.levapramim.domain.models.Trip
import br.ufpe.cin.levapramim.domain.models.trip.Status
import br.ufpe.cin.levapramim.presentation.presenters.base.Presenter
import br.ufpe.cin.levapramim.presentation.ui.BaseView

interface CarrierTripPresenter : Presenter {
    interface View : BaseView {
        fun onTripUpdated(trip: Trip)
    }

    fun updatedStatus(trip: Trip, newStatus: Status)
}