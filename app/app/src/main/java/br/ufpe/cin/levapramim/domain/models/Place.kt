package br.ufpe.cin.levapramim.domain.models

data class Place(
    val id : String,
    val coordinates: Coordinates,
    val name : String,
    val marketId : String)