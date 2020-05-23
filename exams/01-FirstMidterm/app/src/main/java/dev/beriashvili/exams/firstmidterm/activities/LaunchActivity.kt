package dev.beriashvili.exams.firstmidterm.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import dev.beriashvili.exams.firstmidterm.R
import dev.beriashvili.exams.firstmidterm.models.LaunchesModel
import kotlinx.android.synthetic.main.activity_launch.*

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        val intent = intent

        val launch = intent.getParcelableExtra<LaunchesModel>("launch")
        val rocketName = intent.getStringExtra("rocketName")
        val image = intent.getStringExtra("image")

        intent.removeExtra("launch")
        intent.removeExtra("rocketName")
        intent.removeExtra("images")

        val circularProgressDrawable = CircularProgressDrawable(this)

        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f

        circularProgressDrawable.start()

        if (!image.isNullOrEmpty()) {
            Glide.with(this)
                .load(image)
                .placeholder(circularProgressDrawable)
                .into(imageView)
        } else {
            Glide.with(this)
                .load(R.drawable.ic_spacex_logo)
                .placeholder(circularProgressDrawable)
                .into(imageView)
        }

        rocketNameTextView.text = rocketName

        if (launch?.details.isNullOrBlank()) {
            detailsTextView.text = "Unknown/unspecified."
        } else {
            detailsTextView.text = launch?.details
        }

        returnButton.setOnClickListener {
            finish()
        }
    }
}
