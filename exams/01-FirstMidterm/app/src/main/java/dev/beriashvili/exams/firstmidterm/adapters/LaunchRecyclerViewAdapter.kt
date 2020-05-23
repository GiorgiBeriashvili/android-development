package dev.beriashvili.exams.firstmidterm.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import dev.beriashvili.exams.firstmidterm.R
import dev.beriashvili.exams.firstmidterm.activities.LaunchesActivity
import dev.beriashvili.exams.firstmidterm.models.LaunchesModel
import kotlinx.android.synthetic.main.launch_recyclerview_layout.view.*

class LaunchRecyclerViewAdapter(
    private val users: MutableList<LaunchesModel>,
    private val origin: Context
) : RecyclerView.Adapter<LaunchRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.launch_recyclerview_layout, parent, false)
        )
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var launch: LaunchesModel
        private val circularProgressDrawable = CircularProgressDrawable(origin)

        @SuppressLint("SetTextI18n")
        fun onBind() {
            launch = users[adapterPosition]

            val date = launch.launchDate?.substringBefore("T")
            val time = launch.launchDate?.substringAfter("T")

            itemView.missionNameTextView.text = launch.missionName
            itemView.launchDateTextView.text = "$date ${time?.substringBefore(".")}"

            if (launch.launchSuccess == null) {
                itemView.statusTextView.text = "Yet to be launched..."
                itemView.statusTextView.setTextColor(Color.YELLOW)
            } else {
                if (launch.launchSuccess!!) {
                    itemView.statusTextView.text = "Successfully launched!"
                    itemView.statusTextView.setTextColor(Color.GREEN)
                } else {
                    itemView.statusTextView.text = "Failed to launch!"
                    itemView.statusTextView.setTextColor(Color.RED)
                }
            }

            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f

            circularProgressDrawable.start()

            val images = launch.links?.images

            if (!images.isNullOrEmpty()) {
                Glide.with(origin)
                    .load(images.first())
                    .placeholder(circularProgressDrawable)
                    .into(itemView.imageView)
            } else {
                Glide.with(origin)
                    .load(R.drawable.ic_spacex_logo)
                    .placeholder(circularProgressDrawable)
                    .into(itemView.imageView)
            }

            itemView.setOnClickListener {
                if (origin is LaunchesActivity) {
                    origin.navigateToLaunchActivity(launch.flightNumber)
                }
            }
        }
    }
}
