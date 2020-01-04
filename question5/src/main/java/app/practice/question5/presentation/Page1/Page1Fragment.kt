package app.practice.question5.presentation.Page1

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import app.practice.question5.R
import app.practice.question5.domain.Page1UseCase
import app.practice.question5.domain.User
import app.practice.question5.model.DBUserMapper
import app.practice.question5.model.UserDatabase
import app.practice.question5.model.UserRepositoryImpl
import app.practice.question5.presentation.Page2.Page2Activity
import app.practice.question5.utils.DateUtils
import kotlinx.android.synthetic.main.page1_fragment.*
import java.util.*


class Page1Fragment : Fragment(), Page1Adapter.onPage1AdapterListener {

    companion object {
        const val TYPE_ADD = 1
        const val TYPE_EDIT = 2

        fun newInstance() = Page1Fragment()
    }

    val TAG by lazy { javaClass.simpleName }

    private lateinit var viewModel: Page1ViewModel
    private lateinit var mAdapter: Page1Adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page1_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val db = UserDatabase.getInstance(activity!!)?.userDao()
        val mapper = DBUserMapper()
        val repo = UserRepositoryImpl(db!!, mapper)
        val useCase = Page1UseCase(repo)

        viewModel =
            ViewModelProviders.of(this, Page1ViewModel.Factory(useCase))
                .get(Page1ViewModel::class.java)

        initWidget()
        initListener()
        observeViewModel()


    }

    private fun initListener() {

        btNext.setOnClickListener {
            if (viewModel.users().value!!.size == 0) {

                Toast.makeText(activity, getString(R.string.invalid_no_users), Toast.LENGTH_SHORT)
                    .show()

            } else {

                val intent = Intent(activity, Page2Activity::class.java)
                startActivity(intent)

            }

        }

        btCancel.setOnClickListener {
            clearToDefault()
        }

        btAdd.setOnClickListener {

            when (viewModel.actionType().value) {
                TYPE_ADD -> {
                    addUserToDB()
                }
                TYPE_EDIT -> {
                    updateUserToDB()
                }
            }

        }

        btDate.setOnClickListener {
            val c: Calendar = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR)
            val mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                activity!!,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    val date =
                        year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toShort()

                    val dbDate = DateUtils.stringToDBFormat(date)

                    Log.e(TAG, "date select = " + date + " | " + dbDate)

                    viewModel.updateBirthDate(dbDate)
                    btDate.text = DateUtils.dateDBToAppFormat(dbDate)

                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }

    }


    private fun updateUserToDB() {
        viewModel.isValidUser { staus ->
            if (staus) {
                viewModel.updateUser() { status ->
                    if (status) {
                        Toast.makeText(
                            activity,
                            getString(R.string.update_user_success),
                            Toast.LENGTH_SHORT
                        ).show()

                        clearToDefault()

                    } else {
                        Toast.makeText(
                            activity,
                            getString(R.string.update_user_failed),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                }
            } else {
                Toast.makeText(
                    activity,
                    getString(R.string.invalid_input),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun addUserToDB() {

        viewModel.isValidUser() { status ->
            if (status) {
                viewModel.addUser() { status ->
                    if (status) {

                        Toast.makeText(
                            activity,
                            getString(R.string.add_user_success),
                            Toast.LENGTH_SHORT
                        ).show()

                        clearToDefault()

                    } else {
                        Toast.makeText(
                            activity,
                            getString(R.string.add_user_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    activity,
                    getString(R.string.invalid_input),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    private fun clearToDefault() {

        editTxtFirstName.setText("")
        editTxtLastName.setText("")
        btDate.text = getString(R.string.date_of_birth)
        viewModel.updateBirthDate("")
        viewModel.updateUserID(null)
        viewModel.setActionType(TYPE_ADD)
        btAdd.text = getString(R.string.add)

    }

    private fun observeViewModel() {

        viewModel.actionType().observe(this, Observer { actionType ->
            when (actionType) {
                TYPE_ADD -> {
                    btCancel.visibility = View.GONE
                }
                TYPE_EDIT -> {
                    btCancel.visibility = View.VISIBLE
                }
            }
        })

        viewModel.users().observe(this, Observer {
            mAdapter.updateItems(it)
        })

        viewModel.loading().observe(this, Observer {

                status ->
            if (status) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }

        })

    }

    override fun onResume() {
        super.onResume()

        viewModel.fetchAllUser()
    }

    private fun initWidget() {

        editTxtFirstName.addTextChangedListener(viewModel.firstNameTextWatcher)
        editTxtLastName.addTextChangedListener(viewModel.lastNameTextWatcher)

        mAdapter = Page1Adapter(this)

        recViewUser.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }
    }


    private fun deleteUser(user: User) {

        viewModel.deleteUser(user) { status ->
            if (status) {
                Toast.makeText(
                    activity,
                    getString(R.string.delete_user_success),
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                Toast.makeText(activity, getString(R.string.delete_user_failed), Toast.LENGTH_SHORT)
                    .show()
            }
        }


    }

    private fun updateUser(user: User) {

        editTxtFirstName.setText(user.firstName)
        editTxtLastName.setText(user.lastName)
        btDate.text = DateUtils.dateDBToAppFormat(user.birthdate)
        viewModel.updateBirthDate(user.birthdate)
        user.id?.let { viewModel.updateUserID(it) }

        btAdd.text = getString(R.string.update)
        viewModel.setActionType(TYPE_EDIT)

    }


    override fun onClickedItem(user: User) {

        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(getString(R.string.promp_title_user_action))
            builder.setItems(
                arrayOf(
                    getString(R.string.edit),
                    getString(R.string.delete)
                )
            ) { dialogInterface, i ->

                when (i) {
                    0 -> {
                        updateUser(user)
                        viewModel.setActionType(TYPE_EDIT)
                    }
                    1 -> {
                        deleteUser(user)
                    }
                }


            }
            val dialog = builder.create()
            dialog.show()
        }


    }


}


//        btAddUser.setOnClickListener {
//            viewModel.addUser(User(null, "John", "Rambo", "1990-02-04", ""))
//
//        }
//
//        btFetchUser.setOnClickListener {
//            viewModel.getAllUser()
//
//        }
//
//        btDeleteuser.setOnClickListener {
//            val users = viewModel.getAllUser()
////            Log.e(TAG, "btDeleteuser get user = " + users.toString())
//            viewModel.deleteUser(users?.get(0))
//
//        }
//
//        btUpdateUser.setOnClickListener {
//            val users = viewModel.getAllUser()
//            val user = users.get(0)
//            user.firstName = "loso"
//            viewModel.updateUser(user)
//
//        }
