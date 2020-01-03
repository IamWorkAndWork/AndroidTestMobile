package app.practice.question2.Page1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import app.practice.question2.R
import app.practice.question2.`interface`.UserCallBack
import app.practice.question2.page2.Page2Activity
import kotlinx.android.synthetic.main.activity_page1.*

class Page1Activity : AppCompatActivity(), UserCallBack {

    lateinit var viewModel: Page1ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page1)

        viewModel =
            ViewModelProviders.of(this, Page1ViewModelFactory(this))
                .get(Page1ViewModel::class.java)

        initListener()
        initObserver()

    }

    private fun initObserver() {



    }

    private fun initListener() {

        page1EditTxtFirstName.addTextChangedListener(viewModel.firstNameTextWatcher)
        page1EditTxtLastName.addTextChangedListener(viewModel.lastNameTextWatcher)
        page1EditTxtEmail.addTextChangedListener(viewModel.emailTextWatcher)

        page1RadioGroupGender.setOnCheckedChangeListener { radioGroup, id ->
            when (id) {
                R.id.page1RadioBtMale -> {
                    viewModel.setGender(MALE)
                }
                R.id.page1RadioBtFemale -> {
                    viewModel.setGender(FEMALE)
                }
            }
        }

        page1BtNext.setOnClickListener {
            viewModel.onNextClicked(it, this)
        }

    }


    companion object {

        const val MALE = "Male"
        const val FEMALE = "Female"
        const val KEY_USER = "USER"
    }


    override fun onSuccess(message: String) {

        val intent = Intent(this, Page2Activity::class.java)
        intent.putExtra(KEY_USER, viewModel?.user)
        startActivity(intent)

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }


    override fun onError(message: String) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    }


}
