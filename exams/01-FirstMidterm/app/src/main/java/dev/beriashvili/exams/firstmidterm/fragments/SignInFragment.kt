package dev.beriashvili.exams.firstmidterm.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dev.beriashvili.exams.firstmidterm.R
import dev.beriashvili.exams.firstmidterm.activities.AuthenticationActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment(private val origin: Context) : Fragment(R.layout.fragment_sign_in) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        signInButton.setOnClickListener {
            if (origin is AuthenticationActivity) {
                origin.signInUser(
                    signInEmailEditText.text.toString().trim(),
                    signInPasswordEditText.text.toString().trim()
                )
            }
        }
    }
}
