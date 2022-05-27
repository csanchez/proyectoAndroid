package com.example.iisapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iisapp.data.model.IisNotification

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _notification = MutableLiveData<IisNotification>().apply {
        value = IisNotification(
                1,
            2,
        "Mensaje para la comunidad académica del IIS-UNAM",
        "Instituto Investigaciones Sociales\n" +
                "\t\n" +
                "sáb, 20 nov 8:22 (hace 9 días)\n" +
                "\t\n" +
                "para bcc: mí\n" +
                "\n" +
                "A las y los miembros de la comunidad académica del IIS-UNAM: \n" +
                "\n" +
                "\n" +
                "\n" +
                "En alcance a mi mensaje del pasado viernes 12, les hago llegar los datos de conexión de la sesión zoom para el homenaje a Julio Labastida Martín del Campo, el cual se llevará a cabo el próximo lunes 22 de noviembre a las 17:00 horas.\n" +
                "\n" +
                " \n" +
                "\n" +
                "En espera de que nos puedan acompañar, les mando un cordial saludo.\n" +
                "\n" +
                "\n" +
                "El Director.",
        "www.iis.unam.mx",
        "1",
        "29 nov 2021",
        "unseen",
        "alert",
        "direccion",
        "Direccion",
        "Dir"
        )
    }
    val notification: LiveData<IisNotification> = _notification

}