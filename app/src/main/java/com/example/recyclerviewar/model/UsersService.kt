package com.example.recyclerviewar.model

import com.github.javafaker.Faker
import java.util.Collections

//слушатель изменения данных
typealias UsersListener = (users: List<User>) -> Unit

class UsersService {
    //локальная переменная со списком пользователей из класса User
    private var users = mutableListOf<User>()

    //Создаем переменную-список, в которую сложим все слушателей добавления и удаления
    private val listeners = mutableListOf<UsersListener>()

    init {
        // создам фейкер
        val faker = Faker.instance()
        //перемешать элементы
        IMAGES.shuffle()
        val generateUsers = (1..100).map {
            User(
                id = it,
                name = faker.name().name(),
                company = faker.company().name(),

                //берем остатокот деления
                photo = IMAGES[it % IMAGES.size]
            )
        }
    }

    fun addListener(listener: UsersListener){
        listeners.add(listener)
        listener.invoke(users)
    }

    fun deleteListener(listener: UsersListener){
        listeners.remove(listener)
    }

    fun getUsers(): List<User>{
        return users
    }

    fun deleteUser(user: User) {
        //если индентификатор выбраноо итема не совпадает, indexToDelete будет -1 и удаление не случится
        val indexToDelete = users.indexOfFirst { it.id == user.id }
        if(indexToDelete!=-1){
            users.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    private fun notifyChanges(){
        //проверить все слушатели и вызвать метод инвок и передаем текущий список пользователя
        listeners.forEach{it.invoke(users)}
    }

    fun moveUser(user:User, moveBy:Int){
        val oldIndex = users.indexOfFirst { it.id == user.id }
        if(oldIndex==-1) return
        val newIndex = oldIndex +moveBy
        //проверяем чтобы новый индекс не входил за рамки массива элементов
        if(newIndex < 0 || newIndex >= users.size) return
        //Поменять эл-ты местами, указать список, старый и новый индекс
        Collections.swap(users, oldIndex, newIndex)
        notifyChanges()
    }

    companion object {
        private val IMAGES = mutableListOf(
            "https://unsplash.com/photos/ihdvPX36C2w",
            "https://unsplash.com/photos/jzY0KRJopEI",
            "https://unsplash.com/photos/tNCH0sKSZbA",
            "https://unsplash.com/photos/CQwFUfliJyU",
            "https://unsplash.com/photos/PXWiHU7pbeg"
        )
    }
}