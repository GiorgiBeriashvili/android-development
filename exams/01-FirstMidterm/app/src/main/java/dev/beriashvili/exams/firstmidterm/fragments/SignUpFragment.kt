package dev.beriashvili.exams.firstmidterm.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dev.beriashvili.exams.firstmidterm.R
import dev.beriashvili.exams.firstmidterm.activities.AuthenticationActivity
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment(private val origin: Context) : Fragment(R.layout.fragment_sign_up) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpButton.setOnClickListener {
            if (origin is AuthenticationActivity) {
                origin.signUpUser(
                    signUpEmailEditText.text.toString().trim(),
                    signUpPasswordEditText.text.toString().trim()
                )
            }
        }
    }
}
