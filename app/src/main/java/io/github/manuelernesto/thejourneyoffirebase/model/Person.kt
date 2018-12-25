package io.github.manuelernesto.thejourneyoffirebase.model

class Person() {

    lateinit var name: String
    lateinit var email: String
    lateinit var phone: String

    constructor(name: String, email: String, phone: String) : this() {
        this.name = name
        this.email = email
        this.phone = phone
    }
}