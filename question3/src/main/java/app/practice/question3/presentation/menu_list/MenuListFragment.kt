package app.practice.question3.presentation.menu_list

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import app.practice.question3.R
import app.practice.question3.model.Menu
import app.practice.question3.repository.RestaurantRepository
import app.practice.question3.repository.RestaurantService
import kotlinx.android.synthetic.main.fragment_menu_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_RESTAURANT_ID = "RESTAURANT_ID"

class MenuListFragment : Fragment() {
    private val TAG by lazy {
        javaClass.simpleName
    }
    private var restaurantID: String? = null
    private var listener: OnMenuFragmentListener? = null

    lateinit var viewModel: MenuListViewModel
    lateinit var mAdapter: MenuListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            restaurantID = it.getString(ARG_RESTAURANT_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val service = RestaurantService()
        val repo = RestaurantRepository(service)

        viewModel = ViewModelProviders.of(this, MenuListViewModel.Factory(repo))
            .get(MenuListViewModel::class.java)

        initWidget()
        initListener()
        observeViewModel()

    }

    private fun observeViewModel() {

        viewModel.getMenu().observe(this, Observer {
            it?.let { menus ->
                Log.d(TAG, "getMenu = " + menus.toString())
                mAdapter.updateItems(menus)
            }

        })


        viewModel.getIsError().observe(this, Observer { isError ->
            if (isError) {
                Toast.makeText(activity!!, "Error Fetching Data", Toast.LENGTH_LONG).show()
            }
        })


        viewModel.getLoading().observe(this, Observer { isLoading ->
            if (isLoading) {
                menuListProgressBar.visibility = View.VISIBLE
            } else {
                menuListProgressBar.visibility = View.GONE
            }
            menuListSwipe.isRefreshing = isLoading

        })

    }

    private fun initListener() {

        menuListSwipe.setOnRefreshListener {
            restaurantID?.let { viewModel.fetchMenu(it) }
        }

    }

    private fun initWidget() {

        mAdapter = MenuListAdapter(object : MenuListAdapter.onMenuListAdapterListener {
            override fun onClickedMenu(menu: Menu) {

                Toast.makeText(activity, "Click at : " + menu.name, Toast.LENGTH_SHORT).show()

            }

        })
        menuListRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }

    }

    override fun onResume() {
        super.onResume()

        restaurantID?.let {
            viewModel.fetchMenu(it)
        }

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMenuFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnMenuFragmentListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        const val HEADER_TYPE = 1
        const val ITEM_TYPE = 2

        @JvmStatic
        fun newInstance(restaurantID: String) =
            MenuListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_RESTAURANT_ID, restaurantID)
                }
            }
    }
}
