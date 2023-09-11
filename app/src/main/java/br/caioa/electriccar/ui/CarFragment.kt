package br.caioa.electriccar.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.caioa.electriccar.R
import br.caioa.electriccar.data.CarFactory
import br.caioa.electriccar.ui.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CarFragment : Fragment() {
    lateinit var fabRedirectCalculate: FloatingActionButton
    lateinit var listCars: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupList()
        setupListeners()
    }

    private fun setupView(view: View) {
        view.apply {
            fabRedirectCalculate = findViewById(R.id.fab_redirect_calculate)
            listCars = findViewById(R.id.rv_list_cars)
        }
    }

    private fun setupList() {
        val adapter = CarAdapter(CarFactory.list)
        listCars.adapter = adapter
    }

    private fun setupListeners() {
        fabRedirectCalculate.setOnClickListener {
            startActivity(Intent(context, CalculateAutonomyActivity::class.java))
        }
    }
}