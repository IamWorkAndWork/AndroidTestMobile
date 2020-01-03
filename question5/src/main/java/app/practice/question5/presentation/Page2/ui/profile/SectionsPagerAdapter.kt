package app.practice.question5.presentation.Page2.ui.profile

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import app.practice.question5.domain.User


class SectionsPagerAdapter(
    private val context: Context,
    fm: FragmentManager

) :
    FragmentPagerAdapter(fm) {

    private var users: List<User> = arrayListOf()

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        val userID = users.get(position).id
        return UserProfileFragment.newInstance(userID)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val title = users.get(position).firstName
        return title//context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return users.size
    }

    fun updateUser(users: List<User>?) {
        if (users != null) {
            this.users = users
        }
        notifyDataSetChanged()
    }
}