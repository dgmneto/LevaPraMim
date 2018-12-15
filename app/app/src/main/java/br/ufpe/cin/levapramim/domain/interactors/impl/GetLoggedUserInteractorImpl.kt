package br.ufpe.cin.levapramim.domain.interactors.impl

import br.ufpe.cin.levapramim.domain.executors.MainThread
import br.ufpe.cin.levapramim.domain.interactors.GetLoggedUserInteractor
import br.ufpe.cin.levapramim.domain.interactors.GetLoggedUserInteractor.Callback
import br.ufpe.cin.levapramim.domain.interactors.base.AbstractInteractor
import br.ufpe.cin.levapramim.domain.models.User
import br.ufpe.cin.levapramim.domain.models.user.UserType
import br.ufpe.cin.levapramim.domain.repositories.UserRepository
import java.util.concurrent.Executor

class GetLoggedUserInteractorImpl(
    mExecutor : Executor,
    mMainThread : MainThread,
    val callback : Callback,
    val userRepository: UserRepository) :
        AbstractInteractor(mExecutor, mMainThread),
        GetLoggedUserInteractor,
        UserRepository.Callback {

    override fun onUser(id: String, type: UserType?, user: User?) {
        mMainThread.post(Runnable {
            callback.onSuccess(type!!, user!!)
        })
    }

    override fun onError(throwable: Throwable) {
        mMainThread.post(Runnable {
            callback.onError(throwable)
        })
    }

    override fun run() {
        userRepository.getLoggedUser(this)
    }
}