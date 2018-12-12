package br.ufpe.cin.levapramim.domain.models.user

import br.ufpe.cin.levapramim.domain.models.User

class Client(id : String, name : String) : User(UserType.CLIENT, id, name)