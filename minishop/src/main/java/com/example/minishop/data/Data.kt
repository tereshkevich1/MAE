package com.example.minishop.data

import com.example.minishop.models.CartItem
import com.example.minishop.models.Product

object Data {
    var products = mutableListOf(
        Product("сутл"),
        Product("стол"),
        Product("книга"),
        Product("носок"),
        Product("usb"),
        Product("ноутбук"),
        Product("таблетки"),
        Product("телевизор"),
        Product("телефон"),
        Product("дверь"),
        Product("диван"),
        Product("плед"),
        Product("панда"),
        Product("ремень"),
        Product("гантели"),
        Product("пылесос"),
        Product("молоко"),
        Product("хлеб"),
        Product("деньги")
    )
    var userGoods = mutableListOf<CartItem>()
}