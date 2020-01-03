package app.practice.question3.presentation.menu_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.practice.question3.model.MenuItems
import app.practice.question3.model.Restaurant
import app.practice.question3.repository.RestaurantRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MenuListViewModel(private val repo: RestaurantRepository) : ViewModel() {

    //    private val _menus = MutableLiveData<Menus>()
    private val _menuItems = MutableLiveData<List<MenuItems>>()
    private val _loading = MutableLiveData<Boolean>()
    private val _isError = MutableLiveData<Boolean>()

    private var disposable: CompositeDisposable = CompositeDisposable()
    private val TAG by lazy {
        javaClass.simpleName
    }

    fun getMenu(): LiveData<List<MenuItems>> = _menuItems

    fun getLoading(): LiveData<Boolean> = _loading

    fun getIsError(): LiveData<Boolean> = _isError

    fun fetchMenu(restaurantID: String) {
        _loading.value = true

//        val restaurant_ID = restaurantID + ".json"

        Log.d(TAG, "fetchMenu restaurantID = " + restaurantID)
        disposable.add(
            repo.getMenu(restaurantID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ menus ->

                    //                    _menus.value = menus

                    val items = arrayListOf<MenuItems>()

                    val itemHeader = MenuItems(
                        MenuListFragment.HEADER_TYPE,
                        Restaurant(
                            menus?.id, menus?.imageUrl, menus?.kind, menus?.name
                        )
                    )
                    items.add(itemHeader)

                    for (i in menus.menus?.indices!!) {
                        val menu = menus?.menus!!.get(i)
                        val item = MenuItems(MenuListFragment.ITEM_TYPE, menu!!)
                        items.add(item)
                    }

                    _menuItems.value = items
                    _isError.value = false
                    _loading.value = false
                    Log.e(TAG, "fetchMenu success = " + _menuItems.toString())

                }, {
                    _isError.value = true
                    _loading.value = false
                    Log.e(TAG, "fetchMenu error = " + it)

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    class Factory(private val repo: RestaurantRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MenuListViewModel(repo) as T
        }

    }

}