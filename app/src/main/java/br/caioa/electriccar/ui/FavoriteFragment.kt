package br.caioa.electriccar.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.caioa.electriccar.R
import br.caioa.electriccar.data.local.CarRepository
import br.caioa.electriccar.domain.Car
import br.caioa.electriccar.ui.adapter.CarAdapter

class FavoriteFragment : Fragment() {
    lateinit var listFavoriteCars: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupList()
    }

    private fun getCarsOnLocalDb(): List<Car> {
        val repository = CarRepository(requireContext())
        val carList = repository.getAll()
        return carList
    }

    private fun setupView(view: View) {
        view.apply {
            listFavoriteCars = findViewById(R.id.rv_list_favorite_cars)
        }
    }

    private fun setupList() {
        val list = getCarsOnLocalDb()
        val carAdapter = CarAdapter(list, isFavoriteScreen = true)
        listFavoriteCars.apply {
            isVisible = true
            adapter = carAdapter
        }
    }
}