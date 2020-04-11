package dev.beriashvili.assignments.itemlist

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_item.*
import java.text.SimpleDateFormat
import java.util.*

const val REQUEST_CHOOSE_IMAGE = 2

class AddItemActivity : AppCompatActivity() {
    private lateinit var image: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        init()
    }

    private fun init() {
        image = Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(resources.getResourcePackageName(R.mipmap.ic_image_foreground))
            .appendPath(resources.getResourceTypeName(R.mipmap.ic_image_foreground))
            .appendPath(resources.getResourceEntryName(R.mipmap.ic_image_foreground))
            .build()
    }

    fun saveItem(view: View) {
        val intent = intent

        val title = titleEditText.text.toString()
        val description = descriptionEditText.text.toString()
        val currentDateTime =
            SimpleDateFormat.getDateTimeInstance().format(Calendar.getInstance().time)

        val itemModel = ItemModel(
            image,
            title,
            description,
            currentDateTime
        )

        intent.putExtra("itemModel", itemModel)
        setResult(Activity.RESULT_OK, intent)

        finish()
    }

    fun chooseImage(view: View) {
        val intent = Intent()

        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(Intent.createChooser(intent, "Choose Image"), REQUEST_CHOOSE_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CHOOSE_IMAGE) {
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, data?.data)

            imageView.setImageBitmap(bitmap)

            image = data?.data!!
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
