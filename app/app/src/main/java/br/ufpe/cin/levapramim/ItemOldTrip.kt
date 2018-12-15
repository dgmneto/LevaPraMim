package br.ufpe.cin.levapramim

class ItemOldTrip(val name: String, val origin: String, val destiny: String, val date: String, val value: Float) {

    override fun toString(): String {
        return name
    }
}