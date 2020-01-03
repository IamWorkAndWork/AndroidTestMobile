package app.practice.question3.model


import com.google.gson.annotations.SerializedName

data class Menus(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("image_url")
    var imageUrl: String?,
    @SerializedName("kind")
    var kind: String?,
    @SerializedName("menus")
    var menus: List<Menu?>?,
    @SerializedName("name")
    var name: String?
)