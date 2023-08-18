package com.learner.funzo.viewModel

import android.widget.Button
import android.widget.TextView
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
