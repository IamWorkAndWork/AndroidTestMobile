package app.practice.question3.model


import com.google.gson.annotations.SerializedName

data class Menu(
    @SerializedName("deliver_time")
    var deliverTime: Int?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("image_url")
    var imageUrl: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("price")
    var price: String?,
    @SerializedName("restaurant_id")
    var restaurantId: Int?
)