package dev.beriashvili.assignments.mvvm.objects

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.apartment_recyclerview_layout.view.*

object DataBindingComponent {
    @JvmStatic
    @BindingAdapter("image")
    fun setImage(imageView: ImageView, image: String?) {
        val circularProgressDrawable = CircularProgressDrawable(imageView.context)

        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f

        circularProgressDrawable.start()

        Glide.with(imageView.context)
            .load(image)
            .placeholder(circularProgressDrawable)
            .into(imageView.imageView)
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("text")
    fun setText(textView: TextView, text: String?) {
        if (text != null) {
            when {
                text.length >= 160 -> textView.text = "${text.subSequence(0, 160 - 1)}..."
                else -> textView.text = text
            }
        }
    }
}