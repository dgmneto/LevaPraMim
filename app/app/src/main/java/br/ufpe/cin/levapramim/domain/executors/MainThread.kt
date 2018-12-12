package br.ufpe.cin.levapramim.domain.executors

interface MainThread {
    fun post(runnable: Runnable)
}