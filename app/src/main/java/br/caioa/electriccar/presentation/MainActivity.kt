package br.caioa.electriccar.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import br.caioa.electriccar.R

class MainActivity : AppCompatActivity() {
    lateinit var btnRedirectCalculate: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupListeners()
    }

    private fun setupView() {
        btnRedirectCalculate = findViewById(R.id.btn_redirect_calculate)
    }

    private fun setupListeners() {
        btnRedirectCalculate.setOnClickListener {
            startActivity(Intent(this, CalculateAutonomyActivity::class.java))
        }

    }


}