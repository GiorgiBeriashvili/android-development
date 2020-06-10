package dev.beriashvili.assignments.mvvm.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import dev.beriashvili.assignments.mvvm.R
import dev.beriashvili.assignments.mvvm.adapters.ApartmentRecyclerViewAdapter
import dev.beriashvili.assignments.mvvm.databinding.ActivityApartmentsBinding
import dev.beriashvili.assignments.mvvm.models.Apartment
import dev.beriashvili.assignments.mvvm.networking.HttpClient
import dev.beriashvili.assignments.mvvm.networking.RequestCallback
import kotlinx.android.synthetic.main.activity_apartments.*

class ApartmentsActivity : AppCompatActivity() {
    private var apartments = listOf<Apartment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView(
            this,
            R.layout.activity_apartments
        ) as ActivityApartmentsBinding

        init()
    }

    private fun init() {
        swipeRefreshLayout.isRefreshing = true

        fetchApartments()

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true

            fetchApartments()
        }
    }

    private fun fetchApartments() {
        HttpClient.get("5edb4d643200002a005d26f0", object : RequestCallback {
            override fun onError(throwable: Throwable) {
                Toast.makeText(this@ApartmentsActivity, throwable.message, Toast.LENGTH_SHORT)
                    .show()

                swipeRefreshLayout.isRefreshing = false
            }

            override fun onSuccess(response: String) {
                apartments =
                    Gson().fromJson(response, arrayOf<Apartment>()::class.java).toList()

                apartmentRecyclerView.adapter = ApartmentRecyclerViewAdapter(apartments)

                swipeRefreshLayout.isRefreshing = false
            }
        })
    }
}