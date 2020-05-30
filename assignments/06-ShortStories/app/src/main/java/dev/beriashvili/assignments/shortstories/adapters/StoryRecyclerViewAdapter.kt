package dev.beriashvili.assignments.shortstories.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.beriashvili.assignments.shortstories.R
import dev.beriashvili.assignments.shortstories.activities.ShortStoriesActivity
import dev.beriashvili.assignments.shortstories.entities.Story
import kotlinx.android.synthetic.main.story_recyclerview_layout.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StoryRecyclerViewAdapter(
    private val stories: MutableList<Story>,
    private val origin: Context
) : RecyclerView.Adapter<StoryRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.story_recyclerview_layout, parent, false)
        )
    }

    override fun getItemCount(): Int = stories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var story: Story

        fun onBind() {
            story = stories[adapterPosition]

            itemView.titleTextView.text = story.title

            if (origin is ShortStoriesActivity) {
                itemView.setOnClickListener {
                    origin.editStory(story.id, adapterPosition)
                }

                itemView.setOnLongClickListener {
                    origin.stories.removeAt(adapterPosition)

                    notifyItemRemoved(adapterPosition)

                    CoroutineScope(Dispatchers.IO).launch {
                        origin.db.storyDao().delete(story)
                    }

                    if (origin.stories.isEmpty()) {
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(250)

                            origin.onEmpty()
                        }
                    }

                    true
                }
            }
        }
    }
}