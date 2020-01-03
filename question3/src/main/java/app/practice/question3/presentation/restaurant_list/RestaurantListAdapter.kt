package app.practice.question3.presentation.restaurant_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.practice.question3.R
import app.practice.question3.model.Restaurant
import app.practice.question3.utils.getProgressDrawable
import app.practice.question3.utils.loadImage
import kotlinx.android.synthetic.main.restaurant_list_view_holder.view.*


class RestaurantListAdapter(private var listener: onRestaurantListAdapter) :
    RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>() {

    var restaurants: List<Restaurant?> = arrayListOf()

    fun updateItems(restaurants: List<Restaurant?>) {
        this.restaurants = restaurants
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_list_view_holder, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(position)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        init {
            itemView.cardItem.setOnClickListener {
                val restaurant = restaurants.get(adapterPosition)
                restaurant?.let {
                    listener.onClickRestaurant(it)
                }
            }
        }

        fun bindItem(position: Int) {

            val restaurant = restaurants.get(position)

            itemView.txtRestaurantTitle.text = restaurant?.name
            itemView.txtRestaurantKind.text = restaurant?.kind

            itemView.imgRestaurant.loadImage(
                restaurant?.imageUrl.toString(),
                getProgressDrawable(itemView.context)
            )

        }

    }

}