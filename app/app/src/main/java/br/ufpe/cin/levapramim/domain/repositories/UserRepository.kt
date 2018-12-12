package br.ufpe.cin.levapramim.domain.repositories

import br.ufpe.cin.levapramim.domain.models.User
import br.ufpe.cin.levapramim.domain.models.user.UserType

interface UserRepository {
    interface Callback {
        fun onUser(id : String, type: UserType?, user: User?)
    }
    fun getLoggedUser(callback: Callback)
}