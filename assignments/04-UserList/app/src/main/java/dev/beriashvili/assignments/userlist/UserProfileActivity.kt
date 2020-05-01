package dev.beriashvili.assignments.userlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        init()
    }

    private fun init() {
        val intent = intent

        val user = intent.getParcelableExtra<UserModel.Data>("user")

        intent.removeExtra("user")

        val circularProgressDrawable = CircularProgressDrawable(this)

        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f

        circularProgressDrawable.start()

        Glide.with(this)
            .load(user.avatar)
            .placeholder(circularProgressDrawable)
            .into(imageView)

        fullNameTextView.text = String.format("%s %s", user.firstName, user.lastName)
        emailTextView.text = user.email

        returnButton.setOnClickListener {
            finish()
        }
    }
}
