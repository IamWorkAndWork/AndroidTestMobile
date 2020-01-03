package app.practice.question2.page2

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import app.practice.question2.Page1.Page1Activity
import app.practice.question2.R
import app.practice.question2.model.User
import kotlinx.android.synthetic.main.activity_page2.*

class Page2Activity : AppCompatActivity() {

    var user: User? = null

    val TAG by lazy {
        javaClass.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page2)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent?.extras?.let {
            user = it?.getParcelable(Page1Activity.KEY_USER)
            Log.d(TAG, "user = ${user.toString()}")
        }


        user?.let {
            page2Email.text = user!!.getEmail()
            page2TxtName.text = user!!.getFirstName() + " " + user!!.getLastName()
            page2TxtGender.text = user!!.getGender()
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

}
