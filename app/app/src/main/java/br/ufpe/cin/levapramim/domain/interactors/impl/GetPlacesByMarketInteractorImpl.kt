package br.ufpe.cin.levapramim.domain.interactors.impl

import br.ufpe.cin.levapramim.domain.executors.MainThread
import br.ufpe.cin.levapramim.domain.interactors.GetPlacesByMarketInteractor
import br.ufpe.cin.levapramim.domain.interactors.base.AbstractInteractor
import br.ufpe.cin.levapramim.domain.models.Market
import br.ufpe.cin.levapramim.domain.models.Place
import br.ufpe.cin.levapramim.domain.models.place.PlaceType
import br.ufpe.cin.levapramim.domain.repositories.PlaceRepository
import java.util.concurrent.Executor

class GetPlacesByMarketInteractorImpl(
    mExecutor : Executor,
    mMainThread : MainThread,
    val market : Market,
    val callback : GetPlacesByMarketInteractor.Callback,
    val repository: PlaceRepository)
: AbstractInteractor(mExecutor, mMainThread), GetPlacesByMarketInteractor, PlaceRepository.Callback{
    override fun onPlaces(places: List<Place>) {
        val origins = ArrayList<Place>()
        val destinies = ArrayList<Place>()
        for (place in places) {
            when (place.type!!) {
                PlaceType.ORIGIN -> origins.add(place)
                PlaceType.DESTINY -> destinies.add(place)
            }
        }
        callback.onSuccess(origins, destinies)
    }

    override fun onError(throwable: Throwable) {
        callback.onError(throwable)
    }

    override fun run() {
        repository.findPlacesByMarketId(market.id, this)
    }
}