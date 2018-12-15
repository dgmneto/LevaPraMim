package br.ufpe.cin.levapramim.domain.models

import br.ufpe.cin.levapramim.domain.models.market.BoundingBox
import java.io.Serializable

class Market(val id : String, val name : String, val boundingBox: BoundingBox) : Serializable {
}