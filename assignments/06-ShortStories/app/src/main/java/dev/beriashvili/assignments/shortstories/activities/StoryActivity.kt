package dev.beriashvili.assignments.shortstories.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import dev.beriashvili.assignments.shortstories.R
import dev.beriashvili.assignments.shortstories.database.StoryDatabase
import dev.beriashvili.assignments.shortstories.entities.Story
import dev.beriashvili.assignments.shortstories.utils.Mode
import kotlinx.android.synthetic.main.activity_story.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StoryActivity : AppCompatActivity() {
    private val db: StoryDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            StoryDatabase::class.java, "short-stories"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        init()
    }

    private fun init() {
        if (intent.getSerializableExtra("mode") == Mode.ADD) {
            addStory()
        } else if (intent.getSerializableExtra("mode") == Mode.EDIT) {
            editStory()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addStory() {
        storyButton.apply {
            text = "Add Story"

            setOnClickListener {
                if (hasValidFields()) {
                    val story =
                        Story(titleEditText.text.toString(), descriptionEditText.text.toString())

                    CoroutineScope(Dispatchers.IO).launch {
                        db.storyDao().insert(story)
                    }

                    setResult(Activity.RESULT_OK)

                    finish()
                } else {
                    Toast.makeText(
                        this@StoryActivity,
                        "Please enter valid information!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun editStory() {
        val id = intent.getIntExtra("id", 0)
        val position = intent.getIntExtra("position", 0)

        intent.extras?.clear()

        CoroutineScope(Dispatchers.IO).launch {
            val story = db.storyDao().getById(id)

            titleEditText.setText(story.title)
            descriptionEditText.setText(story.description)
        }

        storyButton.apply {
            text = "Edit Story"

            setOnClickListener {
                if (hasValidFields()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        db.storyDao().update(
                            id,
                            titleEditText.text.toString(),
                            descriptionEditText.text.toString()
                        )
                    }

                    intent.putExtra("id", id)
                    intent.putExtra("position", position)

                    setResult(Activity.RESULT_OK, intent)

                    finish()
                } else {
                    Toast.makeText(
                        this@StoryActivity,
                        "Please enter valid information!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun hasValidFields(): Boolean {
        return !titleEditText.text.isNullOrBlank() && !descriptionEditText.text.isNullOrBlank()
    }
}