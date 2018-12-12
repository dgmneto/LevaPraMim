package br.ufpe.cin.levapramim.domain.models.user

import br.ufpe.cin.levapramim.domain.models.User

class Carrier(id : String, name : String) : User(UserType.CARRIER, id, name)