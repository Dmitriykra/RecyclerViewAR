package com.example.recyclerviewar

import android.app.Application
import com.example.recyclerviewar.model.UsersService

class App: Application() {
    //переменная типа UserService() для получения доступа к классу UserService() из любого места приложения
    // в манифест также добавил строку android:name=".App"
    val usersService = UsersService()

}