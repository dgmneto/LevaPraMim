package br.ufpe.cin.levapramim.domain.models.trip

import java.io.Serializable

enum class Status : Serializable {
    PENDING,
    PICKED,
    ARRIVED,
    STARTED,
    DONE;
}