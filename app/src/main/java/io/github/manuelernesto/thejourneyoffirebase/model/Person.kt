package io.github.manuelernesto.thejourneyoffirebase.model

import com.google.firebase.firestore.Exclude
import java.io.Serializable

data class Person(
    @Exclude var id: String? = null,
    var name: String = "",
    var email: String = "",
    var phone: String = ""
) : Serializable