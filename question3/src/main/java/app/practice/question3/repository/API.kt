package app.practice.question3.repository

import app.practice.question3.model.Menus
import app.practice.question3.model.Restaurants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface API {

    //    https://order-plz.herokuapp.com/restaurants.json
    @GET("restaurants.json")
    fun getRestauants(): Observable<Restaurants>

    //    https://order-plz.herokuapp.com/restaurants/1.json
    @GET("restaurants/{restaurant_id}.json")
    fun getMenus(@Path(value = "restaurant_id") restaurant_id: String): Observable<Menus>
}