package ru.ulmc.school.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "t_comments")
data class Comment(
        @field:Id
        @field:GeneratedValue
        var id: Long? = null,

        @field:Column(name = "datetime")
        var dateTime: LocalDateTime = LocalDateTime.now(),

        @field:ManyToOne(cascade = [CascadeType.ALL])
        @field:JoinColumn(name = "author_id")
        var author: User? = null,

        @field:Column
        var text: String = ""

) {
    constructor() : this(author = null) {

    }
}