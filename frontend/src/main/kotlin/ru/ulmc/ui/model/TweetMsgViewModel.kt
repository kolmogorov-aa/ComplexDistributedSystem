package ru.ulmc.ui.model

import com.vaadin.flow.templatemodel.TemplateModel
import java.time.LocalDateTime

data class TweetMsgViewModel(
        var date: String?,
        var text: String?
        ) : TemplateModel
