package com.tecsup.tecunify_movil.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.tecsup.tecunify_movil.data.model.User
import kotlinx.coroutines.tasks.await


class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    suspend fun saveUserIfNeeded(user: FirebaseUser) {
        val docRef = firestore.collection("users").document(user.uid)
        val snapshot = docRef.get().await()

        if (!snapshot.exists()) {
            val userModel = User(
                uid = user.uid,
                displayName = user.displayName.orEmpty(),
                email = user.email.orEmpty(),
                photoUrl = user.photoUrl?.toString().orEmpty()
            )
            docRef.set(userModel).await()
        }
    }

    fun signOut() {
        auth.signOut()
    }
}