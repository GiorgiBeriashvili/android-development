package dev.beriashvili.assignments.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.user_recyclerview_layout.view.*

class UserRecyclerViewAdapter(
    private val users: MutableList<UserModel.Data>,
    private val activity: MainActivity
) : RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.user_recyclerview_layout, parent, false)
        )
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var user: UserModel.Data
        private val circularProgressDrawable = CircularProgressDrawable(activity)

        fun onBind() {
            user = users[adapterPosition]

            itemView.fullNameTextView.text = String.format("%s %s", user.firstName, user.lastName)
            itemView.emailTextView.text = user.email

            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f

            circularProgressDrawable.start()

            Glide.with(activity)
                .load(user.avatar)
                .placeholder(circularProgressDrawable)
                .into(itemView.imageView)

            itemView.setOnClickListener {
                activity.openUserProfile(user.id)
            }
        }
    }
}