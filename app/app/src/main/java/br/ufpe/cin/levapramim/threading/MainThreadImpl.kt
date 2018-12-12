package br.ufpe.cin.levapramim.threading

import android.os.Handler
import android.os.Looper
import br.ufpe.cin.levapramim.domain.executors.MainThread

class MainThreadImpl private constructor() : MainThread {
    private val mHandler : Handler = Handler(Looper.getMainLooper())

    companion object {
        private var instance : MainThreadImpl? = null
        fun getInstance() : MainThreadImpl {
            if (instance == null) {
                instance = MainThreadImpl()
            }
            return instance!!
        }
    }

    override fun post(runnable: Runnable) {
        mHandler.post(runnable)
    }

}