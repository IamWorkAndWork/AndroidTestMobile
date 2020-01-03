package app.practice.question3.presentation.restaurant_list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import app.practice.question3.R
import app.practice.question3.model.Restaurant
import app.practice.question3.repository.RestaurantRepository
import app.practice.question3.repository.RestaurantService
import kotlinx.android.synthetic.main.restaurant_list_fragment.*

class RestaurantListFragment : Fragment(), onRestaurantListAdapter {

    companion object {
        fun newInstance() = RestaurantListFragment()
    }

    val TAG by lazy { javaClass.simpleName }

    private lateinit var viewModel: RestaurantListViewModel
    lateinit var mAdapter: RestaurantListAdapter

    interface onRestaurantListFragmentListener {
        fun onNavigateMenu(restaurant: Restaurant)
    }

    private var listener: onRestaurantListFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.restaurant_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val service = RestaurantService()
        val repo = RestaurantRepository(service)

        viewModel = ViewModelProviders.of(this, RestaurantListViewModel.Factory(repo))
            .get(RestaurantListViewModel::class.java)

        initWidget();
        initListener()
        observeViewModel()
    }

    private fun observeViewModel() {

        viewModel.fetchRestaurants()

        viewModel.getRestaurants().observe(this, Observer { restaurantsData ->
            restaurantsData?.restaurants?.let { restaurants ->
                //                Log.d(TAG, "getRestaurants = ${it.toString()}")
                mAdapter.updateItems(restaurants)
            }

        })

        viewModel.getLoadingError().observe(this, Observer { isError ->
            Log.d(TAG, "getLoadingError = ${isError.toString()}")

            if (isError) {
                restaurantListTxtError.visibility = View.VISIBLE
            } else {
                restaurantListTxtError.visibility = View.GONE
            }

        })

        viewModel.getLoadingStatus().observe(this, Observer { loading ->
            Log.d(TAG, "getLoadingStatus = ${loading.toString()}")

            if (loading) {
                restaurantListProgressBar.visibility = View.VISIBLE
            } else {
                restaurantListProgressBar.visibility = View.GONE
            }

            restaurantListSwipe.isRefreshing = loading


        })


    }

    private fun initListener() {

    }

    private fun initWidget() {

        mAdapter = RestaurantListAdapter(this)
        restaurantListRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }


        restaurantListSwipe.setOnRefreshListener {
            viewModel.fetchRestaurants()
        }

    }

    override fun onClickRestaurant(restaurant: Restaurant) {

        listener?.onNavigateMenu(restaurant)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is onRestaurantListFragmentListener) {
            listener = context
        } else {
            throw RuntimeException("should imolement onRestaurantListFragment")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

}
