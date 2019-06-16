package ru.ulmc.school.api.entity

import java.io.Serializable


data class TweetMsg(val date: String?, val text: String?) : Serializable {
    constructor() : this(null, null) {

    }
}