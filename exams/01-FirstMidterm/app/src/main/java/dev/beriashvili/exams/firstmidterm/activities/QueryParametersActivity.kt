package dev.beriashvili.exams.firstmidterm.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import dev.beriashvili.exams.firstmidterm.R
import kotlinx.android.synthetic.main.activity_query_parameters.*

class QueryParametersActivity : AppCompatActivity() {
    var sortPosition = 0
    private var order = ""
    var offset = 0
    var limit = 0
    private var launches = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query_parameters)
        init()
    }

    private fun init() {
        sortPosition = intent.getIntExtra("sort", 0)
        order = intent.getStringExtra("order")!!
        offset = intent.getIntExtra("offset", 0)
        limit = intent.getIntExtra("limit", 0)
        val filters = intent.getStringArrayListExtra("filters")
        launches = intent.getIntExtra("launches", 0)

        if (launches > 0) {
            launches -= 1
        }

        intent.removeExtra("sort")
        intent.removeExtra("order")
        intent.removeExtra("offset")
        intent.removeExtra("limit")
        intent.removeExtra("filters")
        intent.removeExtra("launches")

        if (filters != null) {
            val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, filters)

            filterSpinner.adapter = adapter
        }

        filterSpinner.setSelection(sortPosition)

        filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(adapterView: AdapterView<*>?) {

            }

            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sortPosition = position
            }
        }

        orderSwitch.isChecked = order == "asc"

        orderSwitch.setOnCheckedChangeListener { _, checked ->
            order = if (checked) {
                "asc"
            } else {
                "desc"
            }
        }

        offsetSeekBar.max = launches
        offsetSeekBar.progress = offset
        offsetTextView.text = String.format("Offset: %d", offset)

        offsetSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                offsetTextView.text = String.format("Offset: %d", progress)

                offset = progress
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        limitSeekBar.max = launches
        limitSeekBar.progress = limit
        limitTextView.text = String.format("Limit: %d", limit)

        limitSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                limitTextView.text = String.format("Limit: %d", progress)

                limit = progress
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        saveButton.setOnClickListener {
            save()
        }
    }

    private fun save() {
        val intent = intent

        intent.putExtra("sortPosition", sortPosition)
        intent.putExtra("order", order)
        intent.putExtra("offset", offset)
        intent.putExtra("limit", limit)

        setResult(Activity.RESULT_OK, intent)

        finish()
    }
}
