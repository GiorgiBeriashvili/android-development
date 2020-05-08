package dev.beriashvili.assignments.userfragments

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var users = mutableListOf<UserModel.Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        HttpClient.get("users", mapOf(Pair("per_page", "12")), object : RequestCallback {
            override fun onFailed(errorMessage: String) {
                d("error", errorMessage)
            }

            override fun onSuccess(response: String) {
                val userModel = Gson().fromJson(response, UserModel::class.java)

                users.addAll(userModel.data)

                userViewPager.adapter =
                    UserViewPagerAdapter(supportFragmentManager, users, this@MainActivity)
            }
        })
    }

    fun openUserProfile(id: Int) {
        val intent = Intent(this, UserProfileActivity::class.java)

        for (user in users) {
            if (user.id == id) {
                HttpClient.get("users", id.toString(), object : RequestCallback {
                    override fun onFailed(errorMessage: String) {
                        d("error", errorMessage)
                    }

                    override fun onSuccess(response: String) {
                        val userModel = Gson().fromJson(response, UserModel.User::class.java)

                        intent.putExtra("user", userModel.data)

                        startActivity(intent)
                    }
                })

                break
            }
        }
    }
}
