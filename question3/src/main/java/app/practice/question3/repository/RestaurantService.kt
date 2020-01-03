package app.practice.question3.repository

import app.practice.question3.BuildConfig
import app.practice.question3.model.Menus
import app.practice.question3.model.Restaurants
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantService {

//    private val BASE_URL = "https://order-plz.herokuapp.com/"

    private val api = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(API::class.java)

    fun getRestaurants(): Observable<Restaurants> {
        return api.getRestauants()
    }

    fun getMenus(restaurantId: String): Observable<Menus> {
        return api.getMenus(restaurantId)
    }
}