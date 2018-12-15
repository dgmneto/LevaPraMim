package br.ufpe.cin.levapramim.domain.models

import br.ufpe.cin.levapramim.domain.models.user.UserType

abstract class User protected constructor(
    val type : UserType,
    open val id : String?,
    open val name : String?)