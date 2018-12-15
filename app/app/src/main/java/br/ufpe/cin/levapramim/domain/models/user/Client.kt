package br.ufpe.cin.levapramim.domain.models.user

import br.ufpe.cin.levapramim.domain.models.User

data class Client(override val id: String?, override val name : String?) : User(UserType.CLIENT, id, name) {
    constructor() : this(null, null)
}