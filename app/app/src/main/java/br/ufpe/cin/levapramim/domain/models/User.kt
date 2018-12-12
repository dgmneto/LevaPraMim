package br.ufpe.cin.levapramim.domain.models

import br.ufpe.cin.levapramim.domain.models.user.UserType

abstract class User protected constructor(
    val type : UserType,
    val id : String,
    val name : String)