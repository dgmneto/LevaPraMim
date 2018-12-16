package br.ufpe.cin.levapramim.domain.repositories.impl

import br.ufpe.cin.levapramim.domain.models.Place
import br.ufpe.cin.levapramim.domain.repositories.PlaceRepository
import br.ufpe.cin.levapramim.domain.repositories.PlaceRepository.Callback
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class FirebasePlaceRepository(val firebaseFirestore: FirebaseFirestore) : PlaceRepository {
    companion object {
        val COLLECTION_NAME = "places"
    }

    override fun findPlacesByMarketId(marketId: String, callback: Callback) {
        val db = firebaseFirestore.collection(COLLECTION_NAME)
        db
            .whereEqualTo("marketId", marketId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val places = LinkedList<Place>()
                querySnapshot.documents.map { it.toObject(Place::class.java) }
                    .forEach { places.add(it!!) }
                callback.onPlaces(places)
            }
            .addOnFailureListener(callback::onError)
    }

    override fun findPlacesByIds(ids: List<String>, callback: Callback) {
        val db = firebaseFirestore.collection(COLLECTION_NAME)
        val places = ConcurrentLinkedQueue<Place>()
        ids.forEach{
            db.document(it)
                .get()
                .addOnSuccessListener {documentSnapshot ->
                    val place = documentSnapshot.toObject(Place::class.java)
                    places.add(place)
                    if (places.size == ids.size) {
                        callback.onPlaces(places.asSequence().toList())
                    }
                }
                .addOnFailureListener(callback::onError)
        }
    }
}