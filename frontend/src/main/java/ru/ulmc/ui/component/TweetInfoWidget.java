package ru.ulmc.ui.component;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import ru.ulmc.school.api.entity.TweetMsg;
import ru.ulmc.ui.model.TweetMsgViewModel;

@Tag("tweet-info")
@HtmlImport("frontend://src/market/tweet-info.html")
public class TweetInfoWidget extends PolymerTemplate<TweetMsgViewModel> {

    public TweetInfoWidget() {
        setId("tweet-info" + System.currentTimeMillis());
    }

    public void update(TweetMsg msg) {
        getModel().setDate(msg.getDate());
        getModel().setText(msg.getText());
    }
}
