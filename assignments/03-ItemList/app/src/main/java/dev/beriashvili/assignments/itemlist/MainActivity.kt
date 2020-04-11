package dev.beriashvili.assignments.itemlist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

private const val REQUEST_ADD_ITEM = 1

class MainActivity : AppCompatActivity() {
    val items = ArrayList<ItemModel>()
    private val position = 0
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerViewAdapter(items, this)
        recyclerView.adapter = adapter
    }

    fun addItem(view: View) {
        val intent = Intent(this, AddItemActivity::class.java)

        startActivityForResult(intent, REQUEST_ADD_ITEM)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_ADD_ITEM) {
            val itemModel: ItemModel = data!!.extras!!.getParcelable("itemModel")!!

            items.add(
                position,
                ItemModel(
                    itemModel.image,
                    itemModel.title,
                    itemModel.description,
                    itemModel.creationDateTime
                )
            )

            adapter.notifyItemInserted(position)
            recyclerView.scrollToPosition(position)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
