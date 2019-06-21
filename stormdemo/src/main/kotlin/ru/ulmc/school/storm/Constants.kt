package ru.ulmc.school.storm

object Names {
    const val SOURCE_SPOUT: String = "src-spt"

    const val TRUMP_BOLT: String = "trump-bolt"
    const val FILTER_BOLT: String = "filter-bolt"
    const val STORE_BOLT: String = "store-bolt"

    object Fields {
        const val TWEET_FIELD: String = "tweet-field"
        const val STORE_FIELD: String = "store-field"

    }
}