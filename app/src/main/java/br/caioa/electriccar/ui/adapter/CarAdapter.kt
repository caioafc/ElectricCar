package br.caioa.electriccar.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.caioa.electriccar.R
import br.caioa.electriccar.domain.Car

class CarAdapter(private val cars: List<Car>) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = cars.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.price.text = cars[position].price
        holder.battery.text = cars[position].battery
        holder.power.text = cars[position].power
        holder.charge.text = cars[position].charge
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val price: TextView
        val battery: TextView
        val power: TextView
        val charge: TextView

        init {
            view.apply {
                price = findViewById(R.id.tv_price_value)
                battery = findViewById(R.id.tv_battery_value)
                power = findViewById(R.id.tv_power_value)
                charge = findViewById(R.id.tv_charge_value)
            }
        }
    }
}