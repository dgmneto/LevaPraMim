package br.ufpe.cin.levapramim.presentation.presenters.base

import br.ufpe.cin.levapramim.domain.executors.MainThread
import java.util.concurrent.Executor

abstract class AbstractPresenter(val mMainThread: MainThread, val mExecutor: Executor)