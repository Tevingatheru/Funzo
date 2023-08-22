package com.learner.funzo.viewModel

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText
import com.learner.funzo.model.retrofit.ClientGenerator
import com.learner.funzo.model.retrofit.UserClient
import com.learner.funzo.model.retrofit.UserClientRepository
import com.learner.funzo.model.retrofit.request.CreateUserRequest
import com.learner.funzo.util.FirebaseUtil
import com.learner.funzo.view.activity.RegisterActivity
import com.learner.funzo.viewModel.nav.NavigationHandler
import kotlinx.coroutines.runBlocking

class RegisterActivityViewModel: ViewModel(), OnClickListener {
    private var applicationContext: Context? = null
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private var buttonReg: Button? = null
    private var textView: TextView? = null

    fun setEditTextEmail(email: TextInputEditText) {
        this.editTextEmail = email
    }

    fun setEditTextPassword(password: TextInputEditText) {
        this.editTextPassword = password
    }

    private fun getEditTextEmail(): String {
        return this.editTextEmail.text.toString()
    }

    private fun getEditTextPassword(): String {
        return this.editTextPassword.text.toString()
    }

    override fun onClick(view: View?) {
        when(view) {
            textView -> {
                NavigationHandler.navigateToLoginActivity(applicationContext = applicationContext!!)
            }
            buttonReg -> {
                val email: String = this.getEditTextEmail()
                val password: String = this.getEditTextPassword()

                when {
                    TextUtils.isEmpty(email) -> {
                        Toast.makeText(applicationContext!!, "Enter Email", Toast.LENGTH_SHORT).show()
                    }
                    TextUtils.isEmpty(password) -> {
                        Toast.makeText(applicationContext!!, "Enter password", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        FirebaseUtil.register(applicationContext!!, email, password)
                        runBlocking {
                            val userClientRepository: UserClientRepository = UserClientRepository(
                                ClientGenerator
                                .createClient(UserClient::class.java))
                            userClientRepository.createUser(CreateUserRequest(email = email))
                        }
                        clearRegistrationForm()
                    }
                }
            }
        }
    }

    private fun clearRegistrationForm() {
        editTextEmail.setText("")
        editTextPassword.setText("")
    }

    fun intiView(buttonReg: Button, textView: TextView, applicationContext: RegisterActivity) {
        this.textView = textView
        this.buttonReg = buttonReg
        this.textView!!.setOnClickListener(this)
        this.applicationContext = applicationContext
        try {
            buttonReg.setOnClickListener(this)
        } catch (e: Exception) {
            Toast.makeText(
                this.applicationContext, "Error While signup",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
