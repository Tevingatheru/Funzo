package com.learner.funzo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learner.funzo.viewModel.nav.NavigationHandler
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    fun onPlayButtonClicked(navigationHandler: NavigationHandler) {
        viewModelScope.launch {
            navigationHandler.navigateToSubjectList()
        }
    }
}
