package br.ufpe.cin.levapramim.presentation.presenters

import br.ufpe.cin.levapramim.domain.models.Market
import br.ufpe.cin.levapramim.presentation.presenters.base.Presenter
import br.ufpe.cin.levapramim.presentation.ui.BaseView

interface MarketPresenter : Presenter {
    interface View : BaseView {
        fun onMarket(market: Market)
    }
}