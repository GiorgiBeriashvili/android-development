package dev.beriashvili.exams.firstmidterm.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import dev.beriashvili.exams.firstmidterm.R
import dev.beriashvili.exams.firstmidterm.adapters.LaunchRecyclerViewAdapter
import dev.beriashvili.exams.firstmidterm.models.LaunchesModel
import dev.beriashvili.exams.firstmidterm.models.Query
import dev.beriashvili.exams.firstmidterm.networking.HttpClient
import dev.beriashvili.exams.firstmidterm.networking.RequestCallback
import dev.beriashvili.exams.firstmidterm.utils.Notifier.showToast
import dev.beriashvili.exams.firstmidterm.utils.RequestCode
import kotlinx.android.synthetic.main.activity_launches.*

class LaunchesActivity : AppCompatActivity() {
    private var launches = mutableListOf<LaunchesModel>()
    private lateinit var adapter: LaunchRecyclerViewAdapter
    private lateinit var query: Query
    private lateinit var filters: Map<String, String>
    private var recordedLaunchCount: Int = 0
    private var sortPosition: Int = 2
    private var alteredOffset: Int = 0
    private var alteredLimit: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launches)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.queryParameters -> {
                val intent = Intent(this, QueryParametersActivity::class.java)

                intent.putExtra("sort", sortPosition)
                intent.putExtra("order", query.parameters["order"])
                intent.putExtra("offset", alteredOffset)
                intent.putExtra("limit", alteredLimit)
                intent.putExtra("filters", ArrayList(filters.values))
                intent.putExtra("launches", recordedLaunchCount)

                startActivityForResult(intent, RequestCode.ALTER_QUERY)
            }
            R.id.signOut -> {
                val intent = intent

                intent.putExtra("id", R.id.signOut)

                setResult(Activity.RESULT_OK, intent)

                showToast(this, "Signed out successfully!")

                finish()
            }
            R.id.about -> {
                AlertDialog.Builder(this)
                    .setTitle("About")
                    .setMessage("Refer to README.md file alongside the source code in \"android-development/exams/01-FirstMidterm/app/\" path for additional documentation regarding the application on my GitHub:\n\nhttps://github.com/GiorgiBeriashvili\n\nThank you for trying this out!")
                    .setIcon(R.drawable.ic_live_help)
                    .setNegativeButton("Close") { _, _ -> }
                    .create()
                    .show()
            }
            R.id.exit -> {
                AlertDialog.Builder(this)
                    .setTitle("Exit Application")
                    .setMessage("Would you like to exit the application?")
                    .setIcon(R.drawable.ic_close_application)
                    .setPositiveButton("Yes") { _, _ ->
                        val intent = intent

                        intent.putExtra("id", R.id.exit)

                        setResult(Activity.RESULT_OK, intent)

                        finish()
                    }
                    .setNegativeButton("No") { _, _ -> }
                    .create()
                    .show()
            }
        }

        return true
    }

    private fun init() {
        query = Query().apply {
            sort = "launch_date_utc"
            order = "asc"
            offset = alteredOffset
            limit = alteredLimit
            filter = ""

            filters = mapOf(
                Pair("flight_number", "Flight Number"),
                Pair("mission_name", "Mission Name"),
                Pair("launch_date_utc", "Launch Date (UTC)"),
                Pair("rocket/rocket_name", "Rocket Name"),
                Pair("launch_success", "Launch Success"),
                Pair("links/flickr_images", "Images"),
                Pair("details", "Details"),
                Pair("upcoming", "Upcoming")
            )

            filters.keys.forEach { filter ->
                this.filter += if (filters.keys.indexOf(filter) != filters.values.size - 1) {
                    "$filter,"
                } else {
                    filter
                }
            }

            parameters = mutableMapOf(
                Pair("sort", sort),
                Pair("order", order),
                Pair("offset", "$offset"),
                Pair("limit", "$limit"),
                Pair("filter", filter)
            )
        }

        fetchRecorderLaunchCount()

        render()
    }

    private fun fetchRecorderLaunchCount() {
        HttpClient.get("launches", mutableMapOf(Pair("filter", "flight_number")),
            object :
                RequestCallback {
                override fun onError(throwable: Throwable) {
                    showToast(this@LaunchesActivity, throwable.message.toString())
                }

                override fun onSuccess(response: String) {
                    launches = Gson().fromJson(response, arrayOf<LaunchesModel>()::class.java)
                        .toMutableList()

                    recordedLaunchCount = launches.size
                }
            })
    }

    private fun render() {
        HttpClient.get(
            "launches",
            query.parameters,
            object :
                RequestCallback {
                override fun onError(throwable: Throwable) {
                    showToast(this@LaunchesActivity, throwable.message.toString())
                }

                override fun onSuccess(response: String) {
                    d("http response (success)", response)

                    launches = Gson().fromJson(response, arrayOf<LaunchesModel>()::class.java)
                        .toMutableList()

                    launchRecyclerView.layoutManager = LinearLayoutManager(this@LaunchesActivity)

                    adapter =
                        LaunchRecyclerViewAdapter(
                            launches,
                            this@LaunchesActivity
                        )

                    launchRecyclerView.adapter = adapter
                }
            })
    }

    fun navigateToLaunchActivity(flightNumber: Int?) {
        for (launch in launches) {
            if (launch.flightNumber == flightNumber) {
                HttpClient.get(
                    "launches/$flightNumber",
                    query.parameters,
                    object :
                        RequestCallback {
                        override fun onError(throwable: Throwable) {
                            showToast(this@LaunchesActivity, throwable.message.toString())
                        }

                        override fun onSuccess(response: String) {
                            val intent = Intent(
                                this@LaunchesActivity,
                                LaunchActivity::class.java
                            )

                            val launchModel = Gson().fromJson(response, LaunchesModel::class.java)

                            intent.putExtra("launch", launchModel)
                            intent.putExtra("rocketName", launchModel.rocket?.rocketName)

                            if (!launchModel.links?.images.isNullOrEmpty()) {
                                intent.putExtra("image", launchModel.links?.images!![0])
                            }

                            startActivity(intent)
                        }
                    })

                break
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == RequestCode.ALTER_QUERY) {
            sortPosition = data?.getIntExtra("sortPosition", 0)!!
            val order = data.getStringExtra("order")
            alteredOffset = data.getIntExtra("offset", 0)
            alteredLimit = data.getIntExtra("limit", 0)

            intent.removeExtra("sort")
            intent.removeExtra("order")
            intent.removeExtra("offset")
            intent.removeExtra("limit")

            var sort = ArrayList(filters.keys)[sortPosition]

            if (sort.contains("/")) {
                sort = sort.substringAfterLast("/")
            }

            query.parameters["sort"] = sort
            query.parameters["order"] = order!!
            query.parameters["offset"] = "$alteredOffset"
            query.parameters["limit"] = "$alteredLimit"

            render()
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
