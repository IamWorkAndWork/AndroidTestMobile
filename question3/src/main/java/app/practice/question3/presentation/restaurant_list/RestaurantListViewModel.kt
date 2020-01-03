package app.practice.question3.presentation.restaurant_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.practice.question3.model.Restaurants
import app.practice.question3.repository.RestaurantRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RestaurantListViewModel(private val repo: RestaurantRepository) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val _restaurants = MutableLiveData<Restaurants>()
    private val _loadingError = MutableLiveData<Boolean>()
    private val _loadingStatus = MutableLiveData<Boolean>()

    fun getRestaurants(): LiveData<Restaurants> {
        return _restaurants
    }

    fun getLoadingError(): LiveData<Boolean> {
        return _loadingError
    }

    fun getLoadingStatus(): LiveData<Boolean> {
        return _loadingStatus
    }


    fun fetchRestaurants() {

        _loadingError.value = false
        _loadingStatus.value = true;

        disposable.add(
            repo.getRestaurants().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ restaurants ->
                    this._restaurants.value = restaurants
                    _loadingError.value = false
                    _loadingStatus.value = false

                }, {
                    _loadingError.value = true
                    _loadingStatus.value = false
                })
        )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    class Factory(private val repo: RestaurantRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RestaurantListViewModel(repo) as T
        }

    }
}
