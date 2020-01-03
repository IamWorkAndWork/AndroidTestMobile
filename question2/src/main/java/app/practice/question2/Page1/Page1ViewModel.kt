package app.practice.question2.Page1

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import app.practice.question2.R
import app.practice.question2.`interface`.UserCallBack
import app.practice.question2.model.User

class Page1ViewModel(private var listener: UserCallBack) : ViewModel() {

    val user: User = User("", "", Page1Activity.MALE, "")
//    val user: User = User("john", "rambo", Page1Activity.MALE, "rambo@test.com")

    val TAG by lazy {
        javaClass.simpleName
    }

    val firstNameTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(text: Editable?) {
            user.setFirstName(text.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }

    val lastNameTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(text: Editable?) {
            user.setLastName(text.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }

    val emailTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(text: Editable?) {
            user.setEmail(text.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }

    fun onNextClicked(v: View, context: Context) {
        val codes = user.isDataValid()

//        var errorMessage = mutableListOf<String>()

        Log.d(TAG, "code = " + codes)

        if (codes.size == 0) {
            listener.onSuccess(context.getString(R.string.valid_user))
        } else {
            val listMessage = mutableListOf<String>()
            for (i in codes.indices) {
                val errorCode = codes.get(i);
                val message = when (errorCode) {
                    1 -> context.getString(R.string.invalid_firstname)
                    2 -> context.getString(R.string.invalid_lastname)
                    else -> context.getString(R.string.invalid_email_pattern)
                }
                listMessage.add(message)
            }
            listener.onError(listMessage.joinToString())
        }

//        if (code == 0) {
//            listener.onError(context.getString(R.string.invalid_firstname_lastname_email))
//        } else if (code == 1) {
//            listener.onError(context.getString(R.string.invalid_firstname_lastname))
//        } else if (code == 2) {
//            listener.onError(context.getString(R.string.invalid_firstname))
//        } else if (code == 2) {
//            listener.onError(context.getString(R.string.invalid_lastname))
//        } else if (code == 3) {
//            listener.onError(context.getString(R.string.invalid_email_pattern))
//        } else {
//            listener.onSuccess(context.getString(R.string.valid_user))
//        }

    }

    fun setGender(gender: String) {
        user.setGender(gender)
    }


}