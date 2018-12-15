package br.ufpe.cin.levapramim.domain.models.user

import br.ufpe.cin.levapramim.domain.models.User

data class Carrier(override val id : String?, override val name : String?) : User(UserType.CARRIER, id, name) {
    constructor() : this(null, null)
}