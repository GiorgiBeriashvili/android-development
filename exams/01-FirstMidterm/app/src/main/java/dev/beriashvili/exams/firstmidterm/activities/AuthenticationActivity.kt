package dev.beriashvili.exams.firstmidterm.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.beriashvili.exams.firstmidterm.R
import dev.beriashvili.exams.firstmidterm.fragments.SignInFragment
import dev.beriashvili.exams.firstmidterm.fragments.SignUpFragment
import dev.beriashvili.exams.firstmidterm.utils.Notifier.showToast
import dev.beriashvili.exams.firstmidterm.utils.RequestCode
import kotlinx.android.synthetic.main.activity_authentication.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var authentication: FirebaseAuth
    private lateinit var signInFragment: SignInFragment
    private lateinit var signUpFragment: SignUpFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        authentication = Firebase.auth

        signInFragment = SignInFragment(this)
        signUpFragment = SignUpFragment(this)

        setCurrentFragment(signInFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.signIn -> setCurrentFragment(signInFragment)
                R.id.signUp -> setCurrentFragment(signUpFragment)
            }

            true
        }
    }

    override fun onStart() {
        super.onStart()

        if (authentication.currentUser != null) {
            navigateToActivity(LaunchesActivity::class.java)
        }
    }

    fun signInUser(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            CoroutineScope(Dispatchers.IO).launch {
                authentication.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { signIn: Task<AuthResult> ->
                        if (signIn.isSuccessful) {
                            showToast(this@AuthenticationActivity, "Signed in successfully!")

                            navigateToActivity(LaunchesActivity::class.java)
                        } else {
                            showToast(this@AuthenticationActivity, signIn.exception?.message)
                        }
                    }
            }
        } else {
            showToast(this, "Please fill in the fields!")
        }
    }

    fun signUpUser(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            CoroutineScope(Dispatchers.IO).launch {
                authentication.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { signUp: Task<AuthResult> ->
                        if (signUp.isSuccessful) {
                            showToast(this@AuthenticationActivity, "Signed up successfully!")

                            setCurrentFragment(signInFragment)

                            bottomNavigationView.selectedItemId = R.id.signIn
                        } else {
                            showToast(this@AuthenticationActivity, signUp.exception?.message)
                        }
                    }
            }
        } else {
            showToast(this, "Please fill in the fields!")
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentFrameLayout, fragment)

            commit()
        }
    }

    private fun navigateToActivity(activityClass: Class<*>?) {
        val intent = Intent(this, activityClass)

        startActivityForResult(intent, RequestCode.SIGN_OUT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == RequestCode.SIGN_OUT) {
            when (data?.extras?.getInt("id")) {
                R.id.signOut -> authentication.signOut()
                R.id.exit -> finishAndRemoveTask()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
