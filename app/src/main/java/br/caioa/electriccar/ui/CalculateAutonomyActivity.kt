package br.caioa.electriccar.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.caioa.electriccar.R

class CalculateAutonomyActivity: AppCompatActivity() {
    lateinit var price: EditText
    lateinit var kmTraveled: EditText
    lateinit var result: TextView
    lateinit var btnCalculate: Button
    lateinit var btnClose: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_autonomy)
        setupView()
        setupListeners()
    }

    private fun setupView() {
        price = findViewById(R.id.et_price)
        btnCalculate = findViewById(R.id.btn_calculate)
        kmTraveled = findViewById(R.id.et_km_traveled)
        result = findViewById(R.id.tv_result)
        btnClose = findViewById(R.id.iv_close)
    }

    private fun setupListeners() {
        btnCalculate.setOnClickListener {
            calculate()
        }

        btnClose.setOnClickListener {
            finish()
        }
    }

    private fun calculate() {
        val price = price.text.toString().toFloat()
        val kmTraveled = kmTraveled.text.toString().toFloat()

        val calculatedResult = price / kmTraveled
        result.text = calculatedResult.toString()
    }
}