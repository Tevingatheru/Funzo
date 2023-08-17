package com.learner.funzo.view

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.learner.funzo.util.FirebaseUtil
import com.learner.funzo.R
import com.learner.funzo.viewModel.RegisterActivityViewModel
import com.learner.funzo.viewModel.nav.NavigationHandler

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        private const val TAG: String = "Register Activity"
    }

    private var editTextEmail: TextInputEditText? = null
    private var editTextPassword: TextInputEditText? = null
    private var buttonReg: Button? = null
    private var textView: TextView? = null
    private val registerActivityViewModel: RegisterActivityViewModel by viewModels()

    public override fun onStart() {
        super.onStart()
        FirebaseUtil.isUserLoggedIn(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        buttonReg = findViewById(R.id.btn_register)
        textView = findViewById(R.id.loginNow)
        textView!!.setOnClickListener(this)
        try {
            buttonReg!!.setOnClickListener(this)
        } catch (e: Exception) {
            Toast.makeText(
                this@RegisterActivity, "Error " + "While signup",
                Toast.LENGTH_SHORT
            ).show()
            Log.e(TAG, "While signup")
        }
    }

    override fun onClick(view: View?) {
        when(view) {
            textView -> {
                NavigationHandler.navigateToLoginActivity(applicationContext = this)
                finish()
            }
            buttonReg -> {
                val email: String = editTextEmail!!.getText().toString()
                val password: String = editTextPassword!!.getText().toString()

                when {
                    TextUtils.isEmpty(email) -> {
                        Toast.makeText(this@RegisterActivity, "Enter Email", Toast.LENGTH_SHORT).show()
                    }
                    TextUtils.isEmpty(password) -> {
                        Toast.makeText(this@RegisterActivity, "Enter password", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        FirebaseUtil.register(this, email, password)
                        editTextEmail!!.setText("")
                        editTextPassword!!.setText("")
                    }
                }
            }
        }
    }
}
