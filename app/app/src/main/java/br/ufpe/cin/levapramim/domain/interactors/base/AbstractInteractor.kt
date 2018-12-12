package br.ufpe.cin.levapramim.domain.interactors.base

import br.ufpe.cin.levapramim.domain.executors.MainThread
import java.util.concurrent.Executor


abstract class AbstractInteractor(val mExecutor : Executor, val mMainThread : MainThread) : Interactor, Runnable {
    @Volatile private var mIsCanceled = false
    @Volatile private var mIsRunning = false

    abstract override fun run()

    fun cancel() {
        mIsCanceled = true
        mIsRunning = false
    }

    fun onFinished() {
        mIsCanceled = false
        mIsRunning = false
    }

    override fun execute() {
        mIsRunning = true
        mExecutor.execute(this)
    }
}