package dev.beriashvili.assignments.userprofile

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit_user_profile.*
import java.text.SimpleDateFormat
import java.util.*

enum class DateType {
    YEAR,
    MONTH,
    DAY
}

class EditUserProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user_profile)
        init()
    }

    private fun init() {
        val intent = intent
        val userModel = intent.getParcelableExtra<UserModel>("userModel")

        intent.removeExtra("userModel")

        nameEditText.setText(userModel.name)
        surnameEditText.setText(userModel.surname)
        emailEditText.setText(userModel.email)
        birthDateTextView.text = userModel.birthDate

        handleBirthDate(userModel.birthDate)
        handleSex(userModel.sex)
    }

    private fun handleBirthDate(birthDate: String) {
        val dates = birthDate.split("-")

        val calendar = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val pattern = "yyyy-MM-dd"
                val simpleDateFormat = SimpleDateFormat(pattern, Locale.US)

                birthDateTextView.text = simpleDateFormat.format(calendar.time)
            }

        birthDateTextView.setOnClickListener {
            DatePickerDialog(
                this, dateSetListener,
                dates[DateType.YEAR.ordinal].toInt(),
                dates[DateType.MONTH.ordinal].toInt(),
                dates[DateType.DAY.ordinal].toInt()
            ).show()
        }
    }

    private fun handleSex(sex: String) {
        when (sex) {
            "Male" -> maleRadioButton.isChecked = true
            "Female" -> femaleRadioButton.isChecked = true
        }
    }

    fun saveProfile(view: View) {
        val intent = intent

        val userModel = UserModel(
            nameEditText.text.toString(),
            surnameEditText.text.toString(),
            emailEditText.text.toString(),
            birthDateTextView.text.toString(),
            findViewById<RadioButton>(sexRadioGroup.checkedRadioButtonId).text.toString()
        )

        intent.putExtra("userModel", userModel)
        setResult(Activity.RESULT_OK, intent)

        finish()
    }
}
