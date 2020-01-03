package app.practice.question3.model


import com.google.gson.annotations.SerializedName

data class Restaurants(
    @SerializedName("restaurants")
    var restaurants: List<Restaurant?>?
)