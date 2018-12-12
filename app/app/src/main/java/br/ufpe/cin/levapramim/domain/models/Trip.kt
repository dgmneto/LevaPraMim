package br.ufpe.cin.levapramim.domain.models

import br.ufpe.cin.levapramim.domain.models.trip.Status

data class Trip(
    val id        : String,
    val marketId  : String,
    val clientId  : String,
    val carrierId : String,
    val fromId    : String,
    val toId      : String,
    val status    : Status)