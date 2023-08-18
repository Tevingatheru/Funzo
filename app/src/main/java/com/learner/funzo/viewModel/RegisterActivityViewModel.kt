package com.learner.funzo.viewModel

import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText

class RegisterActivityViewModel: ViewModel() {
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText


    fun setEditTextEmail(email: TextInputEditText) {
        this.editTextEmail = email
    }

    fun setEditTextPassword(password: TextInputEditText) {
        this.editTextPassword = password
    }

    fun getEditTextEmail(): String {
        return this.editTextEmail.text.toString()
    }

    fun getEditTextPassword(): String {
        return this.editTextPassword.text.toString()
    }
}
