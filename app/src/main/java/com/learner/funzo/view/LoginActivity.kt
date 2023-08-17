package com.learner.funzo.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.learner.funzo.util.FirebaseUtil
import com.learner.funzo.R

class LoginActivity : AppCompatActivity() {
    var editTextEmail: TextInputEditText? = null
    var editTextPassword: TextInputEditText? = null
    var buttonLogin: Button? = null
    var textView: TextView? = null

    companion object{
        const val TAG: String = "Login"
    }

    public override fun onStart() {
        super.onStart()
        FirebaseUtil.isUserLoggedIn(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        buttonLogin = findViewById(R.id.btn_login)
        textView = findViewById(R.id.registerNow)
        textView!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        })

        buttonLogin!!.setOnClickListener(View.OnClickListener {
            val email: String = editTextEmail!!.getText().toString()
            val password: String = editTextPassword!!.getText().toString()
            when {
                TextUtils.isEmpty(email) -> {
                    Toast.makeText(this@LoginActivity, "Enter Email", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                TextUtils.isEmpty(password) -> {
                    Toast.makeText(this@LoginActivity, "Enter password", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                else -> {
                    FirebaseUtil.login(this, email, password)
                }
            }
        })
    }

}