package br.ufpe.cin.levapramim.presentation.presenters.impl

import br.ufpe.cin.levapramim.domain.executors.MainThread
import br.ufpe.cin.levapramim.domain.interactors.GetLoggedUserInteractor
import br.ufpe.cin.levapramim.domain.interactors.impl.GetClosestMarketInteractorImpl
import br.ufpe.cin.levapramim.domain.interactors.impl.GetLoggedUserInteractorImpl
import br.ufpe.cin.levapramim.domain.models.User
import br.ufpe.cin.levapramim.domain.models.user.UserType
import br.ufpe.cin.levapramim.domain.repositories.UserRepository
import br.ufpe.cin.levapramim.presentation.presenters.LoggedPresenter
import br.ufpe.cin.levapramim.presentation.presenters.LoggedPresenter.View
import br.ufpe.cin.levapramim.presentation.presenters.base.AbstractPresenter
import java.util.concurrent.Executor
import java.util.prefs.AbstractPreferences

class LoggedPresenterImpl(
    mMainThread: MainThread,
    mExecutor: Executor,
    val mView: View,
    val mUserRepository: UserRepository) :
        AbstractPresenter(mMainThread, mExecutor),
        LoggedPresenter,
        GetLoggedUserInteractor.Callback {
    override fun resume() {
        val interactor = GetLoggedUserInteractorImpl(
            mExecutor,
            mMainThread,
            this ,
            mUserRepository)
        interactor.execute()
    }

    override fun onSuccess(userType: UserType, user: User) {
        mView.onUser(userType, user)
    }

    override fun onError(throwable: Throwable) {
        throwable.printStackTrace()
        mView.showError(throwable.message ?: "Unkown error")
    }
}