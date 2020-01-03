package app.practice.question3.repository

import app.practice.question3.model.Menus
import app.practice.question3.model.Restaurants
import io.reactivex.Observable

class RestaurantRepository(private val service: RestaurantService) {

    fun getRestaurants(): Observable<Restaurants> {
        return service.getRestaurants()
    }

    fun getMenu(restaurantId: String): Observable<Menus> {
        return service.getMenus(restaurantId)
    }

}