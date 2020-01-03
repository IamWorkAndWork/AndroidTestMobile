package app.practice.question2.Page1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.practice.question2.`interface`.UserCallBack

class Page1ViewModelFactory(private var listener: UserCallBack) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return Page1ViewModel(listener) as T
    }
}