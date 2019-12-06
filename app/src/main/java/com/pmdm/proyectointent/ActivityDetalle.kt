package com.pmdm.proyectointent

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detalle.*

class ActivityDetalle : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        val valor = intent.getStringExtra("color")

        tResultado.setText(valor)


        if (valor == "amarillo") {
            tResultado.setTextColor(Color.YELLOW)
        } else {
            tResultado.setTextColor(Color.RED)
        }
    }
}
