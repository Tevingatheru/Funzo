package com.learner.funzo.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learner.funzo.viewModel.nav.NavigationHandler
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    fun onPlayButtonClicked(applicationContext: Context) {
        viewModelScope.launch {
            NavigationHandler.navigateToSubjectList(applicationContext = applicationContext)
        }
    }
}
