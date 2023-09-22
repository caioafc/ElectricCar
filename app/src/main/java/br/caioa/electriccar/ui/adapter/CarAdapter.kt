package br.caioa.electriccar.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.caioa.electriccar.R
import br.caioa.electriccar.domain.Car

class CarAdapter(private val cars: List<Car>, private val isFavoriteScreen : Boolean = false) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    var carItemListener : (Car) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = cars.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.price.text = cars[position].preco
        holder.battery.text = cars[position].bateria
        holder.power.text = cars[position].potencia
        holder.charge.text = cars[position].recarga
        if (isFavoriteScreen) {
            holder.favorite.setImageResource(R.drawable.ic_star_selected)
        }
        holder.favorite.setOnClickListener {
            val car = cars[position]
            carItemListener(car)
            setupFavorite(car, holder)
        }
    }

    private fun setupFavorite(
        car: Car,
        holder: ViewHolder
    ) {
            car.isFavorite = !car.isFavorite

            if (car.isFavorite) {
                holder.favorite.setImageResource(R.drawable.ic_star_selected)
            } else {
                holder.favorite.setImageResource(R.drawable.ic_star)
            }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val price: TextView
        val battery: TextView
        val power: TextView
        val charge: TextView
        val favorite: ImageView

        init {
            view.apply {
                price = findViewById(R.id.tv_price_value)
                battery = findViewById(R.id.tv_battery_value)
                power = findViewById(R.id.tv_power_value)
                charge = findViewById(R.id.tv_charge_value)
                favorite = findViewById(R.id.iv_favorite)
            }
        }
    }
}