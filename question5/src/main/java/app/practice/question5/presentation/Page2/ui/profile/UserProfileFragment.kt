package app.practice.question5.presentation.Page2.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import app.practice.question5.R
import app.practice.question5.domain.UserProfileUseCase
import app.practice.question5.model.DBUserMapper
import app.practice.question5.model.UserDatabase
import app.practice.question5.model.UserRepositoryImpl
import app.practice.question5.utils.DateUtils
import kotlinx.android.synthetic.main.fragment_user_profile.*

/**
 * A placeholder fragment containing a simple view.
 */
class UserProfileFragment : Fragment() {

    private lateinit var viewModel: UserPofileViewModel
    var userID: Int? = null

    private var listener: onUserProfileFragmentListener? = null

    interface onUserProfileFragmentListener {
        fun onDeleteUser()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            userID = it.getInt(ARG_USER_ID)
        }

        val db = UserDatabase.getInstance(activity!!)?.userDao()
        val mapper = DBUserMapper()
        val repo = UserRepositoryImpl(db!!, mapper)
        val useCase = UserProfileUseCase(repo)

        viewModel = ViewModelProviders.of(this, UserPofileViewModel.Factory(useCase))
            .get(UserPofileViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_user_profile, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userID?.let { viewModel.fetchUserByID(it) }

        initListener()
        observeViewModel()

    }

    private fun initListener() {

        btRemove.setOnClickListener {
            dialogPrompDelete()
        }

    }

    private fun dialogPrompDelete() {

        activity?.let {
            val user = viewModel.user().value
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Are You Sure To Delete \"${user?.fullName()}\"")
            builder.setPositiveButton(android.R.string.ok) { dialogInterface, i ->
                viewModel.deleteUser(user)
            }
            builder.setNegativeButton(android.R.string.cancel, null)
            val dialog = builder.create()
            dialog.show()
        }

    }

    private fun observeViewModel() {

        viewModel.user().observe(this, Observer {

            it.let { user ->

                if (user == null) {
                    listener?.onDeleteUser()
                } else {
                    txtFullName.text = user.fullName()
                    txtBirthDate.text = DateUtils.dateDBToAppFormat(user.birthdate)
                }

            }
        })

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is onUserProfileFragmentListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null

    }

    companion object {

        private const val ARG_USER_ID = "userID"


        @JvmStatic
        fun newInstance(userID: Int?): UserProfileFragment {
            return UserProfileFragment().apply {
                arguments = Bundle().apply {
                    if (userID != null) {
                        putInt(ARG_USER_ID, userID)
                    }
                }
            }
        }
    }
}