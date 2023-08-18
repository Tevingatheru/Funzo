package com.learner.funzo.viewModel

import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText

class LoginActivityViewModel: ViewModel() {

    private lateinit var editTextPassword: TextInputEditText
    private lateinit var editTextEmail: TextInputEditText

    fun setEditTextEmail(editTextEmail: TextInputEditText) {
        this.editTextEmail = editTextEmail
    }

    fun setEditTextPassword(editTextPassword: TextInputEditText) {
        this.editTextPassword = editTextPassword
    }

    fun getEditTextPassword(): String {
        return editTextPassword.text.toString()
    }

    fun getEditTextEmail(): String {
        return editTextEmail.text.toString()
    }
}
