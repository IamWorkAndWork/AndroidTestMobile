package app.practice.question5.presentation.Page1

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.practice.question5.base.BaseViewModel
import app.practice.question5.domain.Page1UseCase
import app.practice.question5.domain.User
import kotlinx.coroutines.*

class Page1ViewModel(private val usecase: Page1UseCase) :
    BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    private val _loading = MutableLiveData<Boolean>()
    private val _actionType = MutableLiveData<Int>().apply {
        this.value = Page1Fragment.TYPE_ADD
    }

    fun loading(): LiveData<Boolean> = _loading

    fun users(): LiveData<List<User>> = _users

    fun actionType(): LiveData<Int> = _actionType

    private var user: User? = User(null, "", "", "", "")


    val firstNameTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(text: Editable?) {
            user?.firstName = text.toString()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }

    val lastNameTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(text: Editable?) {
            user?.lastName = text.toString()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }

    fun updateUserID(id: Int?) {
        user?.id = id
    }

    fun updateBirthDate(dbDate: String) {
        user?.birthdate = dbDate.toString()
    }

    fun addUser(onSuccess: (Boolean) -> Unit) {
        _loading.value = true
        launch {
            try {
                val addUserStatus = user?.let { usecase.addUser(it) }
                Log.e("print", "user add success= " + addUserStatus)
                onSuccess(true)

            } catch (e: Exception) {
                Log.e("print", "user add error = " + e)
                onSuccess(false)

            }

            fetchAllUser()

            _loading.postValue(false)
        }
    }


    fun fetchAllUser(): List<User> = runBlocking {
        _loading.postValue(true)

        var users: List<User> = arrayListOf()
        try {
            users = usecase.getAllUsers()
            _users.postValue(users)
            Log.e("print", "getAllUser success = " + users)
        } catch (e: java.lang.Exception) {
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


    fun updateUser(onSuccess: (Boolean) -> Unit) {

        _loading.value = true

        GlobalScope.launch {

            try {
                val res = withContext(Dispatchers.IO) {
                    user?.let { usecase?.updateUser(it) }
                }

                Log.e("print", "updateUser success = " + res + " | " + user.toString())
//                onSuccess(true)

            } catch (e: java.lang.Exception) {
                Log.e("print", "updateUser error = " + e)
//                onSuccess(false)

            }

            _loading.postValue(false)

            fetchAllUser()
        }

        onSuccess(true)

    }

    fun isValidUser(res: (Boolean) -> Unit) {

        if (user?.isValidInput()!!) {
            res(true)
        } else {
            res(false)
        }


    }

    fun setActionType(ACTION_TYPE: Int) {
//        this.ACTION_TYPE = ACTION_TYPE
        this._actionType.value = ACTION_TYPE
    }

//    fun getActionType(): Int {
//        return ACTION_TYPE
//
//    }

    class Factory(private val usecase: Page1UseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return Page1ViewModel(usecase) as T
        }

    }
}
