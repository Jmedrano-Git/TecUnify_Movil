package com.tecsup.tecunify_movil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import com.tecsup.tecunify_movil.feature.auth.AuthViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.tecsup.tecunify_movil.core.navigation.Screen
import com.tecsup.tecunify_movil.core.navigation.TecUnifyNavGraph
import com.tecsup.tecunify_movil.ui.TecUnifyTheme

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var isDarkMode by rememberSaveable { mutableStateOf(false) }

            TecUnifyTheme(darkTheme = isDarkMode){
                val navController = rememberNavController()
                val context = LocalContext.current

                // 1. Configurar GoogleSignInOptions
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

                // 2. Crear el cliente
                val googleSignInClient = GoogleSignIn.getClient(context, gso)

                // 3. Launcher para recibir el resultado
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartActivityForResult()
                ) { result ->
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    try {
                        val account = task.getResult(ApiException::class.java)
                        authViewModel.handleGoogleSignInResult(
                            account = account,
                            onSuccess = {
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(Screen.Login.route) { inclusive = true }
                                }
                            },
                            onError = {
                                // aquí podrías mostrar un Toast/Snackbar si quieres
                            }
                        )
                    } catch (e: ApiException) {
                        authViewModel.handleGoogleSignInResult(
                            account = null,
                            onSuccess = {},
                            onError = {}
                        )
                    }
                }

                Surface {
                    TecUnifyNavGraph(
                        navController = navController,
                        authViewModel = authViewModel,
                        //  AQUÍ se lanza el intent de Google
                        onGoogleSignInClick = {
                            authViewModel.onGoogleSignInClick() // pone Loading
                            launcher.launch(googleSignInClient.signInIntent)
                        },
                        isDarkMode = isDarkMode,
                        onToggleDarkMode = {isDarkMode = it}
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
}