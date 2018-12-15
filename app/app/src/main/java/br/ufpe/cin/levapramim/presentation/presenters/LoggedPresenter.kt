package br.ufpe.cin.levapramim.presentation.presenters

import br.ufpe.cin.levapramim.domain.models.User
import br.ufpe.cin.levapramim.domain.models.user.UserType
import br.ufpe.cin.levapramim.presentation.presenters.base.Presenter
import br.ufpe.cin.levapramim.presentation.ui.BaseView

interface LoggedPresenter : Presenter {
    interface View : BaseView {
        fun onUser(type : UserType, user : User)
    }
}