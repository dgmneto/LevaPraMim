package br.ufpe.cin.levapramim.presentation.presenters

import br.ufpe.cin.levapramim.domain.models.Market
import br.ufpe.cin.levapramim.domain.models.Place
import br.ufpe.cin.levapramim.presentation.presenters.base.Presenter
import br.ufpe.cin.levapramim.presentation.ui.BaseView

interface ClientMainPresenter : Presenter {
    interface View : BaseView {
        fun onPlaces(origins: List<Place>, destinies: List<Place>)
    }

    fun resume(market: Market)
}