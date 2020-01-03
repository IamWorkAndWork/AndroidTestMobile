package app.practice.question3.presentation.menu_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.practice.question3.R
import app.practice.question3.model.Menu
import app.practice.question3.model.MenuItems
import app.practice.question3.model.Restaurant
import app.practice.question3.utils.getProgressDrawable
import app.practice.question3.utils.loadImage
import app.practice.question3.utils.toDecimalFormat
import kotlinx.android.synthetic.main.menu_list_view_holder.view.*
import kotlinx.android.synthetic.main.restaurant_list_view_holder.view.*

class MenuListAdapter(private val listener: onMenuListAdapterListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<MenuItems> = arrayListOf()

    //    private var listener:
    interface onMenuListAdapterListener {
        fun onClickedMenu(menu: Menu)
    }

    fun updateItems(items: List<MenuItems>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val item = items.get(position).viewType
        return item
//        return super.getItemViewType(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            MenuListFragment.HEADER_TYPE -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.restaurant_list_view_holder, parent, false)
                return HeaderMenuVH(v)
            }
            else -> {//            MenuListFragment.ITEM_TYPE -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.menu_list_view_holder, parent, false)
                return ItemMenuVH(v)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items.get(position)
        when (item.viewType) {
            MenuListFragment.HEADER_TYPE -> {
                (holder as HeaderMenuVH).bindItem((item.item as Restaurant))
            }
            else -> {
                (holder as ItemMenuVH).bindItem((item.item as Menu))
            }
        }
    }

    inner class HeaderMenuVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(restaurant: Restaurant) {

            itemView.txtRestaurantTitle.text = restaurant?.name
            itemView.txtRestaurantKind.text = restaurant?.kind

            itemView.imgRestaurant.loadImage(
                restaurant?.imageUrl.toString(),
                getProgressDrawable(itemView.context)
            )

        }

    }

    inner class ItemMenuVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {

            itemView.cardItemMenu.setOnClickListener {
                val item = items.get(adapterPosition)
                val itemMenu = item.item as Menu?
//                Log.d("print", "click card = " + itemMenu.toString())
                if (itemMenu != null) {
                    listener.onClickedMenu(itemMenu)
                }
            }

        }

        fun bindItem(menu: Menu) {

            itemView.txtRestaurantItemTitle.text = menu?.name
            itemView.txtRestaurantItemPrice.text =
                String.format(
                    itemView.context.getString(R.string.price_item),
                    menu?.price.toDecimalFormat()
                )

            itemView.imgItemRestaurant.loadImage(
                menu?.imageUrl.toString(),
                getProgressDrawable(itemView.context)
            )

        }

    }
}