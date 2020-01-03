package app.practice.question5.presentation.Page1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.practice.question5.R
import app.practice.question5.domain.User
import app.practice.question5.utils.DateUtils
import app.practice.question5.utils.getProgressDrawable
import app.practice.question5.utils.loadImage
import kotlinx.android.synthetic.main.vh_page1.view.*

class Page1Adapter(private val listener: onPage1AdapterListener) :
    RecyclerView.Adapter<Page1Adapter.ViewHolder>() {

    private var users: List<User> = arrayListOf()

    interface onPage1AdapterListener {
        fun onClickedItem(user: User)
//        fun onClickedNavigateItem(user: User)
    }

    fun updateItems(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.vh_page1, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users.get(position)
        holder.bindItem(user)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.cardItem.setOnClickListener {
//                val user = users.get(adapterPosition)
//                listener.onClickedNavigateItem(user)
                val user = users.get(adapterPosition)
                listener.onClickedItem(user)
            }

            itemView.btMore.setOnClickListener {
                val user = users.get(adapterPosition)
                listener.onClickedItem(user)
            }
        }

        fun bindItem(user: User) {

            itemView.imgPicture.loadImage(user.picture, getProgressDrawable(itemView.context));

            itemView.txtFullName.text = user.firstName + " " + user.lastName
            itemView.txtBirthDate.text = DateUtils.dateDBToAppFormat(user.birthdate)


        }

    }


}