package br.ufpe.cin.levapramim.domain.repositories.impl

import br.ufpe.cin.levapramim.domain.models.user.Carrier
import br.ufpe.cin.levapramim.domain.models.user.Client
import br.ufpe.cin.levapramim.domain.models.user.UserType
import br.ufpe.cin.levapramim.domain.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.RuntimeException

class FirebaseUserRepository(
        val firebaseAuth: FirebaseAuth,
        val firebaseFirestore: FirebaseFirestore) : UserRepository {
    companion object {
        val COLLECTION_NAME = "users"
    }

    override fun getLoggedUser(callback: UserRepository.Callback) {
        val uid = firebaseAuth.currentUser!!.uid
        val docRef = firebaseFirestore.collection(COLLECTION_NAME).document(uid)
        docRef.get()
            .addOnSuccessListener {
                val type = it.getString("type")
                val (userType, user) = when (type) {
                    UserType.CLIENT.toString() -> Pair(UserType.CLIENT, it.toObject(Client::class.java))
                    UserType.CARRIER.toString() -> Pair(UserType.CARRIER, it.toObject(Carrier::class.java))
                    else -> throw RuntimeException("Invalid user type")
                }
                callback.onUser(user!!.id, userType, user)
            }
    }
}