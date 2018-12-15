package br.ufpe.cin.levapramim.domain.interactors

import br.ufpe.cin.levapramim.domain.interactors.base.Interactor
import br.ufpe.cin.levapramim.domain.models.User
import br.ufpe.cin.levapramim.domain.models.user.UserType

interface GetLoggedUserInteractor : Interactor {
    interface Callback {
        fun onSuccess(userType: UserType, user : User)
        fun onError(throwable: Throwable)
    }
}