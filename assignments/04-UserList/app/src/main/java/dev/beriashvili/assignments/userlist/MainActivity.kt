package dev.beriashvili.assignments.userlist

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var users = mutableListOf<UserModel.Data>()
    private lateinit var adapter: UserRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        DataLoader.getRequest("users", mapOf(Pair("per_page", "12")), object : RequestCallback {
            override fun onFailed(errorMessage: String) {
                d("error", errorMessage)
            }

            override fun onSuccess(response: String) {
                val userModel = Gson().fromJson(response, UserModel::class.java)

                users.addAll(userModel.data)

                userRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

                adapter = UserRecyclerViewAdapter(users, this@MainActivity)
                userRecyclerView.adapter = adapter
            }
        })
    }

    fun openUserProfile(id: Int) {
        val intent = Intent(this, UserProfileActivity::class.java)

        for (user in users) {
            if (user.id == id) {
                DataLoader.getRequest("users", id.toString(), object : RequestCallback {
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
