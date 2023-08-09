package com.learner.funzo.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.learner.funzo.FirebaseUtil
import com.learner.funzo.R

class RegisterActivity : AppCompatActivity() {
    companion object {
        private const val TAG: String = "Register Activity"
    }

    var editTextEmail: TextInputEditText? = null
    var editTextPassword: TextInputEditText? = null
    var buttonReg: Button? = null

    var textView: TextView? = null
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
        textView!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })
        try {
            buttonReg!!.setOnClickListener(View.OnClickListener {
                val email: String = editTextEmail!!.getText().toString()
                val password: String = editTextPassword!!.getText().toString()
                // If sign in fails, display a message to the user.
                when {
                    TextUtils.isEmpty(email) -> {
                        Toast.makeText(this@RegisterActivity, "Enter Email", Toast.LENGTH_SHORT).show()
                        return@OnClickListener
                    }
                    TextUtils.isEmpty(password) -> {
                        Toast.makeText(this@RegisterActivity, "Enter password", Toast.LENGTH_SHORT).show()
                        return@OnClickListener
                    }
                    else -> {
                        FirebaseUtil.register(this, email, password)
                        editTextEmail!!.setText("")
                        editTextPassword!!.setText("")
                    }
                }
            })
        } catch (e: Exception) {
            Toast.makeText(
                this@RegisterActivity, "Error " + "While signup",
                Toast.LENGTH_SHORT
            ).show()
            Log.e(TAG, "While signup")
        }
    }
}