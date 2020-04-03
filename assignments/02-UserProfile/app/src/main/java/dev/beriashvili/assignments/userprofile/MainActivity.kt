package dev.beriashvili.assignments.userprofile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

private const val REQUEST_CODE = 1

enum class FullName {
    NAME,
    SURNAME
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        fullNameTextView.text = getString(R.string.full_name, "Giorgi", "Beriashvili")
        emailTextView.text = getString(R.string.email, "giorgi.beriashvili@outlook.com")
        birthDateTextView.text = getString(R.string.birth_date, "1999-07-02")
        sexTextView.text = getString(R.string.sex, "Male")
    }

    fun editProfile(view: View) {
        val fullName = fullNameTextView.text.toString().split(" ")

        val userModel = UserModel(
            fullName[FullName.NAME.ordinal],
            fullName[FullName.SURNAME.ordinal],
            emailTextView.text.toString(),
            birthDateTextView.text.toString(),
            sexTextView.text.toString()
        )

        val intent = Intent(this, EditUserProfileActivity::class.java)

        intent.putExtra("userModel", userModel)

        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            val userModel = data?.extras?.getParcelable<UserModel>("userModel")

            if (userModel != null) {
                fullNameTextView.text =
                    getString(R.string.full_name, userModel.name, userModel.surname)
                emailTextView.text = userModel.email
                birthDateTextView.text = userModel.birthDate
                sexTextView.text = userModel.sex
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
