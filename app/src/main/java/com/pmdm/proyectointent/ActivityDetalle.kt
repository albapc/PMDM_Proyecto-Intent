package com.pmdm.proyectointent

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detalle.*

class ActivityDetalle : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //crea la activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        //obtiene el valor declarado en el main activity mediante su clave
        val valor = intent.getStringExtra("color")

        //cambia la caja de texto conforme el valor de la variable
        tResultado.setText(valor)

        //si el valor es amarillo...
        if (valor == "amarillo") {
            tResultado.setTextColor(Color.YELLOW) //cambia el texto a amarillo y escribe dicho nombre
        } else {
            tResultado.setTextColor(Color.RED) //lo mismo pero con el color rojo
        }
    }
}
