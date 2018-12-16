package br.ufpe.cin.levapramim.domain.repositories.impl

import android.app.Activity
import br.ufpe.cin.levapramim.domain.models.Trip
import br.ufpe.cin.levapramim.domain.models.trip.Status
import br.ufpe.cin.levapramim.domain.repositories.TripRepository
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import java.lang.RuntimeException
import java.util.*

class FirebaseTripRepository (val firebaseFirestore : FirebaseFirestore, val activity : Activity) : TripRepository {
    companion object {
        private val COLLECTION_NAME = "trips"
    }

    override fun findTripsByMarketIdAndStatus(
        marketId: String,
        status: Status,
        callback: TripRepository.Callback) {
        val db = firebaseFirestore.collection(COLLECTION_NAME)
        val onEventListener = EventListener<QuerySnapshot> { snapshot, exception ->
            if (exception != null) {
                callback.onError(exception)
                return@EventListener
            }

            if (snapshot != null) {
                snapshot.documentChanges.forEach {documentChange ->
                    val document = documentChange.document
                    val id = document.id
                    document.toObject(Trip::class.java)
                    val trip = when (documentChange.type) {
                        DocumentChange.Type.ADDED, DocumentChange.Type.MODIFIED ->
                            document.toObject(Trip::class.java)
                        DocumentChange.Type.REMOVED -> null
                    }
                    callback.onTrip(id, trip)
                }
            }
        }

        db.whereEqualTo("market_id", marketId)
            .whereEqualTo("status", status.toString())
            .addSnapshotListener(activity, onEventListener)
    }

    override fun findTripsByUserId(userId : String, callback: TripRepository.Callback){
        val db = firebaseFirestore.collection(COLLECTION_NAME)
        val client  = db.whereEqualTo("clientId", userId).get()
        val carrier = db.whereEqualTo("carrierId", userId).get()
        val mergedTasks = Tasks.whenAll(client, carrier)
        mergedTasks.addOnFailureListener(callback::onError)
        mergedTasks.addOnSuccessListener {
            val clientResult = client.result
            val carrierResult = carrier.result
            val result = LinkedList<Trip>()
            getTripsInSnapshot(clientResult).forEach {trip -> result.add(trip)}
            getTripsInSnapshot(carrierResult).forEach {trip -> result.add(trip)}
            result.forEach{trip -> callback.onTrip(trip.id!!, trip)}
            callback.onTrips(result)
        }
    }


    override fun updateTripStatus(
        tripId: String,
        from: Status,
        to: Status,
        callback: TripRepository.Callback) {
        val docRef = firebaseFirestore.collection(COLLECTION_NAME).document(tripId.toString())
        firebaseFirestore
            .runTransaction { transaction ->
                val fromTrip = transaction.get(docRef).toObject(Trip::class.java)
                if (fromTrip != null && fromTrip.status!!.equals(from)) {
                    val toTrip = fromTrip.copy(status = to)
                    transaction.set(docRef, toTrip)
                    return@runTransaction toTrip
                }
                throw RuntimeException("Couldn't update trip status")
            }
            .addOnSuccessListener { trip : Trip ->
                callback.onTrip(trip.id!!, trip)
            }
            .addOnFailureListener { throwable ->
                callback.onError(throwable)
            }
    }

    override fun createTripAndSubscribeToUpdates(
        trip: Trip,
        callback: TripRepository.Callback
    ) {
        val db = firebaseFirestore.collection(COLLECTION_NAME)
        val docRef = db.document()
        val trip = trip.copy(id = docRef.id)
        docRef.set(trip)
            .addOnSuccessListener {
                callback.onTrip(trip.id!!, trip)
                subscribeToUpdates(docRef, callback)
            }
            .addOnFailureListener(callback::onError)

    }

    override fun subscribeToUpdates(trip: Trip, callback: TripRepository.Callback) {
        val db = firebaseFirestore.collection(COLLECTION_NAME)
        val docRef = db.document(trip.id!!)
        subscribeToUpdates(docRef, callback)
    }

    private fun subscribeToUpdates(trip : DocumentReference, callback: TripRepository.Callback) {
        val onEventListener = EventListener<DocumentSnapshot> { docSnapshot, exception ->
            if (exception != null) {
                callback.onError(exception)
                return@EventListener
            }

            val trip = docSnapshot!!.toObject(Trip::class.java)
            callback.onTrip(trip?.id!!, trip)
        }
        trip.addSnapshotListener(onEventListener)
    }

    private fun getTripsInSnapshot(querySnapshot: QuerySnapshot?) : Iterable<Trip> {
        if (querySnapshot == null) {
            return Collections.emptyList()
        }

        return querySnapshot.documents.map { it.toObject(Trip::class.java) }
            .filter { it != null }
            .map { it!! }
    }
}