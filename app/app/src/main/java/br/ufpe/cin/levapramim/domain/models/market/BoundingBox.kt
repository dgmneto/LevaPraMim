package br.ufpe.cin.levapramim.domain.models.market

import br.ufpe.cin.levapramim.domain.models.Coordinates
import java.io.Serializable

class BoundingBox(val upperLeft : Coordinates, val lowerRight : Coordinates) : Serializable {

}