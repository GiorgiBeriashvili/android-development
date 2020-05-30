package dev.beriashvili.assignments.shortstories.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import dev.beriashvili.assignments.shortstories.R
import dev.beriashvili.assignments.shortstories.adapters.StoryRecyclerViewAdapter
import dev.beriashvili.assignments.shortstories.database.StoryDatabase
import dev.beriashvili.assignments.shortstories.entities.Story
import dev.beriashvili.assignments.shortstories.utils.Mode
import kotlinx.android.synthetic.main.activity_short_stories.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShortStoriesActivity : AppCompatActivity() {
    companion object {
        const val ADD_STORY = 1
        const val EDIT_STORY = 2
    }

    val db: StoryDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            StoryDatabase::class.java, "short-stories"
        ).build()
    }

    val stories = mutableListOf<Story>()
    private lateinit var storyRecyclerViewAdapter: StoryRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_short_stories)
        init()
    }

    private fun init() {
        addStoryButton.setOnClickListener {
            val intent = Intent(this, StoryActivity::class.java)

            intent.putExtra("mode", Mode.ADD)

            startActivityForResult(intent, ADD_STORY)
        }

        CoroutineScope(Dispatchers.IO).launch {
            stories.addAll(db.storyDao().getAll())
        }

        storyRecyclerView.layoutManager = LinearLayoutManager(this)
        storyRecyclerViewAdapter = StoryRecyclerViewAdapter(stories, this)
        storyRecyclerView.adapter = storyRecyclerViewAdapter
    }

    fun editStory(id: Int, position: Int) {
        val intent = Intent(this, StoryActivity::class.java)

        intent.putExtra("id", id)
        intent.putExtra("position", position)
        intent.putExtra("mode", Mode.EDIT)

        startActivityForResult(intent, EDIT_STORY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == ADD_STORY) {
            CoroutineScope(Dispatchers.IO).launch {
                val story = db.storyDao().getLastById()

                CoroutineScope(Dispatchers.Main).launch {
                    stories.add(story)

                    storyRecyclerViewAdapter.notifyItemInserted(stories.size - 1)
                    storyRecyclerView.scrollToPosition(stories.size - 1)
                }
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == EDIT_STORY) {
            if (data != null) {
                val id = data.getIntExtra("id", 0)
                val position = data.getIntExtra("position", 0)

                intent.removeExtra("id")
                intent.removeExtra("position")

                CoroutineScope(Dispatchers.IO).launch {
                    val story = db.storyDao().getById(id)

                    CoroutineScope(Dispatchers.Main).launch {
                        stories[position].title = story.title
                        stories[position].description = story.description

                        storyRecyclerViewAdapter.notifyItemChanged(position)
                    }
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}