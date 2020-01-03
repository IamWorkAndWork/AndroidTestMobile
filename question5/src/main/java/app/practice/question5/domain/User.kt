package app.practice.question5.domain

import android.text.TextUtils

data class User(
    var id: Int? = null,
    var firstName: String,
    var lastName: String,
    var birthdate: String,
    var picture: String
) {

    fun isValidInput(): Boolean {
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(
                birthdate
            )
        ) {
            return false
        }
        return true
    }

    fun fullName(): String {
        return firstName + " " + lastName
    }


}