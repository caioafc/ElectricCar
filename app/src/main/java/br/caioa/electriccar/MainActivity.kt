package br.caioa.electriccar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    lateinit var price: EditText
    lateinit var kmTraveled: EditText
    lateinit var btnCalculate: Button
    lateinit var result: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupListeners()
    }

    fun setupView() {
        price = findViewById(R.id.et_price)
        btnCalculate = findViewById(R.id.btn_calculate)
        kmTraveled = findViewById(R.id.et_km_traveled)
        result = findViewById(R.id.tv_result)
    }

    fun setupListeners() {
        btnCalculate.setOnClickListener {
            calculate()
        }

    }

    fun calculate() {
        val price = price.text.toString().toFloat()
        val kmTraveled = kmTraveled.text.toString().toFloat()

        val calculatedResult = price / kmTraveled
        result.text = calculatedResult.toString()
    }
}