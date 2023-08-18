package com.learner.funzo.util

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.learner.funzo.model.retrofit.BackendClientGenerator
import com.learner.funzo.model.retrofit.UserClient
import com.learner.funzo.model.retrofit.UserClientImpl
import com.learner.funzo.model.retrofit.request.CreateUserRequest
import kotlinx.coroutines.runBlocking
import com.learner.funzo.viewModel.nav.NavigationHandler

class FirebaseUtil {

    companion object {
        private var auth = FirebaseAuth.getInstance()

        fun logout(applicationContext: Context) {
            auth.signOut()
            NavigationHandler.navigateToLoginActivity(applicationContext)
        }

        fun isUserLoggedIn(applicationContext: Context) {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                NavigationHandler.navigateToMainActivity(applicationContext = applicationContext)
            }
        }

        fun register(applicationContext: Context, email: String, password: String) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userClientImpl: UserClientImpl = UserClientImpl(BackendClientGenerator
                            .createClient(UserClient::class.java))
                        runBlocking {
                            userClientImpl.createUser(CreateUserRequest(email = email))
                        }

                        Toast.makeText(
                            applicationContext, "Account created.",
                            Toast.LENGTH_SHORT
                        ).show()

                        NavigationHandler.navigateToLoginActivity(applicationContext = applicationContext)
                    } else {
                        Toast.makeText(
                            applicationContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        fun login(applicationContext: Context, email: String, password: String) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            applicationContext, "Login Successful.",
                            Toast.LENGTH_SHORT
                        ).show()
                        NavigationHandler.navigateToMainActivity(applicationContext = applicationContext)
                    } else {
                        Toast.makeText(
                            applicationContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
