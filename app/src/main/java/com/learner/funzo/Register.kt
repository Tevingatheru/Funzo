package com.learner.funzo

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {
    private val TAG: String? = "Register Activity"
    var editTextEmail: TextInputEditText? = null
    var editTextPassword: TextInputEditText? = null
    var buttonReg: Button? = null
    var mAuth: FirebaseAuth? = null
    var textView: TextView? = null
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
//        if (currentUser != null) {
//            val intent = Intent(applicationContext, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = Firebase.auth
        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        buttonReg = findViewById(R.id.btn_register)
        textView = findViewById(R.id.loginNow)
        textView!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        })
        try {
            buttonReg!!.setOnClickListener(View.OnClickListener {
                val email: String
                val password: String
                email = editTextEmail!!.getText().toString()
                password = editTextPassword!!.getText().toString()
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(this@Register, "Enter Email", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this@Register, "Enter password", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                mAuth!!.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.i(TAG, "createUserWithEmail:success")
                            Toast.makeText(
                                this@Register, "Account created.",
                                Toast.LENGTH_SHORT
                            ).show()
                            editTextEmail!!.setText("")
                            editTextPassword!!.setText("")
                            val intent = Intent(applicationContext, Login::class.java)
                            startActivity(intent)

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "createUserWithEmail:fail")

                            Toast.makeText(
                                this@Register, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            })
        } catch (e: Exception) {
            Toast.makeText(
                this@Register, "Error " + "While signup",
                Toast.LENGTH_SHORT
            ).show()
            Log.e(TAG, "While signup")
        }
    }
}