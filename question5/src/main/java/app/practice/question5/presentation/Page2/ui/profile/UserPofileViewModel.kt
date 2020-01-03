package app.practice.question5.presentation.Page2.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.practice.question5.base.BaseViewModel
import app.practice.question5.domain.User
import app.practice.question5.domain.UserProfileUseCase
import kotlinx.coroutines.launch

class UserPofileViewModel(private val useCase: UserProfileUseCase) : BaseViewModel() {

    private val _user = MutableLiveData<User?>()

    fun user(): LiveData<User?> = _user

    fun fetchUserByID(userID: Int) {

        launch {

            try {

                val users = useCase.getUserByID(userID)
                _user.postValue(users)

            } catch (e: Exception) {

            }

        }

    }

    fun deleteUser(user: User?) {

        launch {
            user?.let { useCase.deleteUser(it) }
            _user.postValue(null)
        }

    }

    class Factory(private val useCase: UserProfileUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UserPofileViewModel(useCase) as T
        }
    }

}