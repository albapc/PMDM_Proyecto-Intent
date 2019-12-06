package com.pmdm.proyectointent

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.browse
import android.widget.Toast
import android.provider.MediaStore
import android.util.Log
import android.widget.Button

//variables que contienen el codigo de las requests para las activities
const val SUMA_REQUEST = 1
private val MY_CAMERA_REQUEST_CODE = 100
const val REQUEST_IMAGE_CAPTURE = 2


class MainActivity : Activity() {

    //declarando los botones que se van a usar
    private var bRojo: Button? = null
    private var bAmarillo: Button? = null
    private var bNavegador: Button? = null
    private var bSumar: Button? = null
    private var bCamara: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        //crea la activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //comprueba si la app tiene permisos para usar la camara, si no loa tiene los pide
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_REQUEST_CODE)
        }

        //asignamos a cada boton su respectivo id de la interfaz y lo que va a hacer cada uno al pulsarlo
        bRojo = findViewById(R.id.bRojo)

        bRojo?.setOnClickListener {
            cambiarMensajeRojo()
        }

        bAmarillo = findViewById(R.id.bAmarillo)

        bAmarillo?.setOnClickListener {
            cambiarMensajeAmarillo()
        }

        bNavegador = findViewById(R.id.bNavegador)

        bNavegador?.setOnClickListener {
            abrirNavegador()
        }

        bSumar = findViewById(R.id.bSumar)

        bSumar?.setOnClickListener {
            hacerSuma()
        }

        bCamara = findViewById(R.id.bCamara)

        bCamara?.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    //cambian el texto de color
    fun cambiarMensajeRojo() {
        //asigna el intent a una actividad
        val miIntent = Intent(this, ActivityDetalle::class.java)

        //le da una clave y su valor
        miIntent.putExtra("color", "rojo")

        //inicia dicha activity
        startActivity(miIntent)
    }

    fun cambiarMensajeAmarillo() {
        val miIntent = Intent(this, ActivityDetalle::class.java)

        miIntent.putExtra("color", "amarillo")

        startActivity(miIntent)
    }

    fun abrirNavegador() {
        //UNA FORMA MAS COMPLEJA DE HACERLO (sin librerias)
        /*
        val url = "http://www.google.es"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
         */

        browse("http://www.google.es") //con libreria

    }

    fun hacerSuma() {

        val miIntent = Intent(this, ActivitySuma::class.java)

        //asigna el contenido de cada caja de texto a una variable
        val suma1 = editText.text
        val suma2 = editText2.text

        //los almacena en el intent en una clave distinta, y los pasa a int
        miIntent.putExtra("suma1", Integer.parseInt(suma1.toString()))
        miIntent.putExtra("suma2", Integer.parseInt(suma2.toString()))

        //inicia la activity para recibir un resultado referenciando a su codigo de request
        startActivityForResult(miIntent, SUMA_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //cuando el requestCode sea..
        when (requestCode) {
            SUMA_REQUEST ->
                when (resultCode) { //cuando el resultCode sea...
                    Activity.RESULT_OK ->
                        if (data != null) { //si recibe datos...
                            //modifica la caja de texto y obtiene el resultado de la suma
                            cajaText.setText(data.getIntExtra("suma", 0).toString())
                        }
                    Activity.RESULT_CANCELED ->
                        //muestra en la consola
                        Log.d("miApp", "Cancelado")
                }
            REQUEST_IMAGE_CAPTURE ->
                if (resultCode == RESULT_OK) {
                    //obtiene la foto hecha
                    val imageBitmap =
                        data!!.extras!!.get("data") as Bitmap //con !! se le dice que es seguro que no es nulo
                    //la inserta en una imagen de la interfaz
                    imageView.setImageBitmap(imageBitmap)
                }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        //hereda del mismo metodo
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //si el requestCode es el mismo que el indicado...
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            //si se le han dado permisos de camara...
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //muestra este mensaje
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show()
            } else {
                //sino muestra este otro
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }

        /*
        //OTRA FORMA DE HACERLO
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    MY_CAMERA_REQUEST_CODE)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
         */

    }

    private fun dispatchTakePictureIntent() {
        //abre la camara para hacer una foto y llama a un intent asociando al requestCode para mostrarlo
        //en el onActivityResult
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

}
