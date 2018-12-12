package br.ufpe.cin.levapramim.domain.repositories

import br.ufpe.cin.levapramim.domain.models.Place

interface PlaceRepository {
    interface Callback {
        fun onPlaces(places : List<Place>)

        fun onError(throwable: Throwable)
    }

    fun findPlacesByMarketId(marketId : String, callback: Callback)
}