package app.practice.question5.presentation.Page2

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import app.practice.question5.R
import app.practice.question5.domain.Page2UseCase
import app.practice.question5.model.DBUserMapper
import app.practice.question5.model.UserDatabase
import app.practice.question5.model.UserRepositoryImpl
import app.practice.question5.presentation.Page2.ui.profile.SectionsPagerAdapter
import app.practice.question5.presentation.Page2.ui.profile.UserProfileFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_page2.*

class Page2Activity : AppCompatActivity(), UserProfileFragment.onUserProfileFragmentListener {

    private lateinit var viewModel: Page2ViewModel
    var viewPager: ViewPager? = null
    var tabs: TabLayout? = null
    var sectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page2)

        val db = UserDatabase.getInstance(this)?.userDao()
        val mapper = DBUserMapper()
        val repo = UserRepositoryImpl(db!!, mapper)
        val useCase = Page2UseCase(repo)

        viewModel = ViewModelProviders.of(this, Page2ViewModel.Factory(useCase))
            .get(Page2ViewModel::class.java)

        initWidget()
        obsereViewModel()

    }

    override fun onResume() {
        super.onResume()

        viewModel.fetchAllUser()
    }

    private fun initWidget() {

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewPager = findViewById(R.id.view_pager)
        tabs = findViewById(R.id.tabs)

        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager?.adapter = sectionsPagerAdapter
        tabs?.setupWithViewPager(viewPager)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun obsereViewModel() {

        viewModel.users().observe(this, Observer { users ->

            sectionsPagerAdapter?.updateUser(users)

        })

        viewModel.loading().observe(this, Observer { status ->
            if (status) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })

    }

    override fun onDeleteUser() {
//        Log.e("print", "onDeleteUser call")
        viewModel.fetchAllUser()
    }
}