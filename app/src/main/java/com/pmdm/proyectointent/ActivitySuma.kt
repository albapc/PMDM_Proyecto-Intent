package com.pmdm.proyectointent

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.activity_suma.*


class ActivitySuma : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        //crea la activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suma)

        //obtiene los valores introducidos en las cajas de texto del main activity y les da un valor por defecto
        val valor1 = intent.getIntExtra("suma1", 0)
        val valor2 = intent.getIntExtra("suma2", 0)

        //variable que suma ambos valores
        val suma = valor1 + valor2

        //modifica el contenido del texto almacenando el resultado de la suma
        tResultado.setText(suma.toString())

        //accion realizada al pulsar el boton "volver"
        bVolver.setOnClickListener {
            //variable que almacena el intent
            val data = Intent()

            //clave y valor que se devolverá al activity main
            data.putExtra("suma", suma)
            //devuelve un resultado positivo y el intent de esta actividad
            setResult(Activity.RESULT_OK, data)
            //cierra esta activity y vuelve al main
            finish()
        }
    }
}
