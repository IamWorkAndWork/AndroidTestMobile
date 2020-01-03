package app.practice.question3.presentation

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import app.practice.question3.R
import app.practice.question3.model.Restaurant
import app.practice.question3.presentation.menu_list.MenuListFragment
import app.practice.question3.presentation.restaurant_list.RestaurantListFragment

class MainActivity : AppCompatActivity(), RestaurantListFragment.onRestaurantListFragmentListener,
    MenuListFragment.OnMenuFragmentListener {

    val TAG by lazy {
        javaClass.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateTitle(getString(R.string.page_1))

        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RestaurantListFragment.newInstance())
                .commitNow()
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()

        updateTitle(getString(R.string.page_1))

    }

    private fun updateTitle(title: String) {
        setTitle(title)
    }

    override fun onNavigateMenu(restaurant: Restaurant) {
        updateTitle(getString(R.string.page_2))
//        Log.d(TAG, "onNavigateMenu = " + restaurant.toString())
        changeFragment(MenuListFragment.newInstance(restaurant.id.toString()))

    }

    private fun changeFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()

    }

    override fun onFragmentInteraction(uri: Uri) {

    }


}
