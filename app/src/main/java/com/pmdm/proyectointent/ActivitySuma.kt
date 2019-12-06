package com.pmdm.proyectointent

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.activity_suma.*


class ActivitySuma : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suma)

        val valor1 = intent.getIntExtra("suma1", 0)
        val valor2 = intent.getIntExtra("suma2", 0)

        val suma = valor1 + valor2

        tResultado.setText(suma.toString())

        bVolver.setOnClickListener {
            //val suma = valor1 + valor2
            val data = Intent()

            data.putExtra("suma", suma)
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }
}
