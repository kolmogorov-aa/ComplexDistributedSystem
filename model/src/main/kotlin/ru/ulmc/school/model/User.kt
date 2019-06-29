package ru.ulmc.school.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "T_USER")
data class User(
        @field:Id
        @field:Column(name = "id")
        var id: String? = null,

        @field:Column(name = "registration_date_time")
        var dateTime: LocalDateTime = LocalDateTime.now(),

        @field:Column(name = "login", nullable = false)
        var login: String? = null,

        @field:Column(name = "password")
        var password: String? = null,

        @field:Enumerated(EnumType.STRING)
        @field:Column(name = "role")
        var enum: Role? = null,

        @field:OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
        var comments: List<Comment> = ArrayList()

) {
    constructor() : this(null) {

    }

    init {
        if (id == null) {
            id = UUID.nameUUIDFromBytes("$login $dateTime".toByteArray()).toString()
        }
    }
}

enum class Role {
    USER, MODERATOR, ADMIN
}