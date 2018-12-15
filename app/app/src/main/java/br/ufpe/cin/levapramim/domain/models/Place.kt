package br.ufpe.cin.levapramim.domain.models

import br.ufpe.cin.levapramim.domain.models.place.PlaceType
import java.io.Serializable

data class Place(
    val id : String?,
    val coordinates: Coordinates?,
    val name : String?,
    val marketId : String?,
    val type : PlaceType?)
: Serializable {
    constructor() : this(null, null, null, null, null)

    override fun toString(): String {
        return name ?: ""
    }
}