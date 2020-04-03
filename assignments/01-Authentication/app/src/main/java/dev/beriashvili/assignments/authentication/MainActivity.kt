package dev.beriashvili.assignments.authentication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        logInButton.setOnClickListener {
            if (emailEditText.text.isNotBlank() && passwordEditText.text.isNotBlank()) {
                Toast.makeText(this, "You have signed in successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter your account credentials!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
