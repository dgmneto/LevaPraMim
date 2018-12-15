package br.ufpe.cin.levapramim.domain.models

import java.io.Serializable

data class Coordinates(
    val latitude: Double?,
    val longitude: Double?)
: Serializable {
    constructor() : this(null, null)
}