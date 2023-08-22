package com.learner.funzo.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.learner.funzo.util.FirebaseUtil
import com.learner.funzo.R
import com.learner.funzo.viewModel.RegisterActivityViewModel

class RegisterActivity : AppCompatActivity() {
    companion object {
        private const val TAG: String = "RegisterActivity"
    }

    private val registerActivityViewModel: RegisterActivityViewModel by viewModels()

    public override fun onStart() {
        super.onStart()
        FirebaseUtil.isUserLoggedIn(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        registerActivityViewModel.intiView(
            applicationContext = this,
            buttonReg = findViewById(R.id.btn_register),
            textView = findViewById(R.id.loginNow))
        registerActivityViewModel.setEditTextEmail(findViewById(R.id.email))
        registerActivityViewModel.setEditTextPassword(findViewById(R.id.password))
    }
}
