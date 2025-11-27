package com.tecsup.tecunify_movil.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.tecsup.tecunify_movil.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Error(val message: String) : AuthUiState()
    object Success : AuthUiState()
}

class AuthViewModel(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val authState: StateFlow<AuthUiState> = _authState

    val currentUser get() = auth.currentUser

    fun isLoggedIn(): Boolean = currentUser != null

    /**
     * En Compose, este método solo cambia el estado a Loading.
     * El Activity/Composable se encarga de lanzar el intent de Google.
     */
    fun onGoogleSignInClick() {
        _authState.value = AuthUiState.Loading
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthUiState.Idle
    }

    /**
     * Llamar desde el resultado del intent de Google.
     */
    fun handleGoogleSignInResult(
        account: GoogleSignInAccount?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (account == null) {
            _authState.value = AuthUiState.Error("No se pudo obtener la cuenta de Google.")
            onError("No se pudo obtener la cuenta de Google.")
            return
        }

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        viewModelScope.launch {
            _authState.value = AuthUiState.Loading

            auth.signInWithCredential(credential)
                .addOnSuccessListener { result ->
                    val user = result.user
                    val email = user?.email.orEmpty()

                    val isTecsup = email.endsWith("@tecsup.edu.pe")

                    if (!isTecsup) {
                        repository.signOut()
                        _authState.value = AuthUiState.Error(
                            "Solo se permiten cuentas institucionales TECSUP."
                        )
                        onError("Solo se permiten cuentas institucionales TECSUP.")
                        return@addOnSuccessListener
                    }

                    viewModelScope.launch {
                        user?.let { repository.saveUserIfNeeded(it) }
                        _authState.value = AuthUiState.Success
                        onSuccess()
                    }
                }
                .addOnFailureListener { e ->
                    _authState.value = AuthUiState.Error(e.message ?: "Error al iniciar sesión")
                    onError(e.message ?: "Error al iniciar sesión")
                }
        }
    }
}