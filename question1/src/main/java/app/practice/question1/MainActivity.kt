package app.practice.question1

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showHelloWorld(btHelloWorld)
    }

    fun showHelloWorld(v: View) {
        Toast.makeText(this, getString(R.string.hello_world), Toast.LENGTH_LONG).show()
        Log.d(javaClass.simpleName, getString(R.string.hello_world))
    }
}
