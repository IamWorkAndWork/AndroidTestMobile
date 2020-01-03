package app.practice.question2.model

import android.os.Parcelable
import android.text.TextUtils
import android.util.Patterns
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User(
    private var firstname: String,
    private var lastname: String,
    private var gender: String,
    private var email: String
) : Parcelable {

    fun isDataValid(): List<Int> {
        var listCode = mutableListOf<Int>()
        if (TextUtils.isEmpty(getFirstName())) {
            listCode.add(1)
        }
        if (TextUtils.isEmpty(getLastName())) {
            listCode.add(2)
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()) {
            listCode.add(3)
        }
        return listCode
    }

    fun getEmail(): String {
        return this.email
    }

    fun getLastName(): String {
        return lastname
    }

    fun getFirstName(): String {
        return firstname
    }

    fun getGender(): String {
        return this.gender
    }

    fun setGender(gender: String) {
        this.gender = gender
    }

    fun setFirstName(firstname: String) {
        this.firstname = firstname
    }

    fun setLastName(lastname: String) {
        this.lastname = lastname
    }

    fun setEmail(email: String) {
        this.email = email
    }


}