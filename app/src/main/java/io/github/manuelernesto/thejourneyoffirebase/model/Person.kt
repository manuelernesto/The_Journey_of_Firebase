package io.github.manuelernesto.thejourneyoffirebase.model

import com.google.firebase.firestore.Exclude
import java.io.Serializable

class Person() : Serializable {

    @Exclude
    var id: String? = null

    lateinit var name: String
    lateinit var email: String
    lateinit var phone: String

    constructor(name: String, email: String, phone: String) : this() {
        this.name = name
        this.email = email
        this.phone = phone
    }
}