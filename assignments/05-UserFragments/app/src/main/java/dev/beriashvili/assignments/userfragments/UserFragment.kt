package dev.beriashvili.assignments.userfragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_user.view.*

class UserFragment(private val origin: Context) : Fragment() {
    private lateinit var itemView: View
    lateinit var user: UserModel.Data
    private lateinit var circularProgressDrawable: CircularProgressDrawable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        itemView = inflater.inflate(R.layout.fragment_user, container, false)

        init()

        return itemView
    }

    private fun init() {
        itemView.fullNameTextView.text = String.format("%s %s", user.firstName, user.lastName)
        itemView.emailTextView.text = user.email

        circularProgressDrawable = CircularProgressDrawable(origin)

        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f

        circularProgressDrawable.start()

        Glide.with(origin)
            .load(user.avatar)
            .placeholder(circularProgressDrawable)
            .into(itemView.imageView)

        if (origin is MainActivity) {
            itemView.setOnClickListener {
                origin.openUserProfile(user.id)
            }
        }
    }
}
