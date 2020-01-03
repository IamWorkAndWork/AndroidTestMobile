package app.practice.question5.presentation.Page1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.practice.question5.R

class Page1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page1)

        setTitle(getString(R.string.page1))

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, Page1Fragment.newInstance())
                .commitNow()
        }
    }


}
