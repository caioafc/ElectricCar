package br.caioa.electriccar.ui

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.caioa.electriccar.R
import br.caioa.electriccar.data.CarsApi
import br.caioa.electriccar.data.local.CarRepository
import br.caioa.electriccar.data.local.CarsContract
import br.caioa.electriccar.data.local.CarsContract.CarEntry.COLUMN_NAME_BATERIA
import br.caioa.electriccar.data.local.CarsContract.CarEntry.COLUMN_NAME_POTENCIA
import br.caioa.electriccar.data.local.CarsContract.CarEntry.COLUMN_NAME_PRECO
import br.caioa.electriccar.data.local.CarsContract.CarEntry.COLUMN_NAME_RECARGA
import br.caioa.electriccar.data.local.CarsContract.CarEntry.COLUMN_NAME_URL_PHOTO
import br.caioa.electriccar.data.local.CarsContract.CarEntry.TABLE_NAME
import br.caioa.electriccar.data.local.CarsDbHelper
import br.caioa.electriccar.domain.Car
import br.caioa.electriccar.ui.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CarFragment : Fragment() {
    lateinit var fabRedirectCalculate: FloatingActionButton
    lateinit var listCars: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var noConnectionImage : ImageView
    lateinit var noConnectionText : TextView
    lateinit var carsApi : CarsApi

    var carsArray : ArrayList<Car> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRetrofit()
        setupView(view)
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        if (hasInternet(context)) {
            getAllCars()
        } else {
            emptyState()
        }
    }

    private fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://igorbag.github.io/cars-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        carsApi = retrofit.create(CarsApi::class.java)
    }

    private fun getAllCars() {
        carsApi.getAllCars().enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                if(response.isSuccessful) {
                    progressBar.isVisible = false
                    noConnectionImage.isVisible = false
                    noConnectionText.isVisible = false

                    response.body()?.let {
                        setupList(it)
                    }
                } else {
                    Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun emptyState() {
        progressBar.isVisible = false
        listCars.isVisible = false
        noConnectionImage.isVisible = true
        noConnectionText.isVisible = true
    }

    private fun setupView(view: View) {
        view.apply {
            fabRedirectCalculate = findViewById(R.id.fab_redirect_calculate)
            listCars = findViewById(R.id.rv_list_cars)
            progressBar = findViewById(R.id.pb_loader)
            noConnectionImage = findViewById(R.id.iv_empty_state)
            noConnectionText = findViewById(R.id.tv_no_connection)
        }
    }

    private fun setupList(list : List<Car>) {
        val carAdapter = CarAdapter(list)
        listCars.apply {
            isVisible = true
            adapter = carAdapter
        }
        carAdapter.carItemListener = { carro ->
            val isSaved = CarRepository(requireContext()).saveIfNotExist(carro)
        }
    }

    private fun setupListeners() {
        fabRedirectCalculate.setOnClickListener {
            startActivity(Intent(context, CalculateAutonomyActivity::class.java))
        }
    }

    private fun hasInternet(context: Context?) : Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}