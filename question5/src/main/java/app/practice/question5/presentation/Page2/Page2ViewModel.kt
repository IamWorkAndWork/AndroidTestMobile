package app.practice.question5.presentation.Page2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.practice.question5.base.BaseViewModel
import app.practice.question5.domain.Page2UseCase
import app.practice.question5.domain.User
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Page2ViewModel(private val usecase: Page2UseCase) : BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    private val _loading = MutableLiveData<Boolean>()

    fun loading(): LiveData<Boolean> = _loading

    fun users(): LiveData<List<User>> = _users

    fun fetchAllUser(): List<User> = runBlocking {
        _loading.postValue(true)

        var users: List<User> = arrayListOf()
        try {
            users = usecase.getAllUsers()
            _users.postValue(users)
            Log.e("print", "getAllUser success = " + users)
        } catch (e: Exception) {
            Log.e("print", "getAllUser error = " + e)
        }

        _loading.postValue(false)

        users

    }


    fun deleteUser(user: User, onSuccess: (Boolean) -> Unit) {
        _loading.value = true

        launch {
            try {
                val res = async {
                    usecase?.deleteUser(user)
                }.await()

                Log.e("print", "deleteUser success = " + res)
                onSuccess(true)

            } catch (e: java.lang.Exception) {
                Log.e("print", "deleteUser error = " + e)
                onSuccess(false)

            }

            _loading.postValue(false)

            fetchAllUser()
        }

    }


    class Factory(private val usecase: Page2UseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return Page2ViewModel(usecase) as T
        }

    }

}