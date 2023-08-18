package com.learner.funzo.view

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.learner.funzo.util.FirebaseUtil
import com.learner.funzo.R
import com.learner.funzo.viewModel.LoginActivityViewModel
import com.learner.funzo.viewModel.nav.NavigationHandler

class LoginActivity : AppCompatActivity() , View.OnClickListener{

    private var buttonLogin: Button? = null
    private var textView: TextView? = null
    private val loginActivityViewModel: LoginActivityViewModel by viewModels()

    companion object{
        const val TAG: String = "LoginActivity"
    }

    public override fun onStart() {
        super.onStart()
        FirebaseUtil.isUserLoggedIn(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initializeLoginView()
        textView!!.setOnClickListener(this)

        buttonLogin!!.setOnClickListener(this)
    }

    private fun initializeLoginView() {
        loginActivityViewModel.setEditTextEmail(findViewById(R.id.email))
        loginActivityViewModel.setEditTextPassword(findViewById(R.id.password))
        buttonLogin = findViewById(R.id.btn_login)
        textView = findViewById(R.id.registerNow)
    }

    override fun onClick(p0: View?) {
        when(p0) {
            buttonLogin -> {
                val email: String = loginActivityViewModel.getEditTextEmail()
                val password: String = loginActivityViewModel.getEditTextPassword()
                when {
                    TextUtils.isEmpty(email) -> {
                        Toast.makeText(this@LoginActivity, "Enter Email", Toast.LENGTH_SHORT).show()
                    }
                    TextUtils.isEmpty(password) -> {
                        Toast.makeText(this@LoginActivity, "Enter password", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        FirebaseUtil.login(this, email, password)
                    }
                }
            }
            textView -> {
                NavigationHandler.navigateToRegistrationActivity(applicationContext = this)
                finish()
            }
        }
    }
}
